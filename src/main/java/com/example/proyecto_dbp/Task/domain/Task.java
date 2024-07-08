package com.example.proyecto_dbp.Task.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks")
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
public class Task extends Activity {
    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private Boolean completed;


    private Long courseId;
}
