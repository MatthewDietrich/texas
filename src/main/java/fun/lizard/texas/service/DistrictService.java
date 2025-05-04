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

@Service
public class DistrictService {

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    CityRepository cityRepository;

    @Value("${app.check-distance}")
    Double distanceToCheck;

    public District findDistrictByCityName(String cityName) {
        City city = cityRepository.findOneByName(cityName);
        if (null == city) {
            return null;
        }
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        Point point = new Point(longitude, latitude);
        return districtRepository.findByGeometryNear(point, new Distance(distanceToCheck)).get(0);
    }
}
