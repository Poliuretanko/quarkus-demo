package com.example.notificationservice.scheduler;

import com.example.notificationservice.service.EmailService;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Singleton;

@Singleton
public class EmailSender {
    private final EmailService emailService;

    public EmailSender(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(every = "1s")
    public void sendEmails() {
        emailService.sendNotifications();
    }
}
