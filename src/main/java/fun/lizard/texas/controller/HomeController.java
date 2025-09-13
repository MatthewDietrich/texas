package fun.lizard.texas.controller;

import fun.lizard.texas.document.Camera;
import fun.lizard.texas.document.City;
import fun.lizard.texas.document.County;
import fun.lizard.texas.document.District;
import fun.lizard.texas.exception.CityNotFoundException;
import fun.lizard.texas.response.dto.*;
import fun.lizard.texas.response.txdot.CctvSnapshotResponse;
import fun.lizard.texas.service.CctvService;
import fun.lizard.texas.service.CityService;
import fun.lizard.texas.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Controller
@SessionAttributes({ "cityName" })
@Slf4j
public class HomeController {

    @Autowired
    CctvService cctvService;

    @Autowired
    WeatherService weatherService;

    @Autowired
    CityService cityService;

    @GetMapping("/")
    public String getHome(ModelMap modelMap) throws IOException {
        String texasMap = cityService.getBlankMap();
        List<City> mostSearched = cityService.getMostSearched();
        List<City> recentlySearched = cityService.getRecentlySearched();
        modelMap.put("texasMap", texasMap);
        modelMap.put("mostSearched", mostSearched);
        modelMap.put("recentlySearched", recentlySearched);
        return "home";
    }

    @GetMapping("/city")
    public String getSnapshot(ModelMap modelMap, @RequestParam String name) throws IOException {
        log.info("City search called with input: \"{}\"", name);
        String cityNameStripped = name.strip();
        City city;
        try {
            city = cityService.findOneByName(cityNameStripped);
        } catch (CityNotFoundException e) {
            log.info("Result not found for city search with input: \"{}\"", name);
            modelMap.put("cityName", cityNameStripped);
            return "citynotfound";
        }
        cityService.updateCity(city);
        CompletableFuture<List<CctvSnapshotResponse>> snapshots = CompletableFuture
                .supplyAsync(() -> cctvService.getSnapshotsByCity(city));
        CompletableFuture<WeatherForecastResponse> weather = CompletableFuture
                .supplyAsync(() -> weatherService.getForecastByCity(city));
        CompletableFuture<List<WeatherHistoricalResponse>> weatherHistory = CompletableFuture
                .supplyAsync(() -> weatherService.getHistoryByCity(city));
        CompletableFuture<List<WeatherAlert>> weatherAlerts = CompletableFuture
                .supplyAsync(() -> weatherService.getWeatherAlertsByCity(city));
        SimpleCity simpleCity = cityService.findCountyAndSimplify(city);
        String cityMap = cityService.plotCity(city);
        List<SimpleAirport> simpleAirports = cityService.findNearbyAirports(city);
        String dateString = LocalDate.now(ZoneId.of("America/Chicago"))
                .format(DateTimeFormatter.ofPattern("MMMM dd", Locale.US));
        modelMap.put("weather", weather.join());
        modelMap.put("city", simpleCity);
        modelMap.put("cityMap", cityMap);
        modelMap.put("airports", simpleAirports);
        modelMap.put("weatherHistory", weatherHistory.join());
        modelMap.put("weatherAlerts", weatherAlerts.join());
        modelMap.put("dateString", dateString);
        modelMap.put("snapshots", snapshots.join());
        log.info("Result returned for city search with input: \"{}\"", name);
        return "city";
    }

    @GetMapping("/coordinates")
    public String getByCoordinates(@RequestParam Double lat, @RequestParam Double lon) {
        City city = cityService.findOneNearPoint(lat, lon);
        return "forward:/city?name=" + city.getProperties().getName();
    }

    @GetMapping("/about")
    public String getAbout(ModelMap modelMap) {
        return "about";
    }

    @GetMapping("/citynames")
    public ResponseEntity<List<String>> getCityNames() {
        return ResponseEntity.ok(cityService.findAllNames());
    }

    @GetMapping("/camera")
    public String getCameraByIcdId(ModelMap modelMap, @RequestParam String id) {
        String idStripped = id.strip();
        SimpleSnapshot simpleSnapshot = cctvService.fetchSnapshot(idStripped);
        modelMap.put("cityName", simpleSnapshot.getCityName());
        modelMap.put("cameraId", simpleSnapshot.getCameraId());
        modelMap.put("lat", simpleSnapshot.getLatitude());
        modelMap.put("lon", simpleSnapshot.getLongitude());
        modelMap.put("districtAbbreviation", simpleSnapshot.getDistrictAbbreviation());
        modelMap.put("snapshot", simpleSnapshot.getSnapshot());
        modelMap.put("countyName", simpleSnapshot.getCountyName());
        modelMap.put("snapshotTime", simpleSnapshot.getSnapshotTime());
        return "camera";
    }
}
