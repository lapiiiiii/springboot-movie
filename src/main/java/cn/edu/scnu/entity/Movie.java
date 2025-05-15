package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("movie")
public class Movie {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String movieName;

    private String type;

    private String region;

    private String director;

    private LocalDate releaseDate;

    private Integer goodCommentCount;

    private Integer playTimes;

    private Boolean vipOnly;

    private String coverImage;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}