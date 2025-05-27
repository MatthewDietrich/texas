package fun.lizard.texas.service;

import fun.lizard.texas.constant.WmoWeatherCode;
import fun.lizard.texas.document.City;
import fun.lizard.texas.feign.NwsFeignClient;
import fun.lizard.texas.feign.OpenMeteoForecastFeignClient;
import fun.lizard.texas.feign.OpenMeteoHistoricalFeignClient;
import fun.lizard.texas.response.dto.*;
import fun.lizard.texas.response.nws.NwsAlertResponse;
import fun.lizard.texas.response.openmeteo.OpenMeteoForecastResponse;
import fun.lizard.texas.response.openmeteo.OpenMeteoHistoricalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Autowired
    NwsFeignClient nwsFeignClient;

    private static final double KPA_INHG = 0.02953;

    public WeatherForecastResponse getForecastByCity(City city) {
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        log.info("Retrieving weather forecast for city {}", city.getProperties().getName());
        OpenMeteoForecastResponse openMeteoForecastResponse = openMeteoForecastFeignClient.getCurrentWeather(latitude, longitude);

        Map<LocalDate, WmoWeatherCode> dailyCodes = computeDailyCodes(openMeteoForecastResponse.getHourly());
        Current current = getCurrent(openMeteoForecastResponse);
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        for (int i = 1; i < openMeteoForecastResponse.getDaily().getWeatherCode().size(); i++) {
            DailyForecast dailyForecast = getDailyForecast(openMeteoForecastResponse, i);
            dailyForecasts.add(dailyForecast);
        }
        dailyForecasts.forEach(dailyForecast -> {
            WmoWeatherCode weatherCode = dailyCodes.getOrDefault(dailyForecast.getDate(), WmoWeatherCode.UNKNOWN);
            if (weatherCode != WmoWeatherCode.UNKNOWN) {
                dailyForecast.setIconClass(weatherCode.getIconClass());
                dailyForecast.setDescription(weatherCode.getDescription());
            }
        });

        Map<LocalDateTime, HourlyForecast> hourlyForecastMap = getHourlyForecastMap(openMeteoForecastResponse);
        List<HourlyForecast> hourlyForecasts = new ArrayList<>();
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("America/Chicago"));
        for (int i = 2; i <= 12; i += 2) {
            LocalDateTime searchTime = currentTime.plusHours(i).withMinute(0).withSecond(0).withNano(0);
            hourlyForecasts.add(hourlyForecastMap.get(searchTime));
        }

        Map<Integer, Integer> hourlyConditions = new HashMap<>();
        for (int i = 0; i < openMeteoForecastResponse.getHourly().getTime().size(); i++) {
            hourlyConditions.put(openMeteoForecastResponse.getHourly().getTime().get(i).getHour(), openMeteoForecastResponse.getHourly().getWeatherCode().get(i));
        }
        WmoWeatherCode weatherCode = WmoWeatherCode.fromCode(hourlyConditions.getOrDefault(LocalDateTime.now(ZoneId.of("America/Chicago")).getHour(), -1));
        current.setDescription(weatherCode.getDescription());
        String iconClass = weatherCode.getIconClass();
        if (openMeteoForecastResponse.getCurrent().getIsDay().equals(0)) {
            iconClass = iconClass.replace("day", "night");
            iconClass = iconClass.replace("sunny", "clear");
        }
        current.setIconClass(iconClass);

        WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
        weatherForecastResponse.setLatitude(latitude);
        weatherForecastResponse.setLongitude(longitude);
        weatherForecastResponse.setDailyForecasts(dailyForecasts);
        weatherForecastResponse.setHourlyForecasts(hourlyForecasts);
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

        double pressureInHg = Double.parseDouble(new DecimalFormat("#.##").format(openMeteoCurrent.getPressureMsl() * KPA_INHG));
        current.setPressureMsl(pressureInHg);

        String iconClass = WmoWeatherCode.fromCode(openMeteoCurrent.getWeatherCode()).getIconClass();
        if (openMeteoCurrent.getIsDay().equals(0)) {
            iconClass = iconClass.replace("day", "night");
            iconClass = iconClass.replace("sunny", "clear");
        }
        current.setIconClass(iconClass);

        return current;
    }

    private static DailyForecast getDailyForecast(OpenMeteoForecastResponse openMeteoForecastResponse, int i) {
        DailyForecast dailyForecast = new DailyForecast();
        OpenMeteoForecastResponse.Daily daily = openMeteoForecastResponse.getDaily();
        dailyForecast.setDate(daily.getTime().get(i));
        dailyForecast.setHighTemperature(daily.getMaxTemperature().get(i).intValue());
        dailyForecast.setLowTemperature(daily.getMinTemperature().get(i).intValue());
        dailyForecast.setPrecipitationChance(daily.getPrecipitationChance().get(i));
        dailyForecast.setShortWeekday(daily.getTime().get(i).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US));
        dailyForecast.setDescription(WmoWeatherCode.fromCode(daily.getWeatherCode().get(i)).getDescription());
        dailyForecast.setIconClass(WmoWeatherCode.fromCode(daily.getWeatherCode().get(i)).getIconClass());
        return dailyForecast;
    }

    private static Map<LocalDateTime, HourlyForecast> getHourlyForecastMap(OpenMeteoForecastResponse openMeteoForecastResponse) {
        Map<LocalDateTime, HourlyForecast> hourlyForecastMap = new HashMap<>();
        OpenMeteoForecastResponse.Hourly hourly = openMeteoForecastResponse.getHourly();
        for (int i = 0; i < hourly.getTime().size(); i++) {
            HourlyForecast hourlyForecast = new HourlyForecast();
            LocalDateTime time = hourly.getTime().get(i);
            hourlyForecast.setTime(time);
            hourlyForecast.setTimeFormatted(time.format(DateTimeFormatter.ofPattern("h a")));
            hourlyForecast.setTemperature(hourly.getTemperature().get(i).intValue());
            hourlyForecast.setPrecipitationChance(hourly.getPrecipitationChance().get(i));
            WmoWeatherCode weatherCode = WmoWeatherCode.fromCode(hourly.getWeatherCode().get(i));
            hourlyForecast.setDescription(weatherCode.getDescription());
            String iconClass = weatherCode.getIconClass();
            if (hourly.getIsDay().get(i).equals(0)) {
                iconClass = iconClass.replace("day", "night");
                iconClass = iconClass.replace("sunny", "clear");
            }
            hourlyForecast.setIconClass(iconClass);
            hourlyForecastMap.put(time, hourlyForecast);
        }
        return hourlyForecastMap;
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
        LocalDate searchDate = LocalDate.now(ZoneId.of("America/Chicago"));
        OpenMeteoHistoricalResponse openMeteoHistoricalResponse1yr = openMeteoHistoricalFeignClient.getHistoricalData(latitude, longitude, searchDate.minusYears(1), searchDate.minusYears(1));
        OpenMeteoHistoricalResponse openMeteoHistoricalResponse5yr = openMeteoHistoricalFeignClient.getHistoricalData(latitude, longitude, searchDate.minusYears(5), searchDate.minusYears(5));
        OpenMeteoHistoricalResponse openMeteoHistoricalResponse10yr = openMeteoHistoricalFeignClient.getHistoricalData(latitude, longitude, searchDate.minusYears(10), searchDate.minusYears(10));
        return List.of(
                getWeatherHistoricalResponse(openMeteoHistoricalResponse1yr),
                getWeatherHistoricalResponse(openMeteoHistoricalResponse5yr),
                getWeatherHistoricalResponse(openMeteoHistoricalResponse10yr)
        );
    }

    private static WeatherHistoricalResponse getWeatherHistoricalResponse(OpenMeteoHistoricalResponse openMeteoHistoricalResponse) {
        OpenMeteoHistoricalResponse.Daily daily = openMeteoHistoricalResponse.getDaily();
        WeatherHistoricalResponse weatherHistoricalResponse = new WeatherHistoricalResponse();

        WmoWeatherCode weatherCode = WmoWeatherCode.fromCode(daily.getWeatherCode().get(0));
        weatherHistoricalResponse.setDescription(weatherCode.getDescription());
        weatherHistoricalResponse.setIconClass(weatherCode.getIconClass());

        weatherHistoricalResponse.setWindDirection(daily.getDominantWindDirection().get(0));
        weatherHistoricalResponse.setSunrise(daily.getSunrise().get(0).format(DateTimeFormatter.ofPattern("h:mm a")));
        weatherHistoricalResponse.setSunset(daily.getSunset().get(0).format(DateTimeFormatter.ofPattern("h:mm a")));
        weatherHistoricalResponse.setMaxTemperature(daily.getMaxTemperature().get(0).intValue());
        weatherHistoricalResponse.setMinTemperature(daily.getMinTemperature().get(0).intValue());
        weatherHistoricalResponse.setPrecipitationSum(daily.getPrecipitationSum().get(0));
        weatherHistoricalResponse.setWindSpeed(daily.getMeanWindSpeed().get(0).intValue());
        weatherHistoricalResponse.setHumidity(daily.getHumidity().get(0).intValue());
        weatherHistoricalResponse.setCloudCover(daily.getMeanCloudCover().get(0).intValue());

        double pressureInHg = Double.parseDouble(new DecimalFormat("#.##").format(daily.getPressureMsl().get(0) * KPA_INHG));
        weatherHistoricalResponse.setPressureMsl(pressureInHg);

        return weatherHistoricalResponse;
    }

    public List<WeatherAlert> getWeatherAlertsByCity(City city) {
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        String pointString = "%s,%s".formatted(latitude, longitude);
        NwsAlertResponse nwsAlertResponse = nwsFeignClient.getActiveAlerts(pointString);
        return nwsAlertResponse.getFeatures().stream().map(nwsAlertFeature -> {
            WeatherAlert weatherAlert = new WeatherAlert();
            weatherAlert.setEvent(nwsAlertFeature.getProperties().getEvent());
            weatherAlert.setCertainty(nwsAlertFeature.getProperties().getCertainty());
            weatherAlert.setUrgency(nwsAlertFeature.getProperties().getUrgency());
            weatherAlert.setSeverity(nwsAlertFeature.getProperties().getSeverity());
            weatherAlert.setEndTime(nwsAlertFeature.getProperties().getExpires().format(DateTimeFormatter.ofPattern("h:mm a")));
            return weatherAlert;
        }).toList();
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

    @Scheduled(fixedRate = 300000)
    @CacheEvict("alerts")
    public void emptyAlertsCache() {
        log.info("Emptying weather alert cache");
    }
}
