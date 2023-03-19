package com.lgorev.telegramclient.service.telegram.notifications.strategies;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.lgorev.telegramclient.domain.telegram.TelegramMessageRequest;
import com.lgorev.telegramclient.domain.telegram.TelegramNotificationType;
import com.lgorev.telegramclient.domain.telegram.TransactionNotificationResultDto;
import com.lgorev.telegramclient.service.telegram.client.WalletBotClient;
import com.lgorev.telegramclient.service.telegram.notifications.strategies.base.NotificationStrategy;
import com.lgorev.telegramclient.utils.JsonUtils;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;

@Service
public class TransactionNotificationStrategy implements NotificationStrategy {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private static final String MESSAGE_TEMPLATE = """
            Идентификатор транзакции: <b>%s</b>
                        
            Статус транзакции: <b>%s</b>
                        
            Дата время транзакции: <b>%s</b>
            """;

    private final WalletBotClient walletBotClient;

    @Value("${telegram.bot.transactions.chat_id}")
    private String chatId;

    public TransactionNotificationStrategy(WalletBotClient walletBotClient) {
        this.walletBotClient = walletBotClient;
    }

    @Override
    public Mono<BaseResultDto> send(TelegramMessageRequest request) {
        var context = JsonUtils.readSafe(request.getContext(), TransactionNotificationResultDto.class)
                .orElseThrow(() -> new IllegalStateException("reading context failed"));

        var messageText = MESSAGE_TEMPLATE.formatted(
                context.getId(),
                context.getStatus(),
                FORMATTER.format(context.getDatetime())
        );

        var message = new SendMessage(chatId, messageText)
                .parseMode(ParseMode.HTML);

        return walletBotClient.sendAsync(message, this.type());
    }

    @Override
    public TelegramNotificationType type() {
        return TelegramNotificationType.TRANSACTIONS;
    }
}
