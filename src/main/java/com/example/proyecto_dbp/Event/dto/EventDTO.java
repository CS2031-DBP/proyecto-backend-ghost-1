package com.example.proyecto_dbp.Event.dto;

import lombok.*;

@Data
public class EventDTO {
    private String location;
    private Boolean allDay;
    private String organizer;
    private String attendees;
    private String reminder;

}
