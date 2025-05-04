package fun.lizard.texas.controller;

import fun.lizard.texas.response.weather.WeatherResponse;
import fun.lizard.texas.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String cityName) {
        return ResponseEntity.ok(weatherService.getCurrentWeatherByCityName(cityName));
    }
}
