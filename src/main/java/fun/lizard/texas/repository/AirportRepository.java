package fun.lizard.texas.repository;

import fun.lizard.texas.document.Airport;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends MongoRepository<Airport, String> {

    List<Airport> findByGeometryNear(Point point, Limit limit);
}
