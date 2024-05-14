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

import org.springframework.web.bind.annotation.*;
import com.example.proyecto_dbp.Task.domain.Task;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskInputDto taskInputDto) {
        TaskDto taskDto = taskService.createTask(taskInputDto);
        TaskResponseDto responseDto = new TaskResponseDto(taskDto.getId(), taskDto.getDescription());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getTasks() {
        List<TaskDto> taskDtos = taskService.getTasks();
        List<TaskResponseDto> responseDtos = TaskResponseDto.from(taskDtos);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody Task task) {
        taskService.updateTask(id, task);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
