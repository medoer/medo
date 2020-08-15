package tech.medo.demo.seata.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service", path = "/", url = "${seata-demo.account-service-endpoint:''}")
public interface AccountFeignClient {
    @PostMapping("account/reduce")
    Boolean reduce(@RequestParam("userId") String userId, @RequestParam("money") Integer money);
}
