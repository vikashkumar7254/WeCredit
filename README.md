# WeCredit - Mobile Number/OTP Authentication System

## Overview
WeCredit is a mobile number-based OTP authentication system that enables users to register, log in, and verify their identity through OTPs (One-Time Passwords). The system supports various authentication scenarios such as account creation, login, OTP expiration, and device fingerprinting for secure login.

## Features
- **Create New Accounts**: Users can create new accounts using their mobile numbers and OTPs.
- **Login Using OTP**: Registered users can log in by verifying their OTP sent to their mobile number.
- **Resend OTP**: If the OTP expires, users can request a new OTP to be sent to their registered mobile number.
- **User Details**: After successful login, the user can retrieve their account details.
- **Fingerprinting for Multiple Devices**: Fingerprinting is implemented to track whether the user is logging in from a new or different device, enhancing security.

## Technology Stack
- **Backend**: Spring Boot
- **Authentication Service**: Twilio for OTP-based SMS
- **Database**: MySQL
- **API Documentation**: Swagger (optional)

## Setup Instructions

1. **Clone the Repository**
https://github.com/vikashkumar7254/WeCredit.git
