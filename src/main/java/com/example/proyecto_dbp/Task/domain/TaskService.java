package com.example.proyecto_dbp.Task.domain;

import com.example.proyecto_dbp.Task.dto.TaskDto;
import com.example.proyecto_dbp.Task.dto.TaskInputDto;
import com.example.proyecto_dbp.Task.infrastructure.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    @Transactional
    public TaskDto createTask(TaskInputDto inputDto) {
        Task task = new Task();
        task.setDescription(inputDto.getDescription());
        task = taskRepository.save(task);
        return new TaskDto(task.getId(), task.getDescription());
    }

}
