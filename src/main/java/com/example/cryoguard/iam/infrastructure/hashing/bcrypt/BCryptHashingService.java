package com.example.cryoguard.iam.infrastructure.hashing.bcrypt;

import com.example.cryoguard.iam.application.internal.outboundservices.hashing.HashingService;

/**
 * This interface is a marker interface for the BCrypt hashing service.
 * It extends the {@link HashingService} and {@link PasswordEncoder} interfaces.
 */
public interface BCryptHashingService extends HashingService, org.springframework.security.crypto.password.PasswordEncoder {
}