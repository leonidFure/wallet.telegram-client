package com.lgorev.telegramclient.service.telegram.client.base;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.pengrad.telegrambot.request.SendMessage;
import reactor.core.publisher.Mono;

public interface TelegramBotClient {
    /**
     * Синхронный метод отправки сообщений в телеграм
     *
     * @param message объект отправки сообщение с указанными chatId и текстом сообщения
     * @return результат отправки сообщения в тг
     */
    BaseResultDto sendSync(SendMessage message);

    /**
     * Асинхронный метод отправки сообщений в телеграм
     *
     * @param message объект отправки сообщение с указанными chatId и текстом сообщения
     * @return Mono с результатом отправки сообщения в тг
     */
    Mono<BaseResultDto> sendAsync(SendMessage message);
}
