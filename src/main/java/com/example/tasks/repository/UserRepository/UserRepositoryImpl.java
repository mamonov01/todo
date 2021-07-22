package com.example.tasks.repository.UserRepository;

import com.example.tasks.entity.Board;
import com.example.tasks.entity.Task;
import com.example.tasks.entity.User;
import com.example.tasks.repository.BoardRepository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public void saveUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(int id) {

        List<Board> boardList = boardRepository.getBoardsOfUser(id);

        for (Board board: boardList
        ) {
            boardRepository.deleteBoard(board.getId());
        }

        Query query = entityManager.createQuery("delete from User where id =:userId");
        query.setParameter("userId", id);
        query.executeUpdate();
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public User getUserByLogin(String login, String password) {

        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.login=:login AND u.password=:password");
        query.setParameter("login", login);
        query.setParameter("password",password);
        List<User> user = query.getResultList();

        if (user.isEmpty())
            return null;
        return user.get(0);

    }
}
