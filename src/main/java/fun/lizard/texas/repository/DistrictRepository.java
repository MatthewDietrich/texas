package fun.lizard.texas.repository;

import fun.lizard.texas.document.District;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends MongoRepository<District, String> {

    List<District> findByGeometryNear(Point point, Distance distance);
}
