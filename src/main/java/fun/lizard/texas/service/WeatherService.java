package fun.lizard.texas.service;

import fun.lizard.texas.constant.WmoWeatherCode;
import fun.lizard.texas.document.City;
import fun.lizard.texas.feign.OpenMeteoForecastFeignClient;
import fun.lizard.texas.feign.OpenMeteoHistoricalFeignClient;
import fun.lizard.texas.response.dto.WeatherHistoricalResponse;
import fun.lizard.texas.response.openmeteo.OpenMeteoForecastResponse;
import fun.lizard.texas.response.dto.WeatherForecastResponse;
import fun.lizard.texas.response.dto.Current;
import fun.lizard.texas.response.dto.Forecast;
import fun.lizard.texas.response.openmeteo.OpenMeteoHistoricalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Slf4j
@Service
public class WeatherService {

    @Autowired
    OpenMeteoForecastFeignClient openMeteoForecastFeignClient;

    @Autowired
    OpenMeteoHistoricalFeignClient openMeteoHistoricalFeignClient;

    public WeatherForecastResponse getForecastByCity(City city) {
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        log.info("Retrieving weather forecast for city {}", city.getProperties().getName());
        OpenMeteoForecastResponse openMeteoForecastResponse = openMeteoForecastFeignClient.getCurrentWeather(latitude, longitude);
        Map<LocalDate, WmoWeatherCode> dailyCodes = computeDailyCodes(openMeteoForecastResponse.getHourly());
        Current current = getCurrent(openMeteoForecastResponse);
        List<Forecast> forecasts = new ArrayList<>();
        for (int i = 0; i < openMeteoForecastResponse.getDaily().getWeatherCode().size(); i++) {
            Forecast forecast = getForecast(openMeteoForecastResponse, i);
            forecasts.add(forecast);
        }
        forecasts.forEach(forecast -> {
            WmoWeatherCode weatherCode = dailyCodes.getOrDefault(forecast.getDate(), WmoWeatherCode.UNKNOWN);
            if (weatherCode != WmoWeatherCode.UNKNOWN) {
                forecast.setIconClass(weatherCode.getIconClass());
                forecast.setDescription(weatherCode.getDescription());
            }
        });
        WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
        weatherForecastResponse.setLatitude(latitude);
        weatherForecastResponse.setLongitude(longitude);
        weatherForecastResponse.setForecasts(forecasts);
        weatherForecastResponse.setCurrent(current);
        return weatherForecastResponse;
    }

    private static Current getCurrent(OpenMeteoForecastResponse openMeteoForecastResponse) {
        OpenMeteoForecastResponse.Current openMeteoCurrent = openMeteoForecastResponse.getCurrent();
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

    private static Forecast getForecast(OpenMeteoForecastResponse openMeteoForecastResponse, int i) {
        Forecast forecast = new Forecast();
        OpenMeteoForecastResponse.Daily daily = openMeteoForecastResponse.getDaily();
        forecast.setDate(daily.getTime().get(i));
        forecast.setHighTemperature(daily.getMaxTemperature().get(i).intValue());
        forecast.setLowTemperature(daily.getMinTemperature().get(i).intValue());
        forecast.setPrecipitationChance(daily.getPrecipitationChance().get(i));
        forecast.setShortWeekday(daily.getTime().get(i).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US));
        forecast.setDescription(WmoWeatherCode.fromCode(daily.getWeatherCode().get(i)).getDescription());
        forecast.setIconClass(WmoWeatherCode.fromCode(daily.getWeatherCode().get(i)).getIconClass());
        return forecast;
    }

    private static Map<LocalDate, WmoWeatherCode> computeDailyCodes(OpenMeteoForecastResponse.Hourly hourly) {
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

    public List<WeatherHistoricalResponse> getHistoryByCity(City city) {
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        log.info("Retrieving historical weather for city {}", city.getProperties().getName());
        LocalDate searchDate = LocalDate.now();
        OpenMeteoHistoricalResponse openMeteoHistoricalResponse = openMeteoHistoricalFeignClient.getHistoricalData(latitude, longitude, searchDate.minusYears(3), searchDate.minusYears(1));
        OpenMeteoHistoricalResponse.Daily daily = openMeteoHistoricalResponse.getDaily();
        List<WeatherHistoricalResponse> weatherHistoricalResponses = new ArrayList<>();
        for (int i = 0; i < daily.getWeatherCode().size(); i++) {
            WeatherHistoricalResponse weatherHistoricalResponse = new WeatherHistoricalResponse();
            weatherHistoricalResponse.setLatitude(latitude);
            weatherHistoricalResponse.setLongitude(longitude);
            WmoWeatherCode weatherCode = WmoWeatherCode.fromCode(daily.getWeatherCode().get(i));
            weatherHistoricalResponse.setDescription(weatherCode.getDescription());
            weatherHistoricalResponse.setIconClass(weatherCode.getIconClass());
            weatherHistoricalResponse.setWindDirection(daily.getDominantWindDirection().get(i));
            weatherHistoricalResponse.setSunrise(daily.getSunrise().get(i).format(DateTimeFormatter.ofPattern("hh:mm a")));
            weatherHistoricalResponse.setSunset(daily.getSunset().get(i).format(DateTimeFormatter.ofPattern("hh:mm a")));
            weatherHistoricalResponse.setMaxTemperature(daily.getMaxTemperature().get(i).intValue());
            weatherHistoricalResponse.setMinTemperature(daily.getMinTemperature().get(i).intValue());
            weatherHistoricalResponse.setPrecipitationSum(daily.getPrecipitationSum().get(i));
            weatherHistoricalResponse.setWindSpeed(daily.getMeanWindSpeed().get(i).intValue());
            weatherHistoricalResponse.setHumidity(daily.getHumidity().get(i).intValue());
            weatherHistoricalResponse.setCloudCover(daily.getMeanCloudCover().get(0).intValue());
            weatherHistoricalResponses.add(weatherHistoricalResponse);
        }
        return weatherHistoricalResponses;
    }

    @Scheduled(fixedRate = 300000)
    @CacheEvict("forecasts")
    public void emptyForecastsCache() {
        log.info("Emptying weather forecast cache");
    }

    @Scheduled(fixedRate = 28800000)
    @CacheEvict("histories")
    public void emptyHistoriesCache() {
        log.info("Emptying weather history cache");
    }
}
