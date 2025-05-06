package fun.lizard.texas.repository;

import fun.lizard.texas.document.County;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountyRepository extends MongoRepository<County, String> {

    @Query(value = "{ 'properties.name': ?0 }")
    County findOneByName(String name);

    List<County> findByGeometryNear(Point point, Distance distance, Limit limit);
}
