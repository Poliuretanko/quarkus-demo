package com.example.notificationservice.queue.producer;

import com.example.notificationservice.dto.EmailDto;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationProducer {

    private static final String QUEUE_NAME = "testBrevoQ";

    private final SqsTemplate sqsTemplate;

    public EmailNotificationProducer(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void send(EmailDto emailDto) {
        var message = MessageBuilder.withPayload(emailDto).build();
        sqsTemplate.send(QUEUE_NAME, message);
    }
}
