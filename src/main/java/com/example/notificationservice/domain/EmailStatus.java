package com.example.notificationservice.domain;

public enum EmailStatus {
    SENT,
    SENDING,
    WAITING_FOR_SEND,
    FAILED_TO_SEND,
    ATTEMPTS_ARE_OVER
}
