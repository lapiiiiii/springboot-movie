package cn.edu.scnu.Service;


import cn.edu.scnu.entity.Order;
import cn.edu.scnu.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    // 创建新订单（userId固定为1）
    @Transactional
    public Order createOrder(Integer vipDuration) {
        Order order = new Order();
        order.setOrderNumber(generateOrderId()); // 生成唯一订单号
        order.setUserId(1); // 固定用户ID
        order.setDuration(vipDuration);
        order.setAmount(calculateAmount(vipDuration));
        order.setStatus(1); // 未支付状态
        order.setCreateTime(LocalDateTime.now());
        order.setPayTime(LocalDateTime.now());
        orderMapper.insert(order);
        return order;
    }


    // 生成唯一订单号（示例：UUID+时间戳）
    private String generateOrderId() {
        return UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
    }

    // 计算套餐金额
    private BigDecimal calculateAmount(Integer duration) {
        return switch (duration) {
            case 30 -> new BigDecimal("30.00");
            case 90 -> new BigDecimal("80.00");
            case 365 -> new BigDecimal("300.00");
            default -> BigDecimal.ZERO;
        };
    }
}