package com.example.notificationservice.client;


import com.example.notificationservice.config.ApplicationProperties;
import com.example.notificationservice.dto.EmailDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class BrevoClient {
    private final Client client;
    private final String baseUrl ;

    @Inject
    public BrevoClient(ApplicationProperties applicationProperties) {
        baseUrl = applicationProperties.getBrevoUrl();
        ExecutorService executorService = Executors.newCachedThreadPool();
        client = ClientBuilder.newBuilder()
                .executorService(executorService)
                .build();
    }

    public SendEmailResponse sendEmail(EmailDto emailDto) {
        var request = SendEmailRequest.from(emailDto);

        var requestInvocation = client.target(baseUrl + "v3/smtp/email")
                .request()
                .buildPost(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
        return requestInvocation.invoke(SendEmailResponse.class);
    }
}
