package com.example.tasks.controller;

import com.example.tasks.entity.*;
import com.example.tasks.repository.SessionRepository.SessionRepository;
import com.example.tasks.service.BoardService.BoardService;
import com.example.tasks.service.SessionService.SessionService;
import com.example.tasks.service.TaskService.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    BoardService boardService;

    @Autowired
    SessionService sessionService;

    @PostMapping("/tasks/{taskId}/toggle")
    public Object toggleCompletedState(Model model,
                                       HttpServletResponse response,
                                       @PathVariable int taskId,
                                       @CookieValue(required = false) String sid) throws IOException {

        Session session = sessionService.getAuthenticatedUser(sid, response);
        if (session == null)
            return null;
        Task task = taskService.getTask(taskId);
        Board board = task.getBoard();

        if (board.getUser().getId() != session.getUid())
            return "{\"error\": {\"msg\": \"Access denied\"}}";

        task = taskService.toggleCompleted(taskId);

       return new Completeness(task.isCompleteness());
    }


    @RequestMapping(method = RequestMethod.POST, path = "/boards/{id}/add/{name}")
    public Task addTaskInBoard(@PathVariable int id, @PathVariable String name,
                               HttpServletResponse response,
                               @CookieValue(required = false) String sid)
            throws IOException {
        Session session = sessionService.getAuthenticatedUser(sid, response);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        Board board = boardService.getBoard(id);
        if (board.getUser().getId() == session.getUid()) {
            return taskService.addTaskInBoard(id, name);
        }
        else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
    }


    @PostMapping("/tasks/{taskId}")
    public void updateTask(
            HttpServletResponse response,
            @PathVariable int taskId,
            @CookieValue(required = false) String sid,
            @ModelAttribute NameAccept newName
    ) throws IOException {

        Session session = sessionService.getAuthenticatedUser(sid, response);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Task task = taskService.getTask(taskId);
        Board board = task.getBoard();

        if (board.getUser().getId() != session.getUid()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        task.setText(newName.getName());
        taskService.saveOrUpdateTask(task);

    }


    public Task getTask(int id) {
        return taskService.getTask(id);
    }


    @DeleteMapping("/tasks/{taskId}")
    public void deleteTaskById(HttpServletResponse response,
                               @PathVariable int taskId,
                               @CookieValue(required = false) String sid) throws IOException {

        Session session = sessionService.getAuthenticatedUser(sid, response);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Task task = taskService.getTask(taskId);
        Board board = task.getBoard();

        if (board.getUser().getId() != session.getUid()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        taskService.deleteTaskById(taskId);
    }

}
