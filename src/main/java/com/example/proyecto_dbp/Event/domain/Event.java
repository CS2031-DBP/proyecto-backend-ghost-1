package com.example.proyecto_dbp.Event.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Event extends Activity {

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private boolean allDay;

    @Column(nullable = false)
    private String organizer;

    @Column(nullable = false)
    private String attendees;

    @Column(nullable = false)
    private String reminder;
}
