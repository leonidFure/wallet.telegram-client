package com.lgorev.telegramclient.service.telegram.client.base;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.lgorev.telegramclient.domain.telegram.TelegramNotificationType;
import com.lgorev.telegramclient.service.telegram.executor.TelegramBotExecutor;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Log4j2
public abstract class BaseTelegramBotClient {
    protected final TelegramBotExecutor executor;

    public BaseTelegramBotClient(TelegramBotExecutor executor) {
        this.executor = executor;
    }

    protected abstract String token();

    /**
     * Асинхронный метод отправки сообщений в телеграм
     * @param message объект отправки сообщение с указанными chatId и текстом сообщения
     * @param notificationType тип сообщения в телеграм
     * @return Mono с результатом отправки сообщения в тг
     */
    public Mono<BaseResultDto> sendAsync(SendMessage message, TelegramNotificationType notificationType) {
        // создаем sink, который позволяет управлять созданием Mono потока
        return executor.execute(() -> Mono.create(monoSink -> new TelegramBot(token())
                .execute(message, new Callback<SendMessage, SendResponse>() {
                    @Override
                    public void onResponse(SendMessage request, SendResponse response) {
                        // если метод выполнился без ошибок - возвращаем результат отправки сообщения
                        if (response.isOk()) {
                            monoSink.success(BaseResultDto.ok());
                            log.info("message successfully send");
                        } else {
                            monoSink.success(BaseResultDto.fail("TELEGRAM_MESSAGE_SEND_ERROR", response.description()));
                            log.info("message failure send: {}", response.description());
                        }
                    }

                    @Override
                    public void onFailure(SendMessage request, IOException e) {
                        // если метод вернул ошибку - так же возвращаем, что метод выполнился неуспешно
                        monoSink.error(e);
                        log.error(e);
                    }
                })), notificationType);
    }
}
