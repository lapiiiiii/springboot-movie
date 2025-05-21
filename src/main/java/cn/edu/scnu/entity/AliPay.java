package cn.edu.scnu.entity;


import lombok.Data;

@Data
public class AliPay {
    private String traceNo; // 商户订单号（对应Order.id）
    private double totalAmount; // 支付金额
    private String subject; // 商品名称
}