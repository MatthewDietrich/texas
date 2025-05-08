package fun.lizard.texas.service;

import fun.lizard.texas.constant.WmoWeatherCode;
import fun.lizard.texas.document.City;
import fun.lizard.texas.feign.OpenMeteoFeignClient;
import fun.lizard.texas.repository.CityRepository;
import fun.lizard.texas.response.weather.OpenMeteoResponse;
import fun.lizard.texas.response.weather.WeatherResponse;
import fun.lizard.texas.response.weather.Current;
import fun.lizard.texas.response.weather.Forecast;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class WeatherService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    OpenMeteoFeignClient openMeteoFeignClient;

    public WeatherResponse getCurrentWeatherByCity(City city) {
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
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
        Current current = new Current();
        current.setDescription(WmoWeatherCode.fromCode(openMeteoResponse.getCurrent().getWeatherCode()).getDescription());
        current.setWindSpeed(openMeteoResponse.getCurrent().getWindSpeed());
        current.setWindDirection(openMeteoResponse.getCurrent().getWindDirection());
        current.setPrecipitation(openMeteoResponse.getCurrent().getPrecipitationProbability());
        current.setCloudCover(openMeteoResponse.getCurrent().getCloudCover());
        current.setRain(openMeteoResponse.getCurrent().getRain());
        current.setHumidity(openMeteoResponse.getCurrent().getRelativeHumidity());
        current.setTemperature(openMeteoResponse.getCurrent().getTemperature().intValue());
        current.setApparentTemperature(openMeteoResponse.getCurrent().getApparentTemperature().intValue());
        current.setIconClass(WmoWeatherCode.fromCode(openMeteoResponse.getCurrent().getWeatherCode()).getIconClass());
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

    @Scheduled(fixedRate = 900000)
    @CacheEvict("forecasts")
    public void emptyForecastsCache() {
        log.info("Emptying forecasts cache");
    }
}
