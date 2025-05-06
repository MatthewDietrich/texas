package fun.lizard.texas.service;

import fun.lizard.texas.constant.WmoWeatherCode;
import fun.lizard.texas.document.City;
import fun.lizard.texas.feign.OpenMeteoFeignClient;
import fun.lizard.texas.repository.CityRepository;
import fun.lizard.texas.response.weather.OpenMeteoResponse;
import fun.lizard.texas.response.weather.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeatherService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    OpenMeteoFeignClient openMeteoFeignClient;

    public WeatherResponse getCurrentWeatherByCityName(String cityName) {
        City city = cityRepository.findOneByName(cityName);
        if (null == city) {
            log.info("City not found: {}", cityName);
            return null;
        }
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        OpenMeteoResponse openMeteoResponse = openMeteoFeignClient.getCurrentWeather(latitude, longitude);
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setLatitude(latitude);
        weatherResponse.setLongitude(longitude);
        weatherResponse.setDescription(WmoWeatherCode.fromCode(openMeteoResponse.getCurrent().getWeatherCode()).getDescription());
        weatherResponse.setWindSpeed(openMeteoResponse.getCurrent().getWindSpeed());
        weatherResponse.setWindDirection(openMeteoResponse.getCurrent().getWindDirection());
        weatherResponse.setPrecipitation(openMeteoResponse.getCurrent().getPrecipitation());
        weatherResponse.setCloudCover(openMeteoResponse.getCurrent().getCloudCover());
        weatherResponse.setRain(openMeteoResponse.getCurrent().getRain());
        weatherResponse.setHumidity(openMeteoResponse.getCurrent().getRelativeHumidity());
        weatherResponse.setTemperature(openMeteoResponse.getCurrent().getTemperature());
        weatherResponse.setApparentTemperature(openMeteoResponse.getCurrent().getApparentTemperature());
        return weatherResponse;
    }

    @Scheduled(fixedRate = 900000)
    @CacheEvict("forecasts")
    public void emptyForecastsCache() {
        log.info("Emptying forecasts cache");
    }
}
