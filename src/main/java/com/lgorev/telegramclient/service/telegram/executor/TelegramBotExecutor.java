package com.lgorev.telegramclient.service.telegram.executor;

import com.lgorev.telegramclient.domain.telegram.TelegramNotificationType;

import java.util.function.Supplier;

public interface TelegramBotExecutor {

     <T> T execute(Supplier<T> supplier, TelegramNotificationType type);

}
