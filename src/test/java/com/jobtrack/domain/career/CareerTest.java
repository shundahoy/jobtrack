package com.jobtrack.domain.career;

import com.jobtrack.domain.shared.DateRange;
import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.technology.TechnologyId;
import com.jobtrack.domain.user.UserId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CareerTest {

    @Test
    void 職歴を作成しプロジェクトを追加できる() {
        Career career = sampleCareer();
        Project project = Project.create(
                "ECサイト刷新",
                0,
                "バックエンドエンジニア",
                5,
                DateRange.of(LocalDate.of(2022, 4, 1), LocalDate.of(2023, 3, 31)),
                "API設計・実装"
        );

        career.addProject(project);

        assertThat(career.projects()).hasSize(1);
        assertThat(career.projects().getFirst().projectName()).isEqualTo("ECサイト刷新");
    }

    @Test
    void プロジェクトは表示順でソートされる() {
        Career career = sampleCareer();
        Project second = Project.create("B", 1, null, null, null, null);
        Project first = Project.create("A", 0, null, null, null, null);

        career.addProject(second);
        career.addProject(first);

        assertThat(career.projects().get(0).projectName()).isEqualTo("A");
        assertThat(career.projects().get(1).projectName()).isEqualTo("B");
    }

    @Test
    void 同じ表示順の別プロジェクトは拒否する() {
        Career career = sampleCareer();
        career.addProject(Project.create("A", 0, null, null, null, null));

        assertThatThrownBy(() -> career.addProject(Project.create("B", 0, null, null, null, null)))
                .isInstanceOf(DomainException.class);
    }

    @Test
    void プロジェクトにテクノロジーを紐づけられる() {
        Project project = Project.create("A", 0, null, null, null, null);
        TechnologyId techId = TechnologyId.generate();

        project.addTechnology(techId);

        assertThat(project.technologyIds()).containsExactly(techId);
    }

    private static Career sampleCareer() {
        return Career.create(
                UserId.generate(),
                "株式会社サンプル",
                "開発部",
                "エンジニア",
                EmploymentType.FULL_TIME,
                DateRange.openEnded(LocalDate.of(2020, 4, 1))
        );
    }
}
