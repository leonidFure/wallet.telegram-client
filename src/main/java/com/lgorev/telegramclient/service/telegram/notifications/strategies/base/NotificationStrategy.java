package com.lgorev.telegramclient.service.telegram.notifications.strategies.base;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.lgorev.telegramclient.domain.telegram.TelegramMessageRequest;
import com.lgorev.telegramclient.domain.telegram.TelegramNotificationType;
import reactor.core.publisher.Mono;

/**
 * Базовый интерфейс стратегии для отправки сообщений
 */
public interface NotificationStrategy {

    /**
     * Отправка сообщения в телеграм
     *
     * @param request данные для отправки сообщения
     * @return Mono поток с результатом отправки сообщения в телеграм
     */
    Mono<BaseResultDto> send(TelegramMessageRequest request);

    /**
     * @return Тип уведомления в телеграм
     */
    TelegramNotificationType type();
}
