package com.example.tasks.service.UserService;


import com.example.tasks.entity.Session;
import com.example.tasks.entity.User;
import com.example.tasks.repository.SessionRepository.SessionRepository;
import com.example.tasks.repository.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;


    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userRepository.getUser(id);
    }

    @Override
    @Transactional
    public Session auth(String login, String password) {
        User user = userRepository.getUserByLogin(login, password);
        if (user == null)
            return null;
        Session session = new Session(user.getId(), null);
        return sessionRepository.createSession(session);
    }
}
