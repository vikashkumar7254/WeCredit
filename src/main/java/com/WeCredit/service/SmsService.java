package com.WeCredit.service;

import com.WeCredit.config.TwilioSmsSender;
import com.WeCredit.dto.SmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final TwilioSmsSender smsSender;

    @Autowired
    public SmsService(TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendOtpSms(String mobileNumber, String otp) {
        SmsRequest smsRequest = new SmsRequest(mobileNumber, otp);  // Create SMS request with OTP and mobile number
        smsSender.sendSms(smsRequest);  // Send SMS via TwilioSmsSender
    }
}
