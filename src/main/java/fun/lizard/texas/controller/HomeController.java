package fun.lizard.texas.controller;

import fun.lizard.texas.response.txdot.CctvSnapshotResponse;
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

    @GetMapping("/home")
    public String getHome(ModelMap modelMap) {
        return "home";
    }

    @PostMapping("/home")
    public String getSnapshot(ModelMap modelMap, @RequestParam String cityName) {
        List<CctvSnapshotResponse> snapshots = cctvService.getSnapshotsByCityName(cityName);
        WeatherResponse weather = weatherService.getCurrentWeatherByCityName(cityName);
        modelMap.put("snapshots", snapshots);
        modelMap.put("weather", weather);
        modelMap.put("cityName", cityName);
        return "snapshot";
    }

    @GetMapping("/citynames")
    public ResponseEntity<List<String>> getCityNames() {
        return ResponseEntity.ok(cityService.findAllNames());
    }
}
