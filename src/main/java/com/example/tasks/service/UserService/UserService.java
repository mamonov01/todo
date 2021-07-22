package com.example.tasks.service.UserService;

import com.example.tasks.entity.Session;
import com.example.tasks.entity.User;

public interface UserService {

    public void saveUser (User user);

    public void deleteUser(int id);

    public User getUser(int id);

    public Session auth(String login, String password);

}
