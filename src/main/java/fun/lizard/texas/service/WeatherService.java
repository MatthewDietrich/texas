package fun.lizard.texas.service;

import fun.lizard.texas.constant.WmoWeatherCode;
import fun.lizard.texas.document.City;
import fun.lizard.texas.feign.OpenMeteoFeignClient;
import fun.lizard.texas.response.openmeteo.OpenMeteoResponse;
import fun.lizard.texas.response.WeatherResponse;
import fun.lizard.texas.response.openmeteo.Current;
import fun.lizard.texas.response.openmeteo.Forecast;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

@Slf4j
@Service
public class WeatherService {

    @Autowired
    OpenMeteoFeignClient openMeteoFeignClient;

    public WeatherResponse getCurrentWeatherByCity(City city) {
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        log.info("Retrieving weather for city {}", city.getProperties().getName());
        OpenMeteoResponse openMeteoResponse = openMeteoFeignClient.getCurrentWeather(latitude, longitude);
        Map<LocalDate, WmoWeatherCode> dailyCodes = computeDailyCodes(openMeteoResponse.getHourly());
        Current current = getCurrent(openMeteoResponse);
        List<Forecast> forecasts = new ArrayList<>();
        for (int i = 0; i < openMeteoResponse.getDaily().getWeatherCode().size(); i++) {
            Forecast forecast = getForecast(openMeteoResponse, i);
            forecasts.add(forecast);
        }
        forecasts.forEach(forecast -> {
            WmoWeatherCode weatherCode = dailyCodes.getOrDefault(forecast.getDate(), WmoWeatherCode.UNKNOWN);
            if (weatherCode != WmoWeatherCode.UNKNOWN) {
                forecast.setIconClass(weatherCode.getIconClass());
                forecast.setDescription(weatherCode.getDescription());
            }
        });
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setLatitude(latitude);
        weatherResponse.setLongitude(longitude);
        weatherResponse.setForecasts(forecasts);
        weatherResponse.setCurrent(current);
        return weatherResponse;
    }

    private static Current getCurrent(OpenMeteoResponse openMeteoResponse) {
        OpenMeteoResponse.Current openMeteoCurrent = openMeteoResponse.getCurrent();
        Current current = new Current();
        current.setDescription(WmoWeatherCode.fromCode(openMeteoCurrent.getWeatherCode()).getDescription());
        current.setWindSpeed(openMeteoCurrent.getWindSpeed().intValue());
        current.setWindDirection(openMeteoCurrent.getWindDirection());
        current.setPrecipitation(openMeteoCurrent.getPrecipitationProbability());
        current.setCloudCover(openMeteoCurrent.getCloudCover());
        current.setRain(openMeteoCurrent.getRain());
        current.setHumidity(openMeteoCurrent.getRelativeHumidity());
        current.setTemperature(openMeteoCurrent.getTemperature().intValue());
        current.setApparentTemperature(openMeteoCurrent.getApparentTemperature().intValue());
        String iconClass = WmoWeatherCode.fromCode(openMeteoCurrent.getWeatherCode()).getIconClass();
        if (openMeteoCurrent.getIsDay().equals(0)) {
            iconClass = iconClass.replace("day", "night");
            iconClass = iconClass.replace("sunny", "clear");
        }
        current.setIconClass(iconClass);
        return current;
    }

    private static Forecast getForecast(OpenMeteoResponse openMeteoResponse, int i) {
        Forecast forecast = new Forecast();
        OpenMeteoResponse.Daily daily = openMeteoResponse.getDaily();
        forecast.setDate(daily.getTime().get(i));
        forecast.setHighTemperature(daily.getMaxTemperature().get(i).intValue());
        forecast.setLowTemperature(daily.getMinTemperature().get(i).intValue());
        forecast.setPrecipitationChance(daily.getPrecipitationChance().get(i));
        forecast.setShortWeekday(daily.getTime().get(i).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US));
        forecast.setDescription(WmoWeatherCode.fromCode(daily.getWeatherCode().get(i)).getDescription());
        forecast.setIconClass(WmoWeatherCode.fromCode(daily.getWeatherCode().get(i)).getIconClass());
        return forecast;
    }

    private static Map<LocalDate, WmoWeatherCode> computeDailyCodes(OpenMeteoResponse.Hourly hourly) {
        List<LocalDateTime> times = hourly.getTime();
        List<Integer> codes = hourly.getWeatherCode();
        Map<LocalDate, List<Integer>> dailyCodeLists = new HashMap<>();
        Map<LocalDate, WmoWeatherCode> dailyCodes = new HashMap<>();
        for (int i = 0; i < times.size(); i++) {
            LocalDate date = times.get(i).toLocalDate();
            dailyCodeLists.computeIfAbsent(date, k -> new ArrayList<>()).add(codes.get(i));
        }
        for (Map.Entry<LocalDate, List<Integer>> codeList : dailyCodeLists.entrySet()) {
            Map<Integer, Integer> codeFrequency = new HashMap<>();
            int max = 0;
            int maxCode = 0;
            for (Integer code : codeList.getValue()) {
                codeFrequency.put(code, codeFrequency.getOrDefault(code, 0) + 1);
                int currentFrequency = codeFrequency.get(code);
                if (currentFrequency > max) {
                    max = currentFrequency;
                    maxCode = code;
                }
            }
            dailyCodes.put(codeList.getKey(), WmoWeatherCode.fromCode(maxCode));
        }
        return dailyCodes;
    }

    @Scheduled(fixedRate = 300000)
    @CacheEvict("forecasts")
    public void emptyForecastsCache() {
        log.info("Emptying forecast cache");
    }
}
