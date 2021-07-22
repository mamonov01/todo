package com.example.tasks.service.TaskService;

import com.example.tasks.entity.Task;
import com.example.tasks.repository.TaskRepository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    TaskRepository taskRepository;


    @Override
    @Transactional
    public List<Task> getTasksOfBoard(int id) {
        return taskRepository.getTasksOfBoard(id);
    }

    @Override
    @Transactional
    public Task addTaskInBoard(int id, String nameOfTask) {
    return  taskRepository.addTaskInBoard(id,nameOfTask);
    }

    @Override
    @Transactional
    public Task getTask(int id) {
        return taskRepository.getTask(id);
    }

    @Override
    @Transactional
    public void deleteTaskById(int id) {
        taskRepository.deleteTaskById(id);
    }

    @Override
    @Transactional
    public void deleteAllTasksOfBoard(int boardId) {
        taskRepository.deleteAllTasksOfBoard(boardId);
    }

    @Override
    @Transactional
    public Task toggleCompleted(int taskId) {
        return taskRepository.toggleCompleted(taskId);
    }

    @Override
    @Transactional
    public void saveOrUpdateTask(Task t) {
        taskRepository.saveOrUpdateTask(t);
    }
}
