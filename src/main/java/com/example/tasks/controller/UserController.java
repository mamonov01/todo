package com.example.tasks.controller;

import com.example.tasks.entity.Board;
import com.example.tasks.entity.Session;
import com.example.tasks.entity.User;
import com.example.tasks.repository.BoardRepository.BoardRepository;
import com.example.tasks.service.BoardService.BoardService;
import com.example.tasks.service.SessionService.SessionService;
import com.example.tasks.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    BoardService boardService;

    @Autowired
    SessionService sessionService;

    @Autowired
    UserService userService;




    @RequestMapping(method = RequestMethod.POST, path = "/boards/addboard/{name}")
    public Board createBoardForUser(HttpServletResponse response,
                                             @CookieValue(required = false)
                                            String sid,
                                             @PathVariable String name)
            throws IOException {

        Session session = sessionService.getAuthenticatedUser(sid, response);
        if (session == null)
            return null;
        User user = userService.getUser(session.getUid());
        Board board = boardService.createBoardForUser(name,user);



        return board;

    }

}
