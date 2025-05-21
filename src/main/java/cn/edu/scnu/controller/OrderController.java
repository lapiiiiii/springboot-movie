package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Order;
import cn.edu.scnu.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 创建订单接口
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Integer> request) {
        try {
            logger.info("收到创建订单请求: {}", request);

            // 检查请求参数
            if (request == null || !request.containsKey("vipDuration")) {
                logger.error("创建订单失败: 缺少vipDuration参数");
                return ResponseEntity.badRequest().body("缺少必要参数");
            }

            Integer vipDuration = request.get("vipDuration");
            Order order = orderService.createOrder(vipDuration);

            logger.info("订单创建成功: {}", order.getId());
            return ResponseEntity.ok(order);

        } catch (Exception e) {
            logger.error("创建订单异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("订单创建失败: " + e.getMessage());
        }
    }
}