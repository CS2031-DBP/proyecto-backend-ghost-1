package com.example.proyecto_dbp.Event.events;

import org.springframework.context.ApplicationEvent;

public class HelloEmailEvent extends ApplicationEvent {
    private final String email;

    public HelloEmailEvent(String email) {
        super(email);
        this.email = email;
    }

    public String getEmail() {return email;}
}