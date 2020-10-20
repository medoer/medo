package tech.medo.demo.seata.order.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import tech.medo.demo.seata.order.feign.AccountFeignClient;
import tech.medo.demo.seata.order.mapper.OrderMapper;
import tech.medo.demo.seata.order.model.Order;

@Service
public class OrderService {
    @Resource private AccountFeignClient accountFeignClient;

    @Resource private OrderMapper orderMapper;

    // @Transactional(rollbackFor = Exception.class)
    public void create(String userId, String commodityCode, Integer count) {
        // 订单金额
        Integer orderMoney = count * 2;

        Order order =
                new Order()
                        .setUserId(userId)
                        .setCommodityCode(commodityCode)
                        .setCount(count)
                        .setMoney(orderMoney);
        orderMapper.insert(order);

        accountFeignClient.reduce(userId, orderMoney);
    }
}
