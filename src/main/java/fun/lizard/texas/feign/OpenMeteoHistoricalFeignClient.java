package fun.lizard.texas.feign;

import fun.lizard.texas.response.openmeteo.OpenMeteoHistoricalResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "open-meteo-historical-client",
    url = "https://archive-api.open-meteo.com/v1")
public interface OpenMeteoHistoricalFeignClient {

    @Cacheable("histories")
    @GetMapping("/archive")
    OpenMeteoHistoricalResponse getHistoricalData(@RequestParam Double latitude,
                                                  @RequestParam Double longitude,
                                                  @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                  @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);
}
