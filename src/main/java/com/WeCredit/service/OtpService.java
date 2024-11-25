package com.WeCredit.service;

import com.WeCredit.model.User;
import com.WeCredit.repository.UserRepository;
import com.WeCredit.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmsService smsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${otp.expiry.minutes}")
    private long otpExpiryMinutes;

    public String generateOtp() {
        return String.format("%06d", (int) (Math.random() * 1000000));  // Generate 6-digit OTP
    }

    public void sendOtp(String mobileNumber, String otp) {
        try {
            smsService.sendOtpSms(mobileNumber, "Your OTP is: " + otp);  // Send OTP via SMS
        } catch (Exception e) {
            logger.error("Failed to send OTP to {}: {}", mobileNumber, e.getMessage());
            throw new RuntimeException("Failed to send OTP");
        }
    }

    public boolean isOtpExpired(LocalDateTime otpExpiry) {
        return otpExpiry.isBefore(LocalDateTime.now());
    }

    public String verifyOtp(String mobileNumber, String otpEntered) {
        Optional<User> userOpt = userRepository.findByMobileNumber(mobileNumber);
        if (userOpt.isEmpty()) {
            return "User not found";
        }

        User existingUser = userOpt.get();

        // Check if OTP is expired
        if (existingUser.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return "OTP has expired";
        }

        // Check OTP match
        if (!existingUser.getOtp().equals(otpEntered)) {
            return "Invalid OTP";
        }

        // Generate JWT token after successful OTP verification
        String jwtToken = jwtUtil.generateToken(existingUser.getMobileNumber());
        existingUser.setJwtToken(jwtToken);  // Save JWT token
        userRepository.save(existingUser);

        return jwtToken;  // Return JWT token
    }

    public String resendOtp(String mobileNumber) {
        Optional<User> userOpt = userRepository.findByMobileNumber(mobileNumber);
        if (userOpt.isEmpty()) {
            return "User not found";
        }

        User existingUser = userOpt.get();

        if (!isOtpExpired(existingUser.getOtpExpiry())) {
            return "OTP has not expired yet";
        }

        String newOtp = generateOtp();
        existingUser.setOtp(newOtp);
        existingUser.setOtpExpiry(LocalDateTime.now().plusMinutes(otpExpiryMinutes));
        userRepository.save(existingUser);  // Save the new OTP and expiry time

        sendOtp(mobileNumber, newOtp);
        return "OTP resent successfully";
    }

    public boolean userExists(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber).isPresent();
    }

    public void registerNewUser(String mobileNumber, String fingerprint) {
        User newUser = new User();
        newUser.setMobileNumber(mobileNumber);
        newUser.getDeviceFingerprints().add(fingerprint);  // Save fingerprint
        userRepository.save(newUser);
    }
}
