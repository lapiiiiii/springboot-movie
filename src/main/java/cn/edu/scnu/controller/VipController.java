package cn.edu.scnu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VipController {

    @GetMapping("/pay")
    public String showPayPage() {
        return "pay";  // 返回pay.html模板
    }
}
