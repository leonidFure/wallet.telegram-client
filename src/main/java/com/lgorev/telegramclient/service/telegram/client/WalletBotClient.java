package com.lgorev.telegramclient.service.telegram.client;

import com.lgorev.telegramclient.service.telegram.client.base.BaseTelegramBotClient;
import com.lgorev.telegramclient.service.telegram.executor.TelegramBotExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WalletBotClient extends BaseTelegramBotClient {

    @Value("${telegram.bot.transactions.token}")
    private String token;

    public WalletBotClient(TelegramBotExecutor executor) {
        super(executor);
    }

    @Override
    protected String token() {
        return token;
    }
}
