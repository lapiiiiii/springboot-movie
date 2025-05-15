package cn.edu.scnu.controller;

import cn.edu.scnu.Service.CommentService;
import cn.edu.scnu.Service.UserService;
import cn.edu.scnu.entity.Comment;
import cn.edu.scnu.entity.User;
import cn.edu.scnu.vo.CommentForm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;


    // TODO: 后续需替换为从Session或认证模块获取真实用户信息（当前为测试用硬编码）
    private final Long TEST_USER_ID = 1L; // 测试用户ID（需确保数据库中存在此用户）
    private final String TEST_USERNAME = "影迷小杨"; // 测试用户昵称（需与数据库中用户表的username一致）

    // 显示评论列表页面
    @GetMapping
    public String showCommentList(@RequestParam("movieId") Long movieId,
                                  @RequestParam(defaultValue = "1") Integer pageNum,
                                  Model model) {
        Page<Comment> commentPage = commentService.getCommentsByMovieId(movieId, pageNum, 5);
        model.addAttribute("movieId", movieId);
        model.addAttribute("comments", commentPage.getRecords());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", commentPage.getPages());
        model.addAttribute("commentForm", new CommentForm());
        return "commentList";
    }

    // 处理评论提交（硬编码用户信息，后续需集成用户认证）
    @PostMapping("/save")
    public String saveComment(CommentForm form,
                              Model model) {
        Comment comment = new Comment();
        comment.setMovieId(form.getMovieId());

        // TODO: 此处硬编码用户ID，后续需改为从Session中获取登录用户ID
        // 例如：User currentUser = (User) session.getAttribute("currentUser");
        // comment.setUserId(currentUser.getId());
        comment.setUserId(TEST_USER_ID);

        // TODO: 此处硬编码用户昵称，后续需从用户实体中获取真实昵称
        // 例如：comment.setUsername(currentUser.getUsername());
        comment.setUsername(TEST_USERNAME);

        comment.setContent(form.getContent());
        comment.setParentId(form.getParentId() != null ? form.getParentId() : 0L);

        commentService.addComment(comment);
        model.addAttribute("successMsg", "评论提交成功！");

        // 刷新到第一页
        return showCommentList(form.getMovieId(), 1, model);
    }
}