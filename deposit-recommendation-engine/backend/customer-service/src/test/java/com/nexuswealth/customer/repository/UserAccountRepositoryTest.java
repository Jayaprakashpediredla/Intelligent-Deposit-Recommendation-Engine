package com.nexuswealth.customer.repository;

import com.nexuswealth.common.enums.Role;
import com.nexuswealth.customer.entity.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository repository;

    @Test
    @DisplayName("Should save user account successfully")
    void shouldSaveUserAccount() {

        UserAccount userAccount =
                UserAccount.builder()
                        .username("test-admin")
                        .passwordHash("hashed-password")
                        .role(Role.ADMIN)
                        .enabled(true)
                        .build();

        UserAccount saved =
                repository.saveAndFlush(userAccount);

        assertThat(saved.getId())
                .isNotNull();

        assertThat(saved.getUsername())
                .isEqualTo("test-admin");

        assertThat(saved.getRole())
                .isEqualTo(Role.ADMIN);

        assertThat(saved.isEnabled())
                .isTrue();
    }

    @Test
    @DisplayName("Should find user by username")
    void shouldFindUserByUsername() {

        UserAccount userAccount =
                UserAccount.builder()
                        .username("find-admin")
                        .passwordHash("hashed-password")
                        .role(Role.ADMIN)
                        .enabled(true)
                        .build();

        repository.saveAndFlush(userAccount);

        var result =
                repository.findByUsername("find-admin");

        assertThat(result)
                .isPresent();

        assertThat(result.get().getUsername())
                .isEqualTo("find-admin");
    }

    @Test
    @DisplayName("Should check whether username exists")
    void shouldCheckUsernameExists() {

        UserAccount userAccount =
                UserAccount.builder()
                        .username("existing-user")
                        .passwordHash("hashed-password")
                        .role(Role.CUSTOMER)
                        .enabled(true)
                        .build();

        repository.saveAndFlush(userAccount);

        assertThat(
                repository.existsByUsername(
                        "existing-user"
                )
        ).isTrue();

        assertThat(
                repository.existsByUsername(
                        "missing-user"
                )
        ).isFalse();
    }
}