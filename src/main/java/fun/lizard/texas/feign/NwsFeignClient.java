package fun.lizard.texas.feign;

import feign.Headers;
import fun.lizard.texas.response.nws.NwsAlertResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://api.weather.gov",
        name = "nws-client")
@Headers("User-Agent: (texas.lizard.fun, squam00@gmail.com)")
public interface NwsFeignClient {

    @Cacheable("alerts")
    @GetMapping("/alerts/active")
    NwsAlertResponse getActiveAlerts(@RequestParam String point);
}