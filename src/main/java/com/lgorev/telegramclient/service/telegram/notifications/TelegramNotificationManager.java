package com.lgorev.telegramclient.service.telegram.notifications;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.lgorev.telegramclient.domain.telegram.TelegramMessageRequest;
import com.lgorev.telegramclient.domain.telegram.TelegramNotificationType;
import com.lgorev.telegramclient.service.telegram.notifications.strategies.base.NotificationStrategy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Менеджер отправки сообщений в телеграм
 */
@Service
public class TelegramNotificationManager {

    private final Map<TelegramNotificationType, NotificationStrategy> strategyMap;

    public TelegramNotificationManager(List<NotificationStrategy> strategies) {
        strategyMap = strategies.stream().collect(Collectors.toMap(
                NotificationStrategy::type, Function.identity()
        ));
    }

    /**
     * Отправка сообщения в телеграм
     *
     * @param request данные для отправки сообщения
     * @return Mono поток с результатом отправки сообщения в телеграм
     */
    public Mono<BaseResultDto> send(TelegramMessageRequest request) {
        var strategy = strategyMap.get(request.getType());
        if (strategy == null) {
            throw new IllegalArgumentException("notification with type " + request.getType() + " not supported");
        }
        return strategy.send(request);
    }

}
