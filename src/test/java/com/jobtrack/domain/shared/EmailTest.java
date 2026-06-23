package com.jobtrack.domain.shared;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    void 有効なメールアドレスを受け付ける() {
        Email email = Email.of("user@example.com");
        assertThat(email.value()).isEqualTo("user@example.com");
    }

    @Test
    void 不正な形式は拒否する() {
        assertThatThrownBy(() -> Email.of("invalid"))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("形式");
    }
}
