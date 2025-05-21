package cn.edu.scnu.controller;

import cn.edu.scnu.config.AliPayConfig;
import cn.edu.scnu.entity.AliPay;
import cn.edu.scnu.entity.Order;
import cn.edu.scnu.mapper.OrderMapper;
import cn.edu.scnu.mapper.UserMapper;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("alipay")
@Transactional(rollbackFor = Exception.class)
public class AliPayController {

    @Resource
    private AliPayConfig aliPayConfig;

    @Resource
    private OrderMapper shopOrderMapper;

    @Resource
    private UserMapper userMapper;

    // 沙箱环境网关（旧版SDK固定路径）
    private static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA2";

    // 支付接口
    @GetMapping("/pay")
    public void pay(AliPay aliPay, HttpServletResponse response) throws Exception {
        // 构建支付宝客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAY_URL,
                aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(),
                FORMAT,
                CHARSET,
                aliPayConfig.getAlipayPublicKey(),
                SIGN_TYPE
        );

        // 创建支付请求
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(aliPayConfig.getNotifyUrl()); // 同步回调地址（可选）
        request.setNotifyUrl(aliPayConfig.getNotifyUrl()); // 异步通知地址

        // 设置业务参数（注意：金额需为字符串，避免精度问题）
        request.setBizContent(String.format(
                "{\"out_trade_no\":\"%s\"," +
                        "\"total_amount\":\"%.2f\"," +
                        "\"subject\":\"%s\"," +
                        "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}",
                aliPay.getTraceNo(),
                aliPay.getTotalAmount(),
                aliPay.getSubject()
        ));

        try {
            // 生成支付宝支付表单
            String form = alipayClient.pageExecute(request).getBody();
            response.setContentType("text/html;charset=" + CHARSET);
            response.getWriter().write(form);
            response.getWriter().flush();
        } catch (AlipayApiException | IOException e) {
            e.printStackTrace();
            response.sendError(500, "生成支付页面失败");
        }
    }

    @PostMapping("/notify")
    public String payNotify(HttpServletRequest request) throws Exception {
        if (!"TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            return "failure";
        }

        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> params.put(k, v[0]));

        boolean verifyResult = com.alipay.api.internal.util.AlipaySignature.rsaCheckV1(
                params, aliPayConfig.getAlipayPublicKey(), CHARSET, SIGN_TYPE
        );

        if (verifyResult) {
            String tradeNo = params.get("out_trade_no");
            Order order = shopOrderMapper.selectById(tradeNo); // 查询订单获取套餐时长

            if (order != null && order.getStatus() == 1) { // 确保订单未处理
                // 1. 更新订单状态为已支付
                order.setStatus(2);
                shopOrderMapper.updateById(order);

                // 2. 更新用户VIP信息（固定用户ID=1，后续从Session获取）
                Integer userId = 1; // 临时固定值，后续替换为Session中的用户ID
                Integer duration = order.getDuration(); // 套餐时长（天）

                // 调用UserMapper执行更新
                userMapper.updateUserVipStatus(userId, duration);

                return "success";
            }
        }
        return "failure";
    }
}