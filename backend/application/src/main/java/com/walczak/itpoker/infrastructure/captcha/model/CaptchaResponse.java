package com.walczak.itpoker.infrastructure.captcha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public record CaptchaResponse(
        Boolean success,
        Date timestamp,
        String hostname,
        @JsonProperty("error-codes")
        List<String> errorCodes
) {
}
