package fun.lizard.texas.service;

import fun.lizard.texas.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Cacheable("cities")
    public List<String> findAllNames() {
        return cityRepository.findAllNames().stream().map(city -> city.getProperties().getName()).sorted().toList();
    }

    @Scheduled(fixedRate = 7200000)
    @CacheEvict("cities")
    public void emptyCitiesCache() {
        log.info("Emptying cities cache");
    }
}
