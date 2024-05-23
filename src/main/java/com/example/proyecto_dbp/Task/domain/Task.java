package com.example.proyecto_dbp.Task.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task extends Activity {
    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private Boolean completed;

}
