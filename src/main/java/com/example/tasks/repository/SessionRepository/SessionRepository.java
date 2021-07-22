package com.example.tasks.repository.SessionRepository;

import com.example.tasks.entity.Session;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SessionRepository {

    public Session getBySessionId(String sid);

    public Session createSession(Session session);

    public Session getAuthenticatedUser(String sid, HttpServletResponse response) throws IOException;

}
