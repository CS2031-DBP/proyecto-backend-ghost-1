package com.example.proyecto_dbp.Task.domain;

import com.example.proyecto_dbp.Task.dto.TaskDTO;
import com.example.proyecto_dbp.Task.domain.Task;
import com.example.proyecto_dbp.Task.infrastructure.TaskRepository;
import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        return convertToDTO(task);
    }

    public List<TaskDTO> getTasksByCourseId(Long courseId) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getCourse().getId().equals(courseId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        task.setTitle(taskDTO.getTitulo());
        task.setDescription(taskDTO.getDescripcion());
        task.setStartTime(taskDTO.getFechaInicio());
        task.setEndTime(taskDTO.getFechaFin());
        task.setStatus(taskDTO.getEstado());
        task.setPriority(taskDTO.getPriority());
        task.setCompleted(taskDTO.getCompleted());
        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .titulo(task.getTitle())
                .descripcion(task.getDescription())
                .fechaInicio(task.getStartTime())
                .fechaFin(task.getEndTime())
                .estado(task.getStatus())
                .courseId(task.getCourse().getId())
                .priority(task.getPriority())
                .completed(task.getCompleted())
                .build();
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitulo());
        task.setDescription(taskDTO.getDescripcion());
        task.setStartTime(taskDTO.getFechaInicio());
        task.setEndTime(taskDTO.getFechaFin());
        task.setStatus(taskDTO.getEstado());
        task.setPriority(taskDTO.getPriority());
        task.setCompleted(taskDTO.getCompleted());
        courseRepository.findById(taskDTO.getCourseId()).ifPresent(task::setCourse);
        return task;
    }
}
