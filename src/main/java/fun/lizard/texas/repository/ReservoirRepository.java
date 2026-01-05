package fun.lizard.texas.repository;

import fun.lizard.texas.document.Reservoir;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservoirRepository extends MongoRepository<Reservoir, String> {

    List<Reservoir> findByGeometryNear(Point point, Limit limit);
}
