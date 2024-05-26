package com.example.proyecto_dbp.Task.domain;

import com.example.proyecto_dbp.Task.dto.TaskDTO;
import com.example.proyecto_dbp.Task.domain.Task;
import com.example.proyecto_dbp.Task.infrastructure.TaskRepository;
import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .filter(task -> task.getCourse().getCourseid().equals(courseId))
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
        task.setTitulo(taskDTO.getTitulo());
        task.setDescripcion(taskDTO.getDescripcion());
        task.setFechaInicio(taskDTO.getFechaInicio());
        task.setFechaFin(taskDTO.getFechaFin());
        task.setEstado(taskDTO.getEstado());
        task.setPriority(taskDTO.getPriority());
        task.setCompleted(taskDTO.getCompleted());
        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) throw new ResourceNotFoundException("Task not found with id " + id);
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitulo(task.getTitulo());
        taskDTO.setDescripcion(task.getDescripcion());
        taskDTO.setFechaInicio(task.getFechaInicio());
        taskDTO.setFechaFin(task.getFechaFin());
        taskDTO.setEstado(task.getEstado());
        taskDTO.setPriority(task.getPriority());
        taskDTO.setCompleted(task.getCompleted());
        return taskDTO;
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitulo(taskDTO.getTitulo());
        task.setDescripcion(taskDTO.getDescripcion());
        task.setFechaInicio(taskDTO.getFechaInicio());
        task.setFechaFin(taskDTO.getFechaFin());
        task.setEstado(taskDTO.getEstado());
        task.setPriority(taskDTO.getPriority());
        task.setCompleted(taskDTO.getCompleted());
        courseRepository.findById(taskDTO.getCourseId()).ifPresent(task::setCourse);
        return task;
    }
}
