package com.example.tasks.service.BoardService;

import com.example.tasks.entity.Board;
import com.example.tasks.entity.User;
import com.example.tasks.repository.BoardRepository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardRepository boardRepository;


    @Override
    @Transactional
    public List<Board> getBoardsOfUser(int id) {
        return boardRepository.getBoardsOfUser(id);
    }

    @Override
    @Transactional
    public Board createBoardForUser(String nameOfBoard, User user) {
        return boardRepository.createBoardForUser(nameOfBoard, user);
    }

    @Override
    @Transactional
    public Board getBoard(int id) {
        return boardRepository.getBoard(id);
    }

    @Override
    @Transactional
    public void deleteBoard(int id) {
        boardRepository.deleteBoard(id);
    }

    @Override
    @Transactional
    public void deleteAllBoardsOfUser(int userId) {
        boardRepository.deleteAllBoardsOfUser(userId);
    }

    @Override
    @Transactional
    public void saveOrUpdateBoard(Board board) {
        boardRepository.saveOrUpdateBoard(board);
    }
}
