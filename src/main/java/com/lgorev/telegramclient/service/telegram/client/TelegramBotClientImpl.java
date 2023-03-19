package com.lgorev.telegramclient.service.telegram.client;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.lgorev.telegramclient.service.telegram.client.base.TelegramBotClient;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Log4j2
public class TelegramBotClientImpl extends TelegramBot implements TelegramBotClient {
    public TelegramBotClientImpl(String botToken) {
        super(botToken);
    }

    @Override
    public BaseResultDto sendSync(SendMessage message) {
        var response = this.execute(message);
        if (response.isOk()) {
            return BaseResultDto.ok();
        } else {
            return BaseResultDto.fail("TELEGRAM_MESSAGE_SEND_ERROR", response.description());
        }
    }

    /**
     * Асинхронный метод отправки сообщений в телеграм
     *
     * @param message объект отправки сообщение с указанными chatId и текстом сообщения
     * @return Mono с результатом отправки сообщения в тг
     */
    @Override
    public Mono<BaseResultDto> sendAsync(SendMessage message) {
        // создаем sink, который позволяет управлять созданием Mono потока
        return Mono.create(sink -> this.execute(message, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                // если метод выполнился без ошибок - возвращаем результат отправки сообщения
                if (response.isOk()) {
                    sink.success(BaseResultDto.ok());
                    log.info("message successfully send");
                } else {
                    sink.success(BaseResultDto.fail("TELEGRAM_MESSAGE_SEND_ERROR", response.description()));
                    log.info("message failure send: {}", response.description());
                }
            }

            @Override
            public void onFailure(SendMessage request, IOException e) {
                // если метод вернул ошибку - так же возвращаем, что метод выполнился неуспешно
                sink.error(e);
                log.error(e);
            }
        }));
    }
}
