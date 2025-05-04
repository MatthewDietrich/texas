package fun.lizard.texas.service;

import fun.lizard.texas.entity.City;
import fun.lizard.texas.entity.District;
import fun.lizard.texas.repository.CityRepository;
import fun.lizard.texas.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Value("${app.check-distance}")
    Double distanceToCheck;

    public List<String> findByCoordinates(Double latitude, Double longitude) {
        Point point = new Point(latitude, longitude);
        return cityRepository.findByGeometryNear(point, new Distance(distanceToCheck)).stream().map(city -> city.getProperties().getName()).toList();
    }

    public City findOneByName(String name) {
        return cityRepository.findOneByName(name);
    }
}
