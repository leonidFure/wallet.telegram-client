package com.lgorev.telegramclient.api;

import com.lgorev.telegramclient.domain.common.BaseResultDto;
import com.lgorev.telegramclient.domain.telegram.TransactionNotificationResultDto;
import com.lgorev.telegramclient.service.telegram.TelegramNotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/telegram")
@AllArgsConstructor
public class TelegramMessageController {

    private final TelegramNotificationService service;

    @PostMapping("transactions/send")
    public Mono<BaseResultDto> send(@RequestBody @Valid TransactionNotificationResultDto dto) {
        return service.sendTransactionNotification(dto);
    }
}
