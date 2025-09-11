package fun.lizard.texas.repository;

import fun.lizard.texas.document.County;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountyRepository extends MongoRepository<County, String> {

    List<County> findByGeometryNear(Point point, Distance distance, Limit limit);
}
