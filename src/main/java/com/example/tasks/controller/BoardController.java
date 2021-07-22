package com.example.tasks.controller;


import com.example.tasks.entity.*;
import com.example.tasks.repository.SessionRepository.SessionRepository;
import com.example.tasks.service.BoardService.BoardService;
import com.example.tasks.service.SessionService.SessionService;
import com.example.tasks.service.TaskService.TaskService;
import com.example.tasks.service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Random;


@Controller
@RequestMapping("/")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @GetMapping("/boards/")
    public String getBoards(Model model,
                            HttpServletResponse response,
                            @CookieValue(required = false) String sid) throws IOException {

        Session session = sessionService.getAuthenticatedUser(sid, response);
        if (session == null)
            return null;
        int userId = session.getUid();

        Map<String, Object> modelMap = new ModelMap();
        modelMap.put("r", new Random().nextInt());
        modelMap.put("boards", boardService.getBoardsOfUser(userId));
        modelMap.put("boardName", "Select any board");
        modelMap.put("loginData", new LoginData());
        modelMap.put("userId", userId);
        model.addAllAttributes(modelMap);

        return "index";
    }


    @GetMapping("/boards/{boardId}")
    public String getBoardsOfUser(Model model,
                                  HttpServletResponse response,
                                  @PathVariable int boardId,
                                  @CookieValue(required = false) String sid) throws IOException {

        Session session = sessionService.getAuthenticatedUser(sid, response);
        if (session == null)
            return null;
        int userId = session.getUid();

        Board board = boardService.getBoard(boardId);
        Map<String, Object> modelMap = new ModelMap();
        modelMap.put("r", new Random().nextInt());
        modelMap.put("boards", boardService.getBoardsOfUser(userId));
        if (board.getUser().getId() == userId) {
            modelMap.put("tasks", taskService.getTasksOfBoard(boardId));
            modelMap.put("boardName", board.getName());
        }
        modelMap.put("loginData", new LoginData());

        model.addAllAttributes(modelMap);

        return "index";
    }

    @DeleteMapping("/boards/{boardId}")
    public void deleteBoard(HttpServletResponse response,
                            @PathVariable int boardId,
                            @CookieValue(required = false) String sid) throws IOException {

        Session session = sessionService.getAuthenticatedUser(sid, response);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        int userId = session.getUid();

        Board board = boardService.getBoard(boardId);
        if (board.getUser().getId() == userId){
            boardService.deleteBoard(boardId);

        }
        else response.sendError(HttpServletResponse.SC_FORBIDDEN);


    }

    @PostMapping("/boards/{boardId}")
    public void updateBoard(
            HttpServletResponse response,
            @PathVariable int boardId,
            @CookieValue(required = false) String sid,
            @ModelAttribute NameAccept newName
    ) throws IOException {

        Session session = sessionService.getAuthenticatedUser(sid, response);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Board board = boardService.getBoard(boardId);

        if (board.getUser().getId() != session.getUid()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        board.setName(newName.getName());
        boardService.saveOrUpdateBoard(board);


    }


}
