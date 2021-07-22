package com.example.tasks.repository.UserRepository;

import com.example.tasks.entity.User;

public interface UserRepository {

    public void saveUser (User user);

    public void deleteUser(int id);

    public User getUser(int id);

    public User getUserByLogin(String login, String password);

}
