package com.jobtrack.domain.technology;

import com.jobtrack.domain.shared.DomainException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TechnologyTest {

    @Test
    void テクノロジーを登録できる() {
        Technology technology = Technology.create("Java");

        assertThat(technology.id()).isNotNull();
        assertThat(technology.name()).isEqualTo("Java");
    }

    @Test
    void 名前を変更できる() {
        Technology technology = Technology.create("Java");

        technology.rename("Kotlin");

        assertThat(technology.name()).isEqualTo("Kotlin");
    }

    @Test
    void 名前が空の場合は拒否する() {
        assertThatThrownBy(() -> Technology.create(" "))
                .isInstanceOf(DomainException.class);
    }
}
