package fun.lizard.texas.feign;

import fun.lizard.texas.response.openmeteo.OpenMeteoForecastResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "open-meteo-forecast-client",
    url = "https://api.open-meteo.com/v1")
public interface OpenMeteoForecastFeignClient {

    @Cacheable("forecasts")
    @GetMapping(path = "/forecast")
    OpenMeteoForecastResponse getCurrentWeather(@RequestParam Double latitude, @RequestParam Double longitude);
}
