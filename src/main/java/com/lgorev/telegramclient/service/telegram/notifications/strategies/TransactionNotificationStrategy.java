package com.lgorev.telegramclient.service.telegram.notifications.strategies;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.lgorev.telegramclient.domain.telegram.TelegramMessageRequest;
import com.lgorev.telegramclient.domain.telegram.TelegramNotificationType;
import com.lgorev.telegramclient.domain.telegram.TransactionNotificationResultDto;
import com.lgorev.telegramclient.service.telegram.client.base.TelegramBotClient;
import com.lgorev.telegramclient.service.telegram.notifications.strategies.base.NotificationStrategy;
import com.lgorev.telegramclient.utils.JsonUtils;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;

@Service
public class TransactionNotificationStrategy implements NotificationStrategy {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private static final String MESSAGE_TEMPLATE = """
            Идентификатор транзакции: <b>%s</b>
                        
            Сумма транзакции: <b>%s</b>
                        
            Статус транзакции: <b>%s</b>
                        
            Дата время транзакции: <b>%s</b>
            """;

    private final TelegramBotClient walletBotClient;

    @Value("${telegram.bot.transactions.chat_id}")
    private String chatId;

    public TransactionNotificationStrategy(@Qualifier("wallet-bot") TelegramBotClient walletBotClient) {
        this.walletBotClient = walletBotClient;
    }

    @Override
    public Mono<BaseResultDto> send(TelegramMessageRequest request) {
        return JsonUtils
                .readSafe(request.getContext(), TransactionNotificationResultDto.class)
                .map(this::createMessage)
                .map(walletBotClient::sendAsync)
                .orElse(Mono.create(sink -> sink.error(new IllegalStateException("reading context failed"))));
    }

    private SendMessage createMessage(TransactionNotificationResultDto context) {
        var message = MESSAGE_TEMPLATE.formatted(
                context.getId(),
                context.getAmount().toPlainString(),
                context.getStatus(),
                FORMATTER.format(context.getDatetime())
        );
        return new SendMessage(chatId, message)
                .parseMode(ParseMode.HTML)
                .disableNotification(true);
    }

    @Override
    public TelegramNotificationType type() {
        return TelegramNotificationType.TRANSACTIONS;
    }
}
