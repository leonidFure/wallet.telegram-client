package com.lgorev.telegramclient.service.telegram.client.base;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.pengrad.telegrambot.request.SendMessage;
import reactor.core.publisher.Mono;

public interface TelegramBotClient {
    BaseResultDto sendSync(SendMessage message);
    Mono<BaseResultDto> sendAsync(SendMessage message);
}
