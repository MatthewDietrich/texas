package fun.lizard.texas.controller;

import fun.lizard.texas.document.City;
import fun.lizard.texas.exception.CityNotFoundException;
import fun.lizard.texas.response.SimpleCity;
import fun.lizard.texas.response.txdot.CctvSnapshotResponse;
import fun.lizard.texas.response.weather.Current;
import fun.lizard.texas.response.weather.WeatherResponse;
import fun.lizard.texas.service.CctvService;
import fun.lizard.texas.service.CityService;
import fun.lizard.texas.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes({"cityName"})
public class HomeController {

    @Autowired
    CctvService cctvService;

    @Autowired
    WeatherService weatherService;

    @Autowired
    CityService cityService;

    @GetMapping("/")
    public String getHome(ModelMap modelMap) {
        return "home";
    }

    @PostMapping("/")
    public String getSnapshot(ModelMap modelMap, @RequestParam String cityName) throws IOException {
        String cityNameStripped = cityName.strip();
        City city;
        try {
            city = cityService.findOneByName(cityNameStripped);
        } catch (CityNotFoundException e) {
            modelMap.put("cityName", cityNameStripped);
            return "notfound";
        }
        List<CctvSnapshotResponse> snapshots = cctvService.getSnapshotsByCity(city);
        WeatherResponse weather = weatherService.getCurrentWeatherByCity(city);
        SimpleCity simpleCity = cityService.findCountyAndSimplify(city);
        String cityMap = cityService.plotCity(city);
        modelMap.put("snapshots", snapshots);
        modelMap.put("weather", weather);
        modelMap.put("city", simpleCity);
        modelMap.put("cityMap", cityMap);
        return "snapshot";
    }

    @GetMapping("/citynames")
    public ResponseEntity<List<String>> getCityNames() {
        return ResponseEntity.ok(cityService.findAllNames());
    }
}
