package com.example.proyecto_dbp.Task.domain;

import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.Task.dto.TaskDTO;
import com.example.proyecto_dbp.Task.infrastructure.TaskRepository;
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

    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id).map(this::convertToDTO);
    }

    public List<TaskDTO> getTasksByCourseId(Long courseId) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getCourse().getCourse_id().equals(courseId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
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
        return null;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitulo(task.getTitle());
        taskDTO.setDescripcion(task.getDescription());
        taskDTO.setFechaInicio(task.getStartTime());
        taskDTO.setFechaFin(task.getEndTime());
        taskDTO.setEstado(task.getStatus());
        taskDTO.setCourseId(task.getCourse().getCourse_id());
        taskDTO.setPriority(task.getPriority());
        taskDTO.setCompleted(task.getCompleted());
        return taskDTO;
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
