package cn.edu.scnu.Service;

import cn.edu.scnu.entity.Comment;
import cn.edu.scnu.mapper.CommentMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

    public void addComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        this.baseMapper.insert(comment); // 用 this.baseMapper
    }

    public Page<Comment> getCommentsByMovieId(Long movieId, int pageNum, int pageSize) {
        // 创建分页对象，页码从1开始
        Page<Comment> page = new Page<>(pageNum, pageSize);

        // 构建查询条件
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("movie_id", movieId);

        // 执行分页查询
        IPage<Comment> result = this.baseMapper.selectPage(page, queryWrapper);

        // 返回分页结果
        return (Page<Comment>) result;
    }
}

