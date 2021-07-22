package com.example.tasks.repository.BoardRepository;

import com.example.tasks.entity.Board;
import com.example.tasks.entity.User;

import java.util.List;

public interface BoardRepository {

    public List<Board> getBoardsOfUser(int id);

    public Board createBoardForUser(String nameOfBoard, User user);

    public Board getBoard(int id);

    public void deleteBoard(int id);

    public void deleteAllBoardsOfUser(int userId);

    public void saveOrUpdateBoard(Board board);


}
