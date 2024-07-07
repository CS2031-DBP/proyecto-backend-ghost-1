package com.example.proyecto_dbp.Task.domain;

import com.example.proyecto_dbp.Task.infrastructure.TaskRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> getAllTasks(PageRequest pageRequest) {
        return taskRepository.findAll(pageRequest);
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


    public Task createTask(Task task) {return taskRepository.save(task);}

    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        task.setTitulo(updatedTask.getTitulo());
        task.setDescripcion(updatedTask.getDescripcion());
        task.setFechaInicio(updatedTask.getFechaInicio());
        task.setFechaFin(updatedTask.getFechaFin());
        task.setEstado(updatedTask.getEstado());
        task.setPriority(updatedTask.getPriority());
        task.setCompleted(updatedTask.getCompleted());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) throw new ResourceNotFoundException("Task not found with id " + id);
        taskRepository.deleteById(id);
    }
}
