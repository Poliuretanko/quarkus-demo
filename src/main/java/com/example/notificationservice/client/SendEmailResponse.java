package com.example.notificationservice.client;

import lombok.Data;

import java.util.List;

@Data
public class SendEmailResponse {
    private String messageId;
    private List<String> messageIds;
    private String batchId;
    private String code;
    private String message;
}
