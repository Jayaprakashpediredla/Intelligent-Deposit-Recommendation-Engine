package com.nexuswealth.customer.service;

import com.nexuswealth.customer.dto.auth.LoginRequest;
import com.nexuswealth.customer.dto.auth.LoginResponse;
import com.nexuswealth.customer.entity.UserAccount;
import com.nexuswealth.customer.repository.UserAccountRepository;
import com.nexuswealth.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.nexuswealth.common.exception.UnauthorizedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository userAccountRepository;
    private final JwtService jwtService;

    private static final long TOKEN_EXPIRATION = 3600000L;

    public LoginResponse login(LoginRequest request) {

        log.info("Authentication attempt for username={}", request.username());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
        } catch (AuthenticationException ex) {
            log.warn("Authentication failed for username={}: {}", request.username(), ex.getMessage());
            throw new UnauthorizedException("Invalid username or password");
        }

        UserAccount userAccount = userAccountRepository.findByUsername(request.username()).orElseThrow(
                () -> new IllegalStateException("Authenticated user not found"));

        String token = jwtService.generateToken(userAccount.getUsername(), userAccount.getRole().name());

        log.info("Authentication successful for username={}", request.username());

        return new LoginResponse(token, "Bearer", TOKEN_EXPIRATION);
    }
}
