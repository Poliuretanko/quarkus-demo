package com.example.notificationservice.client;

import com.example.notificationservice.dto.EmailDto;
import com.example.notificationservice.dto.RecipientDto;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
class SendEmailRequest {
    private Long templateId;
    private Set<RecipientDto> to;
    private Map<String, String> params;

    public static SendEmailRequest from(EmailDto emailDto) {
        var request = new SendEmailRequest();

        request.setTo(emailDto.getRecipients());
        request.setTemplateId(emailDto.getTemplateId());
        request.setParams(emailDto.getParameters());

        return request;
    }
}
