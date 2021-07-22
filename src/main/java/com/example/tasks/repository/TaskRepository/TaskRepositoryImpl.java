package com.example.tasks.repository.TaskRepository;

import com.example.tasks.entity.Board;
import com.example.tasks.entity.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Task> getTasksOfBoard(int id) {
        List<Task> tasks = entityManager.createQuery("select ts " +
                "from Task ts " +
                "where ts.board.id = :boardId order by ts.id asc").setParameter("boardId", id).getResultList();
        return tasks;
    }

    @Override
    public Task addTaskInBoard(int id, String nameOfTask) {

        Board board = entityManager.find(Board.class, id);
        Task task = new Task();
        task.setText(nameOfTask);
        task.setCompleteness(false);
        task.setBoard(board);
        entityManager.persist(task);
        return task;

    }

    @Override
    public Task getTask(int id) {
        return entityManager.find(Task.class, id);
    }


    @Override
    public void deleteTaskById(int id) {
        Query query = entityManager.createQuery("delete from Task where id =:taskId");
        query.setParameter("taskId", id);
        query.executeUpdate();
    }

    @Override
    public void deleteAllTasksOfBoard(int boardId) {
        Query query = entityManager.createQuery("delete from Task ts " +
                "where ts.board.id = :boardId");
        query.setParameter("boardId", boardId);
        query.executeUpdate();
    }

    @Override
    public Task toggleCompleted(int taskId) {

        Task task = getTask(taskId);
        task.setCompleteness(!task.isCompleteness());
        entityManager.merge(task);

        return task;
    }

    @Override
    public void saveOrUpdateTask(Task t) {
        entityManager.merge(t);
    }
}