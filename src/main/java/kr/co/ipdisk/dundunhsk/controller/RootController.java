package kr.co.ipdisk.dundunhsk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/welcome")
    @ResponseBody
    public String welcome() {
        return "<div style=\"text-align: center;\font-size: 20px;\"><h3>Welcome to MySite</h3></div>";
    }

}
