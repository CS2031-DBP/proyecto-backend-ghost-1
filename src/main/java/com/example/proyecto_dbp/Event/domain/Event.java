package com.example.proyecto_dbp.Event.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
public class Event extends Activity {
    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Boolean allDay;

    @Column(nullable = false)
    private String organizer;

    @ElementCollection
    private List<String> attendees;

    @Column(nullable = false)
    private String reminder;
}
