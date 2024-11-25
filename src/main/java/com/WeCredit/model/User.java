package com.WeCredit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    private String otp;
    private LocalDateTime otpExpiry;

    @ElementCollection
    private List<String> deviceFingerprints = new ArrayList<>();

    @ElementCollection
    private List<LocalDateTime> otpRequests = new ArrayList<>();

    private String jwtToken;

}
