package com.example.proyecto_dbp.Activity.domain;

import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getAllActivities() {return activityRepository.findAll();}

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id " + id));
    }

    public List<Activity> getActivitiesByCourseId(Long courseId) {
        return activityRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("No activities found for course with id " + courseId));
    }

    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity updateActivity(Long id, Activity updatedActivity) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id " + id));

        activity.setTitulo(updatedActivity.getTitulo());
        activity.setDescripcion(updatedActivity.getDescripcion());
        activity.setFechaInicio(updatedActivity.getFechaInicio());
        activity.setFechaFin(updatedActivity.getFechaFin());
        activity.setEstado(updatedActivity.getEstado());
        return activityRepository.save(activity);
    }

    public void deleteActivity(Long id) {
        if (!activityRepository.existsById(id)) throw new ResourceNotFoundException("Activity not found with id " + id);
        activityRepository.deleteById(id);
    }
}
