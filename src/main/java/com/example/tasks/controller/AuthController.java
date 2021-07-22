package com.example.tasks.controller;

import com.example.tasks.entity.LoginData;
import com.example.tasks.entity.Session;
import com.example.tasks.entity.User;
import com.example.tasks.repository.SessionRepository.SessionRepository;
import com.example.tasks.service.SessionService.SessionService;
import com.example.tasks.service.UserService.UserService;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class AuthController {

    @Autowired
    UserService userService;



    @GetMapping("/auth")
    public String auth(Model model) {
        model.addAttribute("msg_error", "");
        model.addAttribute("loginData", new LoginData());
        return "auth";
    }

    @PostMapping(path = "/auth", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String postAuth(Model model, HttpServletResponse response, @ModelAttribute LoginData loginData) throws IOException {


        String passHash = Hashing.sha256()
                .hashString(loginData.getPassword(), StandardCharsets.UTF_8)
                .toString();
        Session session = userService.auth(
                loginData.getLogin(),
                passHash);

        if (session == null) {
            model.addAttribute("msg_error", "Wrong pair login-password");
            return "/auth";
        }

        response.addCookie(new Cookie("sid", session.getSid()));
        response.sendRedirect("/boards/");
        return null;
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletResponse response, @CookieValue(required = false) String sid) throws IOException {

        Cookie cookie = new Cookie("sid", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/auth");
        return null;
    }
}
