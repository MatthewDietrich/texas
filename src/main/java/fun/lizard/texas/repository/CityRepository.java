package fun.lizard.texas.repository;

import fun.lizard.texas.document.City;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends MongoRepository<City, String> {

    @Query(value = "{ 'properties.name': ?0 }")
    City findOneByName(String name);

    @Query(value = "{}, {'properties.name': 1}")
    List<City> findAllNames();

    @Query(value = "{ 'properties.name': ?0 }")
    List<City> findAllByName(String name);

    List<City> findByGeometryNear(Point point, Distance distance);
}
