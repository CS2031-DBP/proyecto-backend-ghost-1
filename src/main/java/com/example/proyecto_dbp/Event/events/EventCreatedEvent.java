package com.example.proyecto_dbp.Event.events;

import org.springframework.context.ApplicationEvent;

public class EventCreatedEvent extends ApplicationEvent {
    private final String email;

    public EventCreatedEvent(Object source, String email) {
        super(source);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
