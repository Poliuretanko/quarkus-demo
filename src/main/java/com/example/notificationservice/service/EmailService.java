package com.example.notificationservice.service;

import com.example.notificationservice.client.BrevoClient;
import com.example.notificationservice.domain.Email;
import com.example.notificationservice.domain.EmailStatus;
import com.example.notificationservice.dto.EmailDto;
import com.example.notificationservice.repository.EmailDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
public class EmailService {

    @Inject
    private BrevoClient brevoClient;
    @Inject
    private EmailDao emailDao;
    @Inject
    private DelayService delayService;
    private static final Integer MAX_ATTEMPTS = 5;

    public void saveNotification(EmailDto emailDto) {
        log.info("Saving a email message {}", emailDto);
        var nextAttemptTime = ZonedDateTime.now();
        var email = emailDto.convertToEmail(nextAttemptTime);
        emailDao.addEmail(email);
    }

    public void sendNotifications() {
        var now = ZonedDateTime.now();
        var emailsToSend = emailDao.getEmailsForSend(now);
        var emailsToResend = new ArrayList<Email>();

        emailsToSend.forEach(email -> {
            sendNotification(email);
            if (email.getStatus().equals(EmailStatus.FAILED_TO_SEND)) {
                email.setStatus(EmailStatus.WAITING_FOR_SEND);
                emailsToResend.add(email);
            }
        });

        emailDao.addEmails(emailsToResend);
    }

    void sendNotification(Email email) {
        var emailDto = EmailDto.from(email);

        try {
            log.info("Sending email {}", emailDto);
            brevoClient.sendEmail(emailDto);

            email.setStatus(EmailStatus.SENT);
        } catch (Exception e) {
            log.info("Failed to send email {} on {} attempt. The error {}",
                    emailDto, email.getSendingAttempts(), e.getMessage());
            if (email.getSendingAttempts().equals(MAX_ATTEMPTS)) {
                log.info("Abandoning email {}; Attempts are over", emailDto);
                email.setStatus(EmailStatus.ATTEMPTS_ARE_OVER);
                return;
            }

            var now = ZonedDateTime.now();
            var delay = delayService.calculateDelay(email.getSendingAttempts());
            email.increaseAttemptsCount();
            var nextAttempt = now.plusSeconds(delay);
            email.setNextAttempt(nextAttempt);
            email.setStatus(EmailStatus.FAILED_TO_SEND);
            log.info("Resending email {} with delay {} seconds, attempt #{}",
                    emailDto, delay, email.getSendingAttempts());
        }
    }
}
