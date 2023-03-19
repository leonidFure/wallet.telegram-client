package com.lgorev.telegramclient.domain.telegram;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionNotificationResultDto {
    private UUID id;
    private TransactionStatus status;
    private LocalDateTime datetime;

    private enum TransactionStatus {
        SUCCESS, ERROR
    }
}
