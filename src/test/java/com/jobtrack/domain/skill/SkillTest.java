package com.jobtrack.domain.skill;

import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.technology.TechnologyId;
import com.jobtrack.domain.user.UserId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SkillTest {

    @Test
    void スキルを登録できる() {
        Skill skill = Skill.create(
                UserId.generate(),
                TechnologyId.generate(),
                ProficiencyLevel.ADVANCED,
                new BigDecimal("3.5")
        );

        assertThat(skill.id()).isNotNull();
        assertThat(skill.proficiencyLevel()).isEqualTo(ProficiencyLevel.ADVANCED);
        assertThat(skill.experienceYears()).isEqualByComparingTo("3.5");
    }

    @Test
    void 習熟度を更新できる() {
        Skill skill = Skill.create(
                UserId.generate(),
                TechnologyId.generate(),
                ProficiencyLevel.BEGINNER,
                BigDecimal.ZERO
        );

        skill.update(ProficiencyLevel.EXPERT, new BigDecimal("10.0"));

        assertThat(skill.proficiencyLevel()).isEqualTo(ProficiencyLevel.EXPERT);
        assertThat(skill.experienceYears()).isEqualByComparingTo("10.0");
    }

    @Test
    void 経験年数が負の場合は拒否する() {
        assertThatThrownBy(() -> Skill.create(
                UserId.generate(),
                TechnologyId.generate(),
                ProficiencyLevel.BEGINNER,
                new BigDecimal("-0.1")
        )).isInstanceOf(DomainException.class);
    }

    @Test
    void コードから習熟度を復元できる() {
        assertThat(ProficiencyLevel.fromCode(3)).isEqualTo(ProficiencyLevel.INTERMEDIATE);
    }
}
