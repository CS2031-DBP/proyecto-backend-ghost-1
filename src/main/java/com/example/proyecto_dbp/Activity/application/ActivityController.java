package com.example.proyecto_dbp.Activity.application;

import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.Activity.domain.ActivityService;
import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getActivityById(id);
        return ResponseEntity.ok(activity);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Activity>> getActivitiesByCourseId(@PathVariable Long courseId) {
        List<Activity> activities = activityService.getActivitiesByCourseId(courseId);
        return ResponseEntity.ok(activities);
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody @Valid Activity activity) {
        Activity createdActivity = activityService.createActivity(activity);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody @Valid Activity activity) {
        Activity updatedActivity = activityService.updateActivity(id, activity);
        return ResponseEntity.ok(updatedActivity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
