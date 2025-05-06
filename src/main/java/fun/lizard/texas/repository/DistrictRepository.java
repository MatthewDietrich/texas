package fun.lizard.texas.repository;

import fun.lizard.texas.document.District;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends MongoRepository<District, String> {

    @Query("{ 'properties.TXDOT_DIST_NM: ?0 }")
    District findOneByName(String name);

    List<District> findByGeometryNear(Point point, Distance distance);
}
