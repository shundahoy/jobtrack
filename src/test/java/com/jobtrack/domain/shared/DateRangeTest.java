package com.jobtrack.domain.shared;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateRangeTest {

    @Test
    void 終了日が開始日以降なら作成できる() {
        DateRange range = DateRange.of(LocalDate.of(2020, 4, 1), LocalDate.of(2024, 3, 31));
        assertThat(range.start()).isEqualTo(LocalDate.of(2020, 4, 1));
        assertThat(range.end()).isEqualTo(LocalDate.of(2024, 3, 31));
    }

    @Test
    void 終了日なしの在籍中期間を表現できる() {
        DateRange range = DateRange.openEnded(LocalDate.of(2024, 4, 1));
        assertThat(range.isOngoing()).isTrue();
        assertThat(range.endOptional()).isEmpty();
    }

    @Test
    void 終了日が開始日より前なら拒否する() {
        assertThatThrownBy(() -> DateRange.of(LocalDate.of(2024, 1, 1), LocalDate.of(2023, 12, 31)))
                .isInstanceOf(DomainException.class);
    }
}
