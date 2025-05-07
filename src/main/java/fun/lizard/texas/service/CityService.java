package fun.lizard.texas.service;

import fun.lizard.texas.document.City;
import fun.lizard.texas.document.County;
import fun.lizard.texas.exception.CityNotFoundException;
import fun.lizard.texas.repository.CityRepository;
import fun.lizard.texas.repository.CountyRepository;
import fun.lizard.texas.response.SimpleCity;
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
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountyRepository countyRepository;

    @Value("${app.check-distance}")
    Double distanceToCheck;

    @Cacheable("cities")
    public List<String> findAllNames() {
        return cityRepository.findAllNames().stream().map(city -> city.getProperties().getName()).sorted().toList();
    }

    public City findOneByName(String name) {
        try {
            return cityRepository.findAllByName(name).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new CityNotFoundException(name);
        }
    }

    @Scheduled(fixedRate = 7200000)
    @CacheEvict("cities")
    public void emptyCitiesCache() {
        log.info("Emptying cities cache");
    }

    public SimpleCity findCountyAndSimplify(City city) {
        SimpleCity simpleCity = new SimpleCity();
        simpleCity.setName(city.getProperties().getName());
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        List<County> counties = countyRepository.findByGeometryNear(new Point(longitude, latitude), new Distance(distanceToCheck), Limit.of(1));
        if (!counties.isEmpty()) {
            simpleCity.setCountyName(counties.get(0).getProperties().getName());
        }
        simpleCity.setLatitude(latitude);
        simpleCity.setLongitude(longitude);
        return simpleCity;
    }

    public String plotCity(City city) throws IOException {
        double minLon = -106.65;
        double maxLon = -93.51;
        double minLat = 25.84;
        double maxLat = 36.5;
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());

        Resource resource = new ClassPathResource("texas.png");
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
        int x = (int) ((longitude - minLon) / (maxLon - minLon) * drawWidth);
        int y = (int) ((maxLat - latitude) / (maxLat - minLat) * drawHeight);
        g2d.setColor(Color.decode("#268bd2"));
        g2d.fillOval(x - 5, y - 5, 20, 20);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(finalImage, "png", outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
