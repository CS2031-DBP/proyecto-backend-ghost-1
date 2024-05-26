package com.example.proyecto_dbp.Activity.domain;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<ActivityDTO> getActivityById(Long id) {
        return activityRepository.findById(id).map(this::convertToDTO);
    }

    public List<ActivityDTO> getActivitiesByCourseId(Long courseId) {
        return activityRepository.findAll().stream()
                .filter(activity -> activity.getCourse().getCourseid().equals(courseId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        Activity activity = convertToEntity(activityDTO);
        activity = activityRepository.save(activity);
        return convertToDTO(activity);
    }

    public ActivityDTO updateActivity(Long id, ActivityDTO activityDTO) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            activity.setTitulo(activityDTO.getTitulo());
            activity.setDescripcion(activityDTO.getDescripcion());
            activity.setFechaInicio(activityDTO.getFechaInicio());
            activity.setFechaFin(activityDTO.getFechaFin());
            activity.setEstado(activityDTO.getEstado());
            activity = activityRepository.save(activity);
            return convertToDTO(activity);
        }
        return null;
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    private ActivityDTO convertToDTO(Activity activity) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setId(activity.getId());
        activityDTO.setTitulo(activity.getTitulo());
        activityDTO.setDescripcion(activity.getDescripcion());
        activityDTO.setFechaInicio(activity.getFechaInicio());
        activityDTO.setFechaFin(activity.getFechaFin());
        activityDTO.setEstado(activity.getEstado());
        activityDTO.setCourseId(activity.getCourse().getCourseid());
        return activityDTO;
    }

    private Activity convertToEntity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setTitulo(activityDTO.getTitulo());
        activity.setDescripcion(activityDTO.getDescripcion());
        activity.setFechaInicio(activityDTO.getFechaInicio());
        activity.setFechaFin(activityDTO.getFechaFin());
        activity.setEstado(activityDTO.getEstado());
        courseRepository.findById(activityDTO.getCourseId()).ifPresent(activity::setCourse);
        return activity;
    }
}
