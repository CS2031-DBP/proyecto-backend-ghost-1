package com.example.proyecto_dbp.Event.events;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class EmailEventListener {

    @Autowired
    private EmailService emailService;

    @EventListener
    @Async
    public void handleEventCreatedEvent(EventCreatedEvent event) throws MessagingException {
        String htmlBody = "<h1>Confirmación de Evento</h1><p>Tu evento ha sido creado exitosamente</p>";
        emailService.sendHtmlMessage(event.getEmail(), "Confirmación de Evento", htmlBody);
    }
}
