package fun.lizard.texas.service;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import fun.lizard.texas.document.City;
import fun.lizard.texas.document.Camera;
import fun.lizard.texas.document.County;
import fun.lizard.texas.document.District;
import fun.lizard.texas.feign.TxdotFeignClient;
import fun.lizard.texas.repository.CameraRepository;
import fun.lizard.texas.repository.CityRepository;
import fun.lizard.texas.repository.CountyRepository;
import fun.lizard.texas.repository.DistrictRepository;
import fun.lizard.texas.response.dto.SimpleSnapshot;
import fun.lizard.texas.response.txdot.CctvSnapshotResponse;
import fun.lizard.texas.response.txdot.CctvStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CctvService {

    @Autowired
    TxdotFeignClient txdotFeignClient;

    @Autowired
    CameraRepository cameraRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${app.check-distance}")
    Double distanceToCheck;

    @Value("${app.city-camera-limit}")
    Integer cameraLimit;

    public void updateCamerasByDistrictId(String districtId) {
        CctvStatusResponse cctvStatusResponse = txdotFeignClient.getCctvStatusListByDistrict(districtId);
        cctvStatusResponse.getRoadwayCctvStatuses().forEach((roadwayName, cctvStatuses) -> cctvStatuses.forEach((roadwayCctvStatus -> {
            Camera camera = new Camera();
            camera.setLocation(new GeoJsonPoint(new Point(roadwayCctvStatus.getLongitude(), roadwayCctvStatus.getLatitude())));
            camera.setIcdId(roadwayCctvStatus.getIcdId());
            camera.setDirection(roadwayCctvStatus.getDirDescription());
            camera.setHasSnapshot(roadwayCctvStatus.getHasSnapshot());
            camera.setDistrictAbbreviation(districtId);
            Query query = new Query(Criteria.where("icdId").is(camera.getIcdId()));
            mongoTemplate.update(Camera.class)
                    .matching(query)
                    .replaceWith(camera)
                    .withOptions(FindAndReplaceOptions.options().upsert().returnNew())
                    .as(Camera.class)
                    .findAndReplace();
        })));
    }

    public CompletableFuture<CctvSnapshotResponse> fetchSnapshotAsync(String icdId, String districtName) {
        return CompletableFuture.supplyAsync(() -> txdotFeignClient.getCctvSnapshotByIcdId(icdId, districtName));
    }

    public SimpleSnapshot fetchSnapshot(String icdId) {
        Camera camera = cameraRepository.findOneByIcdId(icdId);
        double lat = camera.getLocation().getCoordinates().get(1);
        double lon = camera.getLocation().getCoordinates().get(0);
        BigDecimal bdLat = new BigDecimal(Double.toString(lat)).setScale(7, RoundingMode.HALF_UP);
        BigDecimal bdLon = new BigDecimal(Double.toString(lon)).setScale(7, RoundingMode.HALF_UP);
        Point point = new Point(lon, lat);
        List<District> districts = districtRepository.findByGeometryNear(point, new Distance(distanceToCheck));
        if (districts.isEmpty()) {
            log.info("District not found for camera: {}", icdId);
            return null;
        }
        List<City> cities = cityRepository.findByGeometryNear(point, Limit.of(1));
        if (cities.isEmpty()) {
            log.info("City not found for camera: {}", icdId);
            return null;
        }
        List<County> counties = countyRepository.findByGeometryNear(point, new Distance(distanceToCheck), Limit.of(1));
        if (counties.isEmpty()) {
            log.info("County not found for camera: {}", icdId);
            return null;
        }
        District district = districts.get(0);
        City city = cities.get(0);
        County county = counties.get(0);
        String districtAbbreviation = district.getProperties().getDIST_ABRVN();
        CctvSnapshotResponse response = txdotFeignClient.getCctvSnapshotByIcdId(icdId, districtAbbreviation);
        Integer timesViewed = camera.getTimesViewed();
        if (null == timesViewed) {
            timesViewed = 1;
        } else {
            timesViewed += 1;
        }
        camera.setTimesViewed(timesViewed);
        camera.setLastViewed(LocalDateTime.now());
        cameraRepository.save(camera);

        SimpleSnapshot simpleSnapshot = new SimpleSnapshot();
        simpleSnapshot.setCameraId(camera.getIcdId());
        simpleSnapshot.setCityName(city.getProperties().getName());
        simpleSnapshot.setLongitude(bdLon.doubleValue());
        simpleSnapshot.setLatitude(bdLat.doubleValue());
        simpleSnapshot.setDistrictAbbreviation(districtAbbreviation);
        simpleSnapshot.setSnapshot(response.getSnippet());
        simpleSnapshot.setCountyName(county.getProperties().getName());
        simpleSnapshot.setSnapshotTime(response.getTimestampFormatted());
        simpleSnapshot.setTimesViewed(timesViewed);
        return simpleSnapshot;
    }

    public List<CctvSnapshotResponse> getSnapshotsByCity(City city) {
        String cityName = city.getProperties().getName();
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        Point point = new Point(longitude, latitude);
        List<District> districts = districtRepository.findByGeometryNear(point, new Distance(distanceToCheck));
        if (districts.isEmpty()) {
            log.info("District not found for city: {}", cityName);
            return null;
        }
        District district = districts.get(0);
        String districtAbbreviation = district.getProperties().getDIST_ABRVN();
        updateCamerasByDistrictId(districtAbbreviation);
        Resource resource = new ClassPathResource("unavailable.png");
        byte[] resourceContent;
        try {
            resourceContent = resource.getContentAsByteArray();
        } catch (IOException e) {
            resourceContent = ByteArrayBuilder.NO_BYTES;
        }
        String unavailableSnippet = Base64.getEncoder().encodeToString(resourceContent);
        List<Camera> cameras = cameraRepository.findByLocationNear(point, new Distance(0.4), Limit.of(cameraLimit));
        Map<String, String> cameraDirections = new HashMap<>();
        cameras.forEach(camera -> cameraDirections.put(camera.getIcdId(), camera.getDirection()));
        List<CompletableFuture<CctvSnapshotResponse>> cctvFutures = cameras.stream().map(camera -> fetchSnapshotAsync(camera.getIcdId(), camera.getDistrictAbbreviation())).toList();
        log.info("Retrieving camera images for city {} (district {})", cityName, districtAbbreviation);
        return cctvFutures.stream().map(future -> {
            CctvSnapshotResponse cctvSnapshotResponse = future.join();
            if (null == cctvSnapshotResponse) {
                cctvSnapshotResponse = new CctvSnapshotResponse();
                cctvSnapshotResponse.setIcdId("");
                cctvSnapshotResponse.setSnippet(unavailableSnippet);
                cctvSnapshotResponse.setDirection("");
            } else {
                String direction = cameraDirections.get(cctvSnapshotResponse.getIcdId());
                String snippet = cctvSnapshotResponse.getSnippet();
                String objectUnavailableBase64 = "PGJvZHk+PGgxPkhUVFAvMS4wIDQwNCBPYmplY3Qgbm90IGF2YWlsYWJsZSBvbiB0aGlzIFdlYnNlcnZlcjwvaDE+PC9ib2R5Pg==";
                if (null == direction) {
                    cctvSnapshotResponse.setDirection("");
                } else {
                    cctvSnapshotResponse.setDirection("Facing " + direction);
                }
                if (null == snippet|| snippet.isEmpty() || snippet.equals(objectUnavailableBase64)) {
                    cctvSnapshotResponse.setSnippet(unavailableSnippet);
                }
            }
            return cctvSnapshotResponse;
        }).toList();
    }

    public Camera findOneByIcdId(String icdId) {
        return cameraRepository.findOneByIcdId(icdId);
    }

    @Scheduled(fixedRate = 300000)
    @CacheEvict(value = "snapshots", allEntries = true)
    public void emptySnapshotsCache() {
        log.info("Emptying snapshot cache");
    }

    @Scheduled(fixedRate = 300000)
    @CacheEvict(value = "cameras", allEntries = true)
    public void emptyCamerasCache() {
        log.info("Emptying camera cache");
    }
}
