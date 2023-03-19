package com.lgorev.telegramclient.service.telegram.executor;

import com.lgorev.telegramclient.domain.telegram.TelegramNotificationType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Log4j2
@Service
public class TelegramBotExecutorImpl implements TelegramBotExecutor {

    @Override
    public <T> T execute(Supplier<T> supplier, TelegramNotificationType type) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("Send telegram notifications with type {} was failed.", type);
            throw new RuntimeException("notification send failed");
        }
    }
}
