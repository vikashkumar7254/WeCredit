package com.WeCredit.config;

import com.WeCredit.dto.SmsRequest;
import com.WeCredit.service.SmsSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Twilio SMS Sender implementation
 */
@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getMobileNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getMobileNumber());  // Recipient mobile number
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());  // Twilio sender number
            String otpMessage = "Your OTP is: " + smsRequest.getOtp();  // OTP message

            // Send the message
            Message.creator(to, from, otpMessage).create();
            LOGGER.info("Sent SMS: {} to {}", otpMessage, smsRequest.getMobileNumber());
        } else {
            LOGGER.error("Invalid phone number: {}", smsRequest.getMobileNumber());
            throw new IllegalArgumentException("Phone number [" + smsRequest.getMobileNumber() + "] is not a valid number");
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // Basic validation for a phone number (you can use a regex for better validation)
        return phoneNumber != null && phoneNumber.matches("^\\+?[0-9]{10,15}$");
    }
}
