package fun.lizard.texas.repository;

import fun.lizard.texas.document.City;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends MongoRepository<City, String> {

    @Query(value = "{}, {'properties.name': 1}")
    List<City> findAllNames();

    @Query(value = "{ 'properties.name': {$regex : '^?0$', $options: 'i'}}")
    List<City> findAllByName(String name);

    List<City> findByGeometryNear(Point point, Limit limit);

    List<City> findTop10ByLastSearchedNotNullOrderByLastSearchedDesc();

    List<City> findTop10ByTimesSearchedGreaterThanOrderByTimesSearchedDesc(int minTimesSearched);

    List<City> findTop100ByTimesSearchedGreaterThanOrderByTimesSearchedDesc(int minTimesSearched);
}
