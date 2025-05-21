package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("vip_order")
public class Order {
    private String id; // 主键
    private Integer userId; // 用户ID（固定为1）
    private String orderNumber;//  订单编号
    private Integer duration; // 套餐时长（天）
    private BigDecimal amount; // 金额
    private Integer status; // 订单状态（1=未支付，2=已支付，3=已取消）
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime payTime; // 支付时间
}