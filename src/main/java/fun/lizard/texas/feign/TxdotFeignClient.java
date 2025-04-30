package fun.lizard.texas.feign;

import fun.lizard.texas.response.txdot.CctvSnapshotResponse;
import fun.lizard.texas.response.txdot.CctvStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "txdot-client",
    url = "https://its.txdot.gov/its/DistrictIts")
public interface TxdotFeignClient {

    @GetMapping(path = "/GetCctvStatusListByDistrict")
    CctvStatusResponse getCctvStatusListByDistrict(@RequestParam String districtCode);

    @GetMapping(path = "/GetCctvSnapshotByIcdId")
    CctvSnapshotResponse getCctvSnapshotByIcdId(@RequestParam String icdId, @RequestParam String districtCode);
}
