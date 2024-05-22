package com.example.proyecto_dbp.CalendarIntegration.infrastructure;

import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarIntegrationRepository extends JpaRepository<CalendarIntegration, Long> {
}
