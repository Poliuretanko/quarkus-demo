package com.example.notificationservice.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jetbrains.annotations.NotNull;

@Data
@ApplicationScoped
public class ApplicationProperties {

    @NotNull
    @ConfigProperty(name = "application.brevo-api.url")
    String brevoUrl;

    @NotNull
    @ConfigProperty(name = "application.brevo-api.api-key")
    String brevoApiKey;

    @NotNull
    @ConfigProperty(name = "application.brevo-api.default-template-id")
    Long brevoDefaultTemplateId;

    @NotNull
    @ConfigProperty(name = "spring.cloud.aws.sqs.endpoint")
    String sqsEndpoint;

    @NotNull
    @ConfigProperty(name = "quarkus.sqs.aws.region")
    String awsRegion;

    @NotNull
    @ConfigProperty(name = "quarkus.sqs.aws.credentials.type")
    String awsCredentialsType;

    @NotNull
    @ConfigProperty(name = "quarkus.sqs.aws.credentials.static-provider.access-key-id")
    String awsAccessKey;

    @NotNull
    @ConfigProperty(name = "quarkus.sqs.aws.credentials.static-provider.secret-access-key")
    String awsSecretKey;
}

