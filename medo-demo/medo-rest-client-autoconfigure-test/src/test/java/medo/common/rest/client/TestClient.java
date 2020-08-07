package medo.common.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "test", url = "https://www.test.com")
public interface TestClient {

    // @RequestParam 必须写，否则会被认定为 post 请求
    @GetMapping
    ResponseEntity<String> test(@RequestParam("mkt") String mkt);

}