package tech.medo.demo.seata.business.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import io.seata.spring.annotation.GlobalTransactional;
import tech.medo.demo.seata.business.feign.OrderFeignClient;
import tech.medo.demo.seata.business.feign.StorageFeignClient;

@Service
public class BusinessService {

    private static final String COMMODITY_CODE = "P001";
    private static final int ORDER_COUNT = 1;

    @Resource
    private OrderFeignClient orderFeignClient;

    @Resource
    private StorageFeignClient storageFeignClient;

    /**
     * 下订单
     */
    @GlobalTransactional
    public void placeOrder(String userId) {
        storageFeignClient.deduct(COMMODITY_CODE, ORDER_COUNT);

        orderFeignClient.create(userId, COMMODITY_CODE, ORDER_COUNT);
    }
}
