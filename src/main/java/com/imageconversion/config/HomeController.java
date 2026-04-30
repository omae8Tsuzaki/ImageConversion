package com.imageconversion.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>ルートURLをトップページにリダイレクトするコントローラー。</p>
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/home/Menu.html";
    }
}
