package com.WeCredit.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("twilio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwilioConfiguration {

    private String accountSid="AC8f24839c09a2d84b1e416a0f09cba7bd";
    private String authToken="7c7c10e976d343fe90cf811596aba12e";
    private String trialNumber="+18127210290";
}