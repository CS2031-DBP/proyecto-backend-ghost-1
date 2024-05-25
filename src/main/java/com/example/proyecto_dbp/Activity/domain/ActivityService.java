package com.example.proyecto_dbp.Activity.domain;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import com.example.proyecto_dbp.Activity.domain.Activity;

import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
            activity.setTitle(activityDTO.getTitulo());
            activity.setDescription(activityDTO.getDescripcion());
            activity.setStartTime(activityDTO.getFechaInicio());
            activity.setEndTime(activityDTO.getFechaFin());
            activity.setStatus(activityDTO.getEstado());
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
        activityDTO.setTitulo(activity.getTitle());
        activityDTO.setDescripcion(activity.getDescription());
        activityDTO.setFechaInicio(activity.getStartTime());
        activityDTO.setFechaFin(activity.getEndTime());
        activityDTO.setEstado(activity.getStatus());
        activityDTO.setCourseId(activity.getCourse().getCourseid());
        return activityDTO;
    }

    private Activity convertToEntity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setTitle(activityDTO.getTitulo());
        activity.setDescription(activityDTO.getDescripcion());
        activity.setStartTime(activityDTO.getFechaInicio());
        activity.setEndTime(activityDTO.getFechaFin());
        activity.setStatus(activityDTO.getEstado());
        courseRepository.findById(activityDTO.getCourseId()).ifPresent(activity::setCourse);
        return activity;
    }
}
