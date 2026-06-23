package com.jobtrack.domain.user;

import com.jobtrack.domain.career.CareerId;
import com.jobtrack.domain.certification.CertificationId;
import com.jobtrack.domain.education.EducationId;
import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.shared.Email;
import com.jobtrack.domain.shared.PersonName;
import com.jobtrack.domain.shared.PhoneNumber;
import com.jobtrack.domain.skill.SkillId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @Test
    void ユーザーを登録できる() {
        User user = User.register(
                PersonName.of("山田", "太郎", "ヤマダ", "タロウ"),
                Email.of("yamada@example.com"),
                PhoneNumber.of("090-1234-5678"),
                LocalDate.of(1990, 1, 1)
        );

        assertThat(user.id()).isNotNull();
        assertThat(user.name().fullName()).isEqualTo("山田 太郎");
        assertThat(user.skillIds()).isEmpty();
    }

    @Test
    void プロフィールを更新できる() {
        User user = sampleUser();

        user.updateProfile(
                PersonName.of("山田", "花子", "ヤマダ", "ハナコ"),
                Email.of("hanako@example.com"),
                PhoneNumber.of("080-0000-0000"),
                LocalDate.of(1991, 2, 2),
                "東京都",
                "東京",
                8_000_000
        );

        assertThat(user.name().firstName()).isEqualTo("花子");
        assertThat(user.desiredSalary()).isEqualTo(8_000_000);
    }

    @Test
    void 希望年収が負の場合は拒否する() {
        User user = sampleUser();

        assertThatThrownBy(() -> user.updateProfile(
                user.name(),
                user.email(),
                user.phone(),
                user.birthDate(),
                null,
                null,
                -1
        )).isInstanceOf(DomainException.class);
    }

    @Test
    void 関連集約のIDをリンクできる() {
        User user = sampleUser();
        SkillId skillId = SkillId.generate();
        CertificationId certId = CertificationId.generate();
        EducationId eduId = EducationId.generate();
        CareerId careerId = CareerId.generate();

        user.linkSkill(skillId);
        user.linkCertification(certId);
        user.linkEducation(eduId);
        user.linkCareer(careerId);

        assertThat(user.skillIds()).containsExactly(skillId);
        assertThat(user.certificationIds()).containsExactly(certId);
        assertThat(user.educationIds()).containsExactly(eduId);
        assertThat(user.careerIds()).containsExactly(careerId);
    }

    @Test
    void 同じスキルIDの重複リンクは防ぐ() {
        User user = sampleUser();
        SkillId skillId = SkillId.generate();

        user.linkSkill(skillId);
        user.linkSkill(skillId);

        assertThat(user.skillIds()).hasSize(1);
    }

    private static User sampleUser() {
        return User.register(
                PersonName.of("山田", "太郎", "ヤマダ", "タロウ"),
                Email.of("yamada@example.com"),
                PhoneNumber.of("090-1234-5678"),
                LocalDate.of(1990, 1, 1)
        );
    }
}
