package com.example.proyecto_dbp.Activity.application;

import com.example.proyecto_dbp.Activity.domain.ActivityService;
import com.example.proyecto_dbp.Activity.dto.ActivityDTO;

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
    public List<ActivityDTO> getAllActivities() {
        return activityService.getAllActivities();
    }

    @PostMapping
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody ActivityDTO activityDTO) {
        ActivityDTO createdActivity = activityService.createActivity(activityDTO);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    // Other CRUD operations
}
