package tech.medo.demo.seata.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "storage-service",
        path = "/",
        url = "${seata-demo.storage-service-endpoint:''}")
public interface StorageFeignClient {

    @GetMapping("storage/deduct")
    Boolean deduct(
            @RequestParam("commodityCode") String commodityCode,
            @RequestParam("count") Integer count);
}
