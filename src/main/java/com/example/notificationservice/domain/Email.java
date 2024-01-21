package com.example.notificationservice.domain;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;

@Data
public class Email {
    @NotNull
    private Long templateId;
    @NotNull
    private EmailStatus status;
    @NotNull
    private Set<Recipient> recipients;
    private Map<String, String> parameters;
    @NotNull
    private ZonedDateTime nextAttempt;
    @NotNull
    private Integer sendingAttempts;

    public Email(
            @NotNull Long templateId,
            @NotNull Set<Recipient> recipients,
            Map<String, String> parameters,
            @NotNull ZonedDateTime nextAttempt
    ) {
        this.templateId = templateId;
        this.recipients = recipients;
        this.parameters = parameters;
        this.nextAttempt = nextAttempt;
        sendingAttempts = 0;
        status = EmailStatus.WAITING_FOR_SEND;
    }

    public void increaseAttemptsCount() {
        sendingAttempts++;
    }
}
