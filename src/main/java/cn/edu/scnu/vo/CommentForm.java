package cn.edu.scnu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentForm {
    private Long movieId;
    private String content;
    private Long parentId; // 回复的评论 ID（可选）
    // getters and setters
}