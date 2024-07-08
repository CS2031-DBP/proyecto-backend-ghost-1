package com.example.proyecto_dbp.Task.application;

import com.example.proyecto_dbp.Course.domain.Course;
import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.Task.domain.Task;
import com.example.proyecto_dbp.Task.domain.TaskService;
import com.example.proyecto_dbp.Task.dto.TaskDTO;
import com.example.proyecto_dbp.Task.infrastructure.TaskRepository;
import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.domain.UserService;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.getAllTasks(pageable);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/course/{courseId}")
    public List<Task> getTasksByCourseId(@PathVariable Long courseId) {
        return taskService.getTasksByCourseId(courseId);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + taskDTO.getUserId()));

        Course course = new Course();
        course.setNombreCurso(taskDTO.getCourseName());
        course.setDescripcion(taskDTO.getDescripcion());
        course.setUser(user);

        Task task = new Task();
        task.setTitulo(taskDTO.getTitulo());
        task.setDescripcion(taskDTO.getDescripcion());
        task.setFechaInicio(taskDTO.getFechaInicio());
        task.setFechaFin(taskDTO.getFechaFin());
        task.setEstado(taskDTO.getEstado());
        task.setPriority(taskDTO.getPriority());
        task.setCompleted(taskDTO.getCompleted());
        task.setCourse(course);

        courseRepository.save(course);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
