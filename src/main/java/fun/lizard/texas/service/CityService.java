package fun.lizard.texas.service;

import fun.lizard.texas.document.City;
import fun.lizard.texas.document.County;
import fun.lizard.texas.repository.CityRepository;
import fun.lizard.texas.repository.CountyRepository;
import fun.lizard.texas.response.SimpleCity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountyRepository countyRepository;

    @Value("${app.check-distance}")
    Double distanceToCheck;

    @Cacheable("cities")
    public List<String> findAllNames() {
        return cityRepository.findAllNames().stream().map(city -> city.getProperties().getName()).sorted().toList();
    }

    public City findOneByName(String name) {
        return cityRepository.findOneByName(name);
    }

    @Scheduled(fixedRate = 7200000)
    @CacheEvict("cities")
    public void emptyCitiesCache() {
        log.info("Emptying cities cache");
    }

    public SimpleCity findCountyAndSimplify(City city) {
        SimpleCity simpleCity = new SimpleCity();
        simpleCity.setName(city.getProperties().getName());
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        List<County> counties = countyRepository.findByGeometryNear(new Point(longitude, latitude), new Distance(distanceToCheck), Limit.of(1));
        if (!counties.isEmpty()) {
            simpleCity.setCountyName(counties.get(0).getProperties().getName());
        }
        simpleCity.setLatitude(latitude);
        simpleCity.setLongitude(longitude);
        return simpleCity;
    }
}
