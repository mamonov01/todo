package com.example.tasks.service.TaskService;

import com.example.tasks.entity.Task;

import java.util.List;

public interface TaskService {

    public List<Task> getTasksOfBoard(int id);

    public Task addTaskInBoard(int id,String nameOfTask);

    public Task getTask(int id);

    public void deleteTaskById(int id);

    public void deleteAllTasksOfBoard(int boardId);

    public Task toggleCompleted(int taskId);

    public void saveOrUpdateTask(Task t);
}
