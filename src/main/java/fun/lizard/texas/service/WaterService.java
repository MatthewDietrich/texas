package fun.lizard.texas.service;

import fun.lizard.texas.document.Reservoir;
import fun.lizard.texas.feign.WaterDataForTexasFeignClient;
import fun.lizard.texas.repository.ReservoirRepository;
import fun.lizard.texas.response.dto.SimpleReservoir;
import fun.lizard.texas.response.waterdatafortexas.RecentConditionsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class WaterService {

    private final WaterDataForTexasFeignClient waterDataForTexasFeignClient;
    private final ReservoirRepository reservoirRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public WaterService(WaterDataForTexasFeignClient waterDataForTexasFeignClient, ReservoirRepository reservoirRepository, MongoTemplate mongoTemplate) {
        this.waterDataForTexasFeignClient = waterDataForTexasFeignClient;
        this.reservoirRepository = reservoirRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Cacheable("reservoirs")
    public void updateCurrentReservoirData() {
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Reservoir.class);
        RecentConditionsResponse recentConditionsResponse = waterDataForTexasFeignClient.getRecentConditions();
        recentConditionsResponse.getFeatures().forEach(
                feature -> {
                    Query query = new Query((Criteria.where("properties.name").is(feature.getProperties().getFullName())));
                    Update update = new Update()
                            .set("geometry", new GeoJsonPoint(feature.getGeometry().getCoordinates().get(0), feature.getGeometry().getCoordinates().get(1)))
                            .set("percentFull", feature.getProperties().getPercentFull())
                            .set("name", feature.getProperties().getFullName())
                            .set("asOfDate", feature.getProperties().getTimestamp());
                    bulkOperations.upsert(query, update);
                }
        );
        bulkOperations.execute();
    }

    @Scheduled(cron = "0 0 12 * * *")
    @CacheEvict("reservoirs")
    public void clearReservoirsCache() {
        log.info("Emptying reservoirs cache");
    }
}
