package com.nexuswealth.customer.security;

import com.nexuswealth.customer.entity.UserAccount;
import com.nexuswealth.customer.repository.UserAccountRepository;
import com.nexuswealth.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService
        implements UserDetailsService,
        SecurityUserService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        UserAccount userAccount =
                userAccountRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        "User not found: " + username
                                )
                        );

        return User.builder()
                .username(userAccount.getUsername())
                .password(userAccount.getPasswordHash())
                .authorities(
                        new SimpleGrantedAuthority(
                                "ROLE_" +
                                        userAccount.getRole().name()
                        )
                )
                .disabled(!userAccount.isEnabled())
                .build();
    }
}