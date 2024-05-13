package com.example.proyecto_dbp.Task.application;


import com.example.proyecto_dbp.Task.domain.TaskService;
import com.example.proyecto_dbp.Task.dto.TaskDto;
import com.example.proyecto_dbp.Task.dto.TaskInputDto;
import com.example.proyecto_dbp.Task.dto.TaskResponseDto;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.proyecto_dbp.Task.domain.Task;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task newTask = taskService.saveTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskInputDto taskInputDto) {
        TaskDto taskDto = taskService.createTask(taskInputDto);
        TaskResponseDto responseDto = new TaskResponseDto(taskDto.getId(), taskDto.getDescription());
        return ResponseEntity.ok(responseDto);
    }
}
