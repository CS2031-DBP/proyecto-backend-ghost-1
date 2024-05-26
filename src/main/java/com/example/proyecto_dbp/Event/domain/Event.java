package com.example.proyecto_dbp.Event.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Entity
@Setter
@Getter
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
