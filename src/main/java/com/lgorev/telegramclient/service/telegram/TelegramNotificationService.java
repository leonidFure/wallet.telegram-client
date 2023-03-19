package com.lgorev.telegramclient.service.telegram;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.lgorev.telegramclient.domain.telegram.TelegramMessageRequest;
import com.lgorev.telegramclient.domain.telegram.TelegramNotificationType;
import com.lgorev.telegramclient.domain.telegram.TransactionNotificationResultDto;
import com.lgorev.telegramclient.service.telegram.notifications.TelegramNotificationManager;
import com.lgorev.telegramclient.utils.JsonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class TelegramNotificationService {
    private final TelegramNotificationManager manager;

    public Mono<BaseResultDto> sendTransactionNotification(TransactionNotificationResultDto dto) {
        return manager.send(new TelegramMessageRequest(
                JsonUtils.writeJsonNode(dto),
                TelegramNotificationType.TRANSACTIONS
        ));
    }
}
