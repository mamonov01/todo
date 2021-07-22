package com.example.tasks.repository.BoardRepository;

import com.example.tasks.entity.Board;

import com.example.tasks.entity.User;
import com.example.tasks.repository.TaskRepository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    @Autowired
    private EntityManager entityManager;


    @Autowired
    private TaskRepository taskRepository;


    @Override
    public List<Board> getBoardsOfUser(int id) {

        List<Board> boards = entityManager.createQuery("select br " +
                "from Board br " +
                "where br.user.id = :userId").setParameter("userId", id).getResultList();

        return boards;
    }

    @Override
    public Board createBoardForUser(String nameOfBoard, User user) {

        Board board = new Board();
        board.setName(nameOfBoard);
        board.setUser(user);
        entityManager.persist(board);

        return board;
    }

    @Override
    public Board getBoard(int id) {
        return entityManager.find(Board.class, id);
    }

    @Override
    public void deleteBoard(int id) {
        taskRepository.deleteAllTasksOfBoard(id);

        Query query = entityManager.createQuery("delete from Board where id =:boardId");
        query.setParameter("boardId", id);
        query.executeUpdate();
    }

    @Override
    public void deleteAllBoardsOfUser(int userId) {

        List<Board> boards = getBoardsOfUser(userId);
        for (Board board : boards) {
            deleteBoard(board.getId());
        }

        Query query = entityManager.createQuery("delete from Board br " +
                "where br.user.id = :userId");
        query.setParameter("userId", userId);
        query.executeUpdate();
    }


    @Override
    public void saveOrUpdateBoard(Board board) {
        entityManager.merge(board);
    }
}
