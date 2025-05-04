package fun.lizard.texas.controller;

import fun.lizard.texas.entity.Camera;
import fun.lizard.texas.response.txdot.CctvSnapshotResponse;
import fun.lizard.texas.service.CctvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CameraController {

    @Autowired
    CctvService cctvService;

    @GetMapping(value = "/snapshot")
    public ResponseEntity<List<CctvSnapshotResponse>> getSnapshotByCityName(@RequestParam String cityName){
        return ResponseEntity.ok(cctvService.getSnapshotsByCityName(cityName));
    }
}
