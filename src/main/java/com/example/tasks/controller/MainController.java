package com.example.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(Model model, HttpServletResponse response, @CookieValue(required = false) String sid) throws IOException {
        if (sid == null)
            response.sendRedirect("/auth");
        else
            response.sendRedirect("/boards/");
        return null;
    }
}
