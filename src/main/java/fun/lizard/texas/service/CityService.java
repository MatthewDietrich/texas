package fun.lizard.texas.service;

import fun.lizard.texas.document.Airport;
import fun.lizard.texas.document.City;
import fun.lizard.texas.document.County;
import fun.lizard.texas.document.Highway;
import fun.lizard.texas.exception.CityNotFoundException;
import fun.lizard.texas.repository.AirportRepository;
import fun.lizard.texas.repository.CityRepository;
import fun.lizard.texas.repository.CountyRepository;
import fun.lizard.texas.repository.HighwayRepository;
import fun.lizard.texas.response.dto.SimpleAirport;
import fun.lizard.texas.response.dto.SimpleCity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    HighwayRepository highwayRepository;

    @Value("${app.check-distance}")
    Double distanceToCheck;

    @Cacheable("cities")
    public List<String> findAllNames() {
        return cityRepository.findAllNames().stream().map(city -> city.getProperties().getName()).sorted().toList();
    }

    public City findOneByName(String name) {
        try {
            log.info("Searching for city: \"{}\"", name);
            return cityRepository.findAllByName(name).get(0);
        } catch (IndexOutOfBoundsException e) {
            log.info("City not found: \"{}\"", name);
            throw new CityNotFoundException(name);
        }
    }

    public SimpleCity findCountyAndSimplify(City city) {
        String cityName = city.getProperties().getName();
        SimpleCity simpleCity = new SimpleCity();
        simpleCity.setName(cityName);
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        List<County> counties = countyRepository.findByGeometryNear(new Point(longitude, latitude), new Distance(distanceToCheck), Limit.of(1));
        if (!counties.isEmpty()) {
            String countyName = counties.get(0).getProperties().getName();
            simpleCity.setCountyName(countyName);
            log.info("Found county \"{}\" for city \"{}\"", countyName, cityName);
        } else {
            log.warn("Failed to resolve county for city \"{}\"", city);
        }
        List<String> nearestThreeNames = findNearestThree(city).stream().map(c -> c.getProperties().getName()).toList();
        simpleCity.setLatitude(latitude);
        simpleCity.setLongitude(longitude);
        simpleCity.setLastSearched(city.getLastSearched());
        simpleCity.setTimesSearched(city.getTimesSearched());
        simpleCity.setNearestThreeNames(nearestThreeNames);
        try {
            simpleCity.setPopulation(NumberFormat.getNumberInstance(Locale.US).format(city.getPopulation()));
        } catch (IllegalArgumentException e) {
            simpleCity.setPopulation("(no population data)");
        }
        return simpleCity;
    }

    public String plotCity(City city, String theme) throws IOException {
        double minLon = -106.65;
        double maxLon = -93.51;
        double minLat = 25.84;
        double maxLat = 36.5;
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());

        String filename = getMapImageFilename(theme);
        Resource resource = new ClassPathResource(filename);
        InputStream inputStream = resource.getInputStream();
        BufferedImage texasImage = ImageIO.read(inputStream);
        inputStream.close();
        int width = texasImage.getWidth();
        int height = texasImage.getHeight();

        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = finalImage.createGraphics();

        g2d.drawImage(texasImage, 0, 0, null);
        int borderWidth = 20;
        int drawWidth = width - 2 * borderWidth;
        int drawHeight = width - 2 * borderWidth;
        int x = (int) ((longitude - minLon) / (maxLon - minLon) * drawWidth) + borderWidth;
        int y = (int) ((maxLat - latitude) / (maxLat - minLat) * drawHeight) + borderWidth;
        String color = getMapPointColor(theme);
        g2d.setColor(Color.decode(color));
        g2d.fillOval(x - 5, y - 5, 20, 20);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(finalImage, "png", outputStream);
        String result = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        outputStream.close();
        return result;
    }

    public List<SimpleAirport> findNearbyAirports(City city) {
        Point point = getPoint(city);
        List<Airport> airports = airportRepository.findByGeometryNear(point, Limit.of(5));
        return airports.stream().map(airport -> {
            SimpleAirport simpleAirport = new SimpleAirport();
            simpleAirport.setCode(airport.getProperties().getFAA_CD());
            simpleAirport.setName(airport.getProperties().getARPRT_NM());
            City airportCity = cityRepository.findByGeometryNear(airport.getGeometry(), Limit.of(1)).get(0);
            simpleAirport.setCityName(airportCity.getProperties().getName());
            return simpleAirport;
        }).toList();
    }

    public List<String> findNearbyHighways(City city) {
        Point point = getPoint(city);
        return highwayRepository.findByGeometryNearAndNameNotNull(point, new Distance(distanceToCheck))
                .stream()
                .map(Highway::getName)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .toList();
    }

    public String getBlankMap(String theme) throws IOException {
        String filename = getMapImageFilename(theme);
        Resource resource = new ClassPathResource(filename);
        byte[] resourceBytes = resource.getContentAsByteArray();
        return Base64.getEncoder().encodeToString(resourceBytes);
    }

    public String getMapWithPoint(String theme, double lat, double lon) throws IOException {
        double minLon = -106.65;
        double maxLon = -93.51;
        double minLat = 25.84;
        double maxLat = 36.5;

        String filename = getMapImageFilename(theme);
        Resource resource = new ClassPathResource(filename);
        InputStream inputStream = resource.getInputStream();
        BufferedImage texasImage = ImageIO.read(inputStream);
        inputStream.close();
        int width = texasImage.getWidth();
        int height = texasImage.getHeight();

        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = finalImage.createGraphics();

        String color = getMapPointColor(theme);
        g2d.drawImage(texasImage, 0, 0, null);
        int borderWidth = 20;
        int drawWidth = width - 2 * borderWidth;
        int drawHeight = width - 2 * borderWidth;
        int x = (int) ((lon - minLon) / (maxLon - minLon) * drawWidth) + borderWidth;
        int y = (int) ((maxLat - lat) / (maxLat - minLat) * drawHeight) + borderWidth;
        g2d.setColor(Color.decode(color));
        g2d.fillOval(x - 5, y - 5, 20, 20);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(finalImage, "png", outputStream);
        String result = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        outputStream.close();
        return result;
    }

    private String getMapImageFilename(String theme) {
        String filename;
        switch (theme) {
            case "burntorange" -> filename = "texas_burntorange.png";
            case "maroon" -> filename = "texas_maroon.png";
            default -> filename = "texas.png";
        }
        return filename;
    }

    private String getMapPointColor(String theme) {
        String color;
        switch (theme) {
            case "burntorange" -> color = "#eb7d23";
            case "maroon" -> color = "#ffffff";
            default -> color = "#268bd2";
        }
        return color;
    }

    public City findOneNearPoint(double latitude, double longitude) {
        Point point = new Point(longitude, latitude);
        return cityRepository.findByGeometryNear(point, Limit.of(1)).get(0);
    }

    public void updateCity(City city) {
        Integer timesSearched = city.getTimesSearched();
        if (null == timesSearched) {
            timesSearched = 0;
        }
        city.setTimesSearched(timesSearched + 1);
        city.setLastSearched(LocalDateTime.now());
        cityRepository.save(city);
    }

    public List<City> findNearestThree(City city) {
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        Point point = new Point(longitude, latitude);
        return cityRepository.findByGeometryNear(point, Limit.of(4)).subList(1, 4);
    }

    private Point getPoint(City city) {
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        return new Point(longitude, latitude);
    }

    @Cacheable("mostSearched")
    public List<City> getMostSearched() {
        return cityRepository.findTop10ByTimesSearchedGreaterThanOrderByTimesSearchedDesc(0);
    }

    @Cacheable("recentlySearched")
    public List<City> getRecentlySearched() {
        return cityRepository.findTop10ByLastSearchedNotNullOrderByLastSearchedDesc();
    }

    @Scheduled(fixedRate = 10000)
    @CacheEvict("mostSearched")
    public void clearMostSearched() {
    }

    @Scheduled(fixedRate = 10000)
    @CacheEvict("recentlySearched")
    public void clearRecentlySearched() {
    }
}
