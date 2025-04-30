package fun.lizard.texas.service;

import fun.lizard.texas.entity.txdot.Camera;
import fun.lizard.texas.feign.TxdotFeignClient;
import fun.lizard.texas.repository.CameraRepository;
import fun.lizard.texas.response.txdot.CctvStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CctvService {

    @Autowired
    TxdotFeignClient txdotFeignClient;

    @Autowired
    CameraRepository cameraRepository;

    public List<Camera> getCamerasByDistrictId(String districtId) {
        List<Camera> cameras = new ArrayList<>();
        CctvStatusResponse cctvStatusResponse = txdotFeignClient.getCctvStatusListByDistrict(districtId);
        cctvStatusResponse.getRoadwayCctvStatuses().forEach((roadwayName, cctvStatuses) -> cctvStatuses.forEach((roadwayCctvStatus -> {
            Camera camera = new Camera();
            camera.setLatitude(roadwayCctvStatus.getLatitude());
            camera.setLongitude(roadwayCctvStatus.getLongitude());
            camera.setIcdId(roadwayCctvStatus.getIcdId());
            camera.setDirection(roadwayCctvStatus.getDirDescription());
            camera.setHasSnapshot(roadwayCctvStatus.getHasSnapshot());
            cameras.add(camera);
        })));
        cameraRepository.saveAll(cameras);
        return cameras;
    }
}
