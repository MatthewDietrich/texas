package fun.lizard.texas.feign;

import fun.lizard.texas.response.waterdatafortexas.RecentConditionsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "water-data-for-texas-client",
    url = "https://www.waterdatafortexas.org/reservoirs/statewide")
public interface WaterDataForTexasFeignClient {

    @GetMapping(path = "/recent-conditions.geojson")
    RecentConditionsResponse getRecentConditions();
}
