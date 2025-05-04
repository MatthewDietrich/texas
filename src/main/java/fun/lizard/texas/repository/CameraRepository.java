package fun.lizard.texas.repository;

import fun.lizard.texas.entity.Camera;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraRepository extends MongoRepository<Camera, String> {

    List<Camera> findByLocationNear(Point point, Distance distance, Limit limit);
}
