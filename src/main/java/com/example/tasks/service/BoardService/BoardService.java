package com.example.tasks.service.BoardService;

import com.example.tasks.entity.Board;
import com.example.tasks.entity.User;

import java.util.List;

public interface BoardService {

    public List<Board> getBoardsOfUser(int id);

    public Board createBoardForUser(String nameOfBoard, User user);

    public Board getBoard(int id);

    public void deleteBoard(int id);

    public void deleteAllBoardsOfUser(int userId);

    public void saveOrUpdateBoard(Board board);

}
