package fun.lizard.texas.repository;

import fun.lizard.texas.document.Highway;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HighwayRepository extends MongoRepository<Highway, String> {

    List<Highway> findByGeometryNearAndNameNotNull(Point point, Distance distance);
}
