package tech.medo.demo.seata.business.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.medo.demo.seata.business.service.BusinessService;

@RestController
public class BusinessController {

    @Resource
    private BusinessService businessService;

    /**
     * 下单场景测试-正常.
     */
    @RequestMapping(path = "/placeOrder")
    public Boolean placeOrder() {
        businessService.placeOrder("U001");
        return true;
    }

    /**
     * 下单场景测试-回滚.
     */
    @RequestMapping(path = "/placeOrderFallBack")
    public Boolean placeOrderFallBack() {
        businessService.placeOrder("U002");
        return true;
    }
}
