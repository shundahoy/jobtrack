package com.jobtrack.domain.certification;

import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.user.UserId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CertificationTest {

    @Test
    void 資格を登録できる() {
        Certification certification = Certification.create(
                UserId.generate(),
                "基本情報技術者",
                LocalDate.of(2020, 4, 1)
        );

        assertThat(certification.id()).isNotNull();
        assertThat(certification.name()).isEqualTo("基本情報技術者");
    }

    @Test
    void 資格名が空の場合は拒否する() {
        assertThatThrownBy(() -> Certification.create(
                UserId.generate(),
                "  ",
                LocalDate.of(2020, 4, 1)
        )).isInstanceOf(DomainException.class);
    }

    @Test
    void 資格情報を更新できる() {
        Certification certification = Certification.create(
                UserId.generate(),
                "基本情報技術者",
                LocalDate.of(2020, 4, 1)
        );

        certification.update("応用情報技術者", LocalDate.of(2022, 10, 1));

        assertThat(certification.name()).isEqualTo("応用情報技術者");
        assertThat(certification.acquiredAt()).isEqualTo(LocalDate.of(2022, 10, 1));
    }
}
