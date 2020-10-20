package tech.medo.demo.seata.storage.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.medo.demo.seata.storage.service.StorageService;

@RestController
@RequestMapping("/storage")
@AllArgsConstructor
public class StorageController {

    private StorageService storageService;

    /**
     * 减库存.
     *
     * @param commodityCode 商品代码
     * @param count 数量
     */
    @RequestMapping(path = "/deduct")
    public Boolean deduct(String commodityCode, Integer count) {
        storageService.deduct(commodityCode, count);
        return true;
    }
}
