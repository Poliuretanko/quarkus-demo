package com.example.notificationservice.queue.consumer;

import com.example.notificationservice.config.ApplicationProperties;
import com.example.notificationservice.dto.EmailDto;
import com.example.notificationservice.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.SqsClient;

@SuppressWarnings("unused")
@Slf4j
public class EmailNotificationConsumer {
    @Inject
    SqsClient sqs;


    static ObjectReader QUARK_READER = new ObjectMapper().readerFor(EmailDto.class);

    @Inject
    EmailService emailService;

    @Inject
    ApplicationProperties applicationProperties;

    @Scheduled(every = "1s")
    public void pollQueue() {
        var messages = sqs.receiveMessage(consumer -> consumer
                .maxNumberOfMessages(10)
                .queueUrl(applicationProperties.getSqsEndpoint())
        ).messages();
        messages.forEach(message -> {
            var emailDto = toEmailDto(message.body());
            emailService.saveNotification(emailDto);
        });
    }

    private EmailDto toEmailDto(String message) {
        EmailDto emailDto = null;
        try {
            emailDto = QUARK_READER.readValue(message);
        } catch (Exception e) {
            log.error("Error decoding message", e);
            throw new RuntimeException(e);
        }
        return emailDto;
    }
}
