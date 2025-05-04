package fun.lizard.texas.feign;

import fun.lizard.texas.response.weather.OpenMeteoResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "open-meteo-client",
    url = "https://api.open-meteo.com/v1")
public interface OpenMeteoFeignClient {

    @Cacheable("forecasts")
    @GetMapping(path = "/forecast")
    OpenMeteoResponse getCurrentWeather(@RequestParam Double latitude, @RequestParam Double longitude);
}
