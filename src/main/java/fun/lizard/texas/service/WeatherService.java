package fun.lizard.texas.service;

import fun.lizard.texas.constant.WmoWeatherCode;
import fun.lizard.texas.document.City;
import fun.lizard.texas.feign.OpenMeteoFeignClient;
import fun.lizard.texas.response.weather.OpenMeteoResponse;
import fun.lizard.texas.response.weather.WeatherResponse;
import fun.lizard.texas.response.weather.Current;
import fun.lizard.texas.response.weather.Forecast;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        Current current = getCurrent(openMeteoResponse);
        List<Forecast> forecasts = new ArrayList<>();
        for (int i = 0; i < openMeteoResponse.getDaily().getWeatherCode().size(); i++) {
            Forecast forecast = getForecast(openMeteoResponse, i);
            forecasts.add(forecast);
        }
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
        forecast.setDate(openMeteoResponse.getDaily().getTime().get(i));
        forecast.setIconClass(WmoWeatherCode.fromCode(openMeteoResponse.getDaily().getWeatherCode().get(i)).getIconClass());
        forecast.setHighTemperature(openMeteoResponse.getDaily().getMaxTemperature().get(i).intValue());
        forecast.setLowTemperature(openMeteoResponse.getDaily().getMinTemperature().get(i).intValue());
        forecast.setPrecipitationChance(openMeteoResponse.getDaily().getPrecipitationChance().get(i));
        forecast.setDescription(WmoWeatherCode.fromCode(openMeteoResponse.getDaily().getWeatherCode().get(i)).getDescription());
        forecast.setShortWeekday(openMeteoResponse.getDaily().getTime().get(i).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US));
        return forecast;
    }

    @Scheduled(fixedRate = 300000)
    @CacheEvict("forecasts")
    public void emptyForecastsCache() {
        log.info("Emptying forecast cache");
    }
}
