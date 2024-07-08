package com.example.proyecto_dbp.Task.domain;

import com.example.proyecto_dbp.Course.domain.Course;
import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.Task.infrastructure.TaskRepository;
import com.example.proyecto_dbp.Task.dto.TaskDTO;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
    }

    public List<Task> getTasksByCourseId(Long courseId) {
        List<Task> tasks = taskRepository.findByCourseId(courseId);
        if (tasks.isEmpty()) throw new ResourceNotFoundException("No tasks found for course with id " + courseId);
        return tasks;
    }

    public Task createTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitulo(taskDTO.getTitulo());
        task.setDescripcion(taskDTO.getDescripcion());
        task.setFechaInicio(taskDTO.getFechaInicio());
        task.setFechaFin(taskDTO.getFechaFin());
        task.setEstado(taskDTO.getEstado());
        task.setPriority(taskDTO.getPriority());
        task.setCompleted(taskDTO.getCompleted());

        Course course = courseRepository.findById(taskDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + taskDTO.getCourseId()));
        task.setCourse(course);

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        task.setTitulo(taskDTO.getTitulo());
        task.setDescripcion(taskDTO.getDescripcion());
        task.setFechaInicio(taskDTO.getFechaInicio());
        task.setFechaFin(taskDTO.getFechaFin());
        task.setEstado(taskDTO.getEstado());
        task.setPriority(taskDTO.getPriority());
        task.setCompleted(taskDTO.getCompleted());

        Course course = courseRepository.findById(taskDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + taskDTO.getCourseId()));
        task.setCourse(course);

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) throw new ResourceNotFoundException("Task not found with id " + id);
        taskRepository.deleteById(id);
    }
}
