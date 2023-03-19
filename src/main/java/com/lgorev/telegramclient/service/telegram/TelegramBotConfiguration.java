package com.lgorev.telegramclient.service.telegram;

import com.lgorev.telegramclient.service.telegram.client.TelegramBotClientImpl;
import com.lgorev.telegramclient.service.telegram.client.base.TelegramBotClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfiguration {

    @Bean(name = "wallet-bot")
    public TelegramBotClient walletTelegramBot(@Value("${telegram.bot.transactions.token}") String token) {
        return new TelegramBotClientImpl(token);
    }
}
