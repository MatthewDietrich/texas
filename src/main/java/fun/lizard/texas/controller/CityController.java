package fun.lizard.texas.controller;

import fun.lizard.texas.entity.City;
import fun.lizard.texas.entity.District;
import fun.lizard.texas.service.CityService;
import fun.lizard.texas.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {

    @Autowired
    CityService cityService;

    @Autowired
    DistrictService districtService;

    @GetMapping("/city")
    public ResponseEntity<List<String>> getByCoordinates(@RequestParam Double lat, @RequestParam Double lon) {
        return ResponseEntity.ok(cityService.findByCoordinates(lat, lon));
    }

    @GetMapping("/cityname")
    public ResponseEntity<City> getByName(@RequestParam String name) {
        return ResponseEntity.ok(cityService.findOneByName(name));
    }

    @GetMapping("/district")
    public ResponseEntity<District> getDistrictByCityName(@RequestParam String cityName) {
        return ResponseEntity.ok(districtService.findDistrictByCityName(cityName));
    }
}
