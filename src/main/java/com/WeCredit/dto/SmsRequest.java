package com.WeCredit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-twilio-sms-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 23/10/21
 * Time: 12.57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest {

    @JsonProperty("phoneNumber")
    @NotBlank
    private String mobileNumber; // destination

    @JsonProperty("otp")
    @NotBlank
    private String otp;

}
