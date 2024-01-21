package com.example.notificationservice.repository;

import com.example.notificationservice.domain.Email;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EmailDao {
    private final List<Email> emails;

    public EmailDao() {
        emails = new ArrayList<>();
    }

    public List<Email> getEmailsForSend(ZonedDateTime onTime) {
        var emailsToSend = emails.stream()
                .filter(email -> onTime.isAfter(email.getNextAttempt()))
                .collect(Collectors.toList());
        emails.removeAll(emailsToSend);
        return emailsToSend;
    }

    public void addEmail(Email email) {
        emails.add(email);
    }
    public void addEmails(List<Email> emails) {
        this.emails.addAll(emails);
    }
}
