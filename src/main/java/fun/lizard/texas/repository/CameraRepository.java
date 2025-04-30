package fun.lizard.texas.repository;

import fun.lizard.texas.entity.txdot.Camera;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends CrudRepository<Camera, String> {

    Camera findOneByIcdId(String icdId);
}
