package com.example.notificationservice.dto;

import com.example.notificationservice.domain.Email;
import com.example.notificationservice.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private Long templateId;
    private Set<RecipientDto> recipients;
    private Map<String, String> parameters;

    public static EmailDto from(Email email) {
        var recipientDtos = email.getRecipients().stream()
                .map(RecipientDto::from)
                .collect(Collectors.toSet());
        return new EmailDto(email.getTemplateId(), recipientDtos, email.getParameters());
    }

    public Email convertToEmail(ZonedDateTime nextAttemptTime) {
        var recipients = this.recipients.stream()
                .map(recipient -> new Recipient(recipient.getEmail(), recipient.getName()))
                .collect(Collectors.toSet());
        return new Email(getTemplateId(), recipients, parameters, nextAttemptTime);
    }

    @Override
    public String toString() {
        return "EmailDto{" +
                "templateId=" + templateId +
                ", parameters=" + parameters +
                '}';
    }
}
