package tech.medo.demo.seata.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service", path = "/", url = "${seata-demo.order-service-endpoint:''}")
public interface OrderFeignClient {

    @GetMapping("order/create")
    Boolean create(@RequestParam("userId") String userId,
            @RequestParam("commodityCode") String commodityCode,
            @RequestParam("count") Integer count);
}
