package com.example.proyecto_dbp.CalendarIntegration.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import jakarta.persistence.*;

@Entity
@Table(name = "calendar_integrations")
public class CalendarIntegration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @Column(nullable = false)
    private String googleCalendarEventId;

    @Column(nullable = false)
    private String synchronizationStatus;

    @OneToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    private Activity activity;

    // Getters and Setters


    public Long getActivityId() {return activityId;}

    public void setActivityId(Long activityId) {this.activityId = activityId;}

    public String getGoogleCalendarEventId() {return googleCalendarEventId;}

    public void setGoogleCalendarEventId(String googleCalendarEventId) {this.googleCalendarEventId = googleCalendarEventId;}

    public String getSynchronizationStatus() {return synchronizationStatus;}

    public void setSynchronizationStatus(String synchronizationStatus) {this.synchronizationStatus = synchronizationStatus;}

    public Activity getActivity() {return activity;}

    public void setActivity(Activity activity) {this.activity = activity;}
}