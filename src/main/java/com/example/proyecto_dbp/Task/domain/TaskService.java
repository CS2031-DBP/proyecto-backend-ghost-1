package com.example.proyecto_dbp.Task.domain;

import com.example.proyecto_dbp.Task.infrastructure.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

}
