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
@TableName("creator")
public class Creator {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String creatorName;

    private String creatorType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}