package com.jobtrack.domain.education;

import com.jobtrack.domain.shared.DateRange;
import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.user.UserId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EducationTest {

    @Test
    void 学歴を登録できる() {
        Education education = Education.create(
                UserId.generate(),
                "サンプル大学",
                "工学部",
                DateRange.of(LocalDate.of(2012, 4, 1), LocalDate.of(2016, 3, 31))
        );

        assertThat(education.id()).isNotNull();
        assertThat(education.schoolName()).isEqualTo("サンプル大学");
        assertThat(education.period().end()).isEqualTo(LocalDate.of(2016, 3, 31));
    }

    @Test
    void 学校名が空の場合は拒否する() {
        assertThatThrownBy(() -> Education.create(
                UserId.generate(),
                "",
                "工学部",
                DateRange.of(LocalDate.of(2012, 4, 1), LocalDate.of(2016, 3, 31))
        )).isInstanceOf(DomainException.class);
    }

    @Test
    void 学歴を更新できる() {
        Education education = Education.create(
                UserId.generate(),
                "サンプル大学",
                "工学部",
                DateRange.of(LocalDate.of(2012, 4, 1), LocalDate.of(2016, 3, 31))
        );

        education.update(
                "サンプル大学大学院",
                "情報科学研究科",
                DateRange.of(LocalDate.of(2016, 4, 1), LocalDate.of(2018, 3, 31))
        );

        assertThat(education.schoolName()).isEqualTo("サンプル大学大学院");
        assertThat(education.faculty()).isEqualTo("情報科学研究科");
    }
}
