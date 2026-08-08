package com.nexuswealth.customer.repository;

import com.nexuswealth.customer.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository
        extends JpaRepository<UserAccount, UUID> {

    Optional<UserAccount> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<UserAccount> findByCustomerId(UUID customerId);
}