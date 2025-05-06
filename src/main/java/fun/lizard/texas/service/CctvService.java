package fun.lizard.texas.service;

import fun.lizard.texas.document.City;
import fun.lizard.texas.document.Camera;
import fun.lizard.texas.document.District;
import fun.lizard.texas.feign.TxdotFeignClient;
import fun.lizard.texas.repository.CameraRepository;
import fun.lizard.texas.repository.CityRepository;
import fun.lizard.texas.repository.DistrictRepository;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
    MongoTemplate mongoTemplate;

    @Value("${app.check-distance}")
    Double distanceToCheck;

    @Value("${app.city-camera-limit}")
    Integer cameraLimit;

    public void updateCamerasByDistrictId(String districtId) {
        List<Camera> cameras = new ArrayList<>();
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

    public List<CctvSnapshotResponse> getSnapshotsByCity(City city) throws IOException {
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        Point point = new Point(longitude, latitude);
        District district = districtRepository.findByGeometryNear(point, new Distance(distanceToCheck)).get(0);
        if (null == district) {
            log.info("District not found for city: {}", city.getProperties().getName());
            return null;
        }
        updateCamerasByDistrictId(district.getProperties().getDIST_ABRVN());
        Resource resource = new ClassPathResource("unavailable.png");
        String unavailableSnippet = Base64.getEncoder().encodeToString(resource.getContentAsByteArray());
        List<Camera> cameras = cameraRepository.findByLocationNear(point, new Distance(0.4), Limit.of(cameraLimit));
        return cameras.stream().map(camera -> txdotFeignClient.getCctvSnapshotByIcdId(camera.getIcdId(), camera.getDistrictAbbreviation())).peek(
                cctvSnapshotResponse -> {
                    if (cctvSnapshotResponse != null && cctvSnapshotResponse.getSnippet().isEmpty()) {
                        cctvSnapshotResponse.setSnippet(unavailableSnippet);
                    }
                }
        ).toList();
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
