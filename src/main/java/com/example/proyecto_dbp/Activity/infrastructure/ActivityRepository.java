package com.example.proyecto_dbp.Activity.infrastructure;

import com.example.proyecto_dbp.Activity.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<List<Activity>> findByCourseId(Long courseId);
}
