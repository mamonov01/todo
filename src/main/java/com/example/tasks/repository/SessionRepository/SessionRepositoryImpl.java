package com.example.tasks.repository.SessionRepository;

import com.example.tasks.RandomString;
import com.example.tasks.entity.Session;
import com.example.tasks.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Repository
public class SessionRepositoryImpl implements SessionRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Session getBySessionId(String sid) {
        Query query = entityManager.createQuery("SELECT s FROM Session s WHERE s.sid=:getSid");
        query.setParameter("getSid", sid);
        List<Session> session = query.getResultList();
        if (session.isEmpty())
            return null;
        return session.get(0);
    }

    @Override
    public Session createSession(Session session) {
        RandomString rs = new RandomString(127);
        session.setSid(rs.nextString());
        entityManager.merge(session);
        return session;
    }

    @Override
    public Session getAuthenticatedUser(String sid, HttpServletResponse response) throws IOException {
        Session session = getBySessionId(sid);
        if (session == null) {
            response.sendRedirect("/auth");
        }
        return session;
    }
}
