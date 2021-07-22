package com.example.tasks.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "uid")
    private int uid;

    @Column(name = "sid")
    private String sid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Session() {
    }

    public Session(int uid, String sid) {
        this.uid = uid;
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", uid=" + uid +
                ", sid='" + sid + '\'' +
                '}';
    }
}
