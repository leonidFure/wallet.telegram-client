package com.lgorev.telegramclient.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResultDto {
    private String errorCode;
    private String errorMessage;

    public static BaseResultDto ok() {
        return new BaseResultDto();
    }

    public static BaseResultDto fail(String errorCode, String errorMessage) {
        return new BaseResultDto(errorCode, errorMessage);
    }
}
