package com.example.tasks.repository.TaskRepository;

import com.example.tasks.entity.Task;

import java.util.List;

public interface TaskRepository {

    public List<Task> getTasksOfBoard(int id);

    public Task addTaskInBoard(int id, String nameOfTask);

    public Task getTask(int id);

    public void deleteTaskById(int id);

    public void deleteAllTasksOfBoard(int boardId);

    public Task toggleCompleted(int taskId);

    public void saveOrUpdateTask(Task t);
}
