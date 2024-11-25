package com.WeCredit.controller;

import com.WeCredit.model.User;
import com.WeCredit.service.OtpService;
import com.WeCredit.util.JwtUtil;
import com.WeCredit.util.FingerprintUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final OtpService otpService;
    private final JwtUtil jwtUtil;
    private final FingerprintUtil fingerprintUtil;

    public AuthController(OtpService otpService, JwtUtil jwtUtil, FingerprintUtil fingerprintUtil) {
        this.otpService = otpService;
        this.jwtUtil = jwtUtil;
        this.fingerprintUtil = fingerprintUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String mobileNumber, @RequestHeader("User-Agent") String userAgent) {
        String fingerprint = fingerprintUtil.generateFingerprint(userAgent);

        if (otpService.userExists(mobileNumber)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        otpService.registerNewUser(mobileNumber, fingerprint);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String mobileNumber) {
        String otp = otpService.generateOtp();
        otpService.sendOtp(mobileNumber, otp);
        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String mobileNumber, @RequestParam String otpEntered) {
        String result = otpService.verifyOtp(mobileNumber, otpEntered);

        if (result.equals("Invalid OTP") || result.equals("OTP has expired")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.ok("Login successful. JWT Token: " + result);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestParam String mobileNumber) {
        return ResponseEntity.ok(otpService.resendOtp(mobileNumber));
    }
}
