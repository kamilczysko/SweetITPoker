package com.walczak.itpoker.infrastructure.captcha;

import com.walczak.itpoker.configuration.CaptchaSettings;
import com.walczak.itpoker.infrastructure.captcha.model.CaptchaResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.regex.Pattern;

@Service
public class CaptchaService {
        private final CaptchaSettings captchaSettings;
        private final RestOperations restTemplate;

    public CaptchaService(CaptchaSettings captchaSettings, RestOperations restTemplate) {
        this.captchaSettings = captchaSettings;
        this.restTemplate = restTemplate;
    }

    private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

        public void processResponse(String response, String ip) {
            if(!responseSanityCheck(response)) {
                throw new IllegalStateException("Response contains invalid characters");
            }

            URI verifyUri = URI.create(String.format(
                    "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                    captchaSettings.getSecret(), response, ip));

            CaptchaResponse googleResponse = restTemplate.getForObject(verifyUri, CaptchaResponse.class);

            if(!googleResponse.success()) {
                throw new IllegalStateException("reCaptcha was not successfully validated");
            }
        }

        private boolean responseSanityCheck(String response) {
            return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();

    }
}
