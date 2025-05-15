package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private Integer userType;

    private String adminAccount;

    private LocalDateTime registerTime;

    private LocalDateTime vipStartTime;

    private LocalDateTime vipEndTime;

    private LocalDateTime lastRenewTime;

    private Integer totalRenewTimes;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}