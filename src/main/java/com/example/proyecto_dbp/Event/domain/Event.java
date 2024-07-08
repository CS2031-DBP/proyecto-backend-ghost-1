package com.example.proyecto_dbp.Event.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.Course.domain.Course;
import jakarta.persistence.*;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
