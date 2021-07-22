package com.example.tasks.service.SessionService;

import com.example.tasks.entity.Session;
import com.example.tasks.repository.SessionRepository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class SessionServiceImpl implements SessionService{

    @Autowired
    SessionRepository sessionRepository;

    @Override
    @Transactional
    public Session getBySessionId(String sid) {
        return sessionRepository.getBySessionId(sid);
    }

    @Override
    @Transactional
    public Session createSession(Session session) {
        return sessionRepository.createSession(session);
    }

    @Override
    @Transactional
    public Session getAuthenticatedUser(String sid, HttpServletResponse response) throws IOException {
        return sessionRepository.getAuthenticatedUser(sid, response);
    }
}
