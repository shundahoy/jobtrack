package com.jobtrack.domain.skill;

import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.technology.TechnologyId;
import com.jobtrack.domain.user.UserId;

import java.math.BigDecimal;
import java.util.Objects;

public class Skill {

    private final SkillId id;
    private final UserId userId;
    private final TechnologyId technologyId;
    private ProficiencyLevel proficiencyLevel;
    private BigDecimal experienceYears;

    private Skill(
            SkillId id,
            UserId userId,
            TechnologyId technologyId,
            ProficiencyLevel proficiencyLevel,
            BigDecimal experienceYears
    ) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.technologyId = Objects.requireNonNull(technologyId);
        this.proficiencyLevel = Objects.requireNonNull(proficiencyLevel);
        this.experienceYears = requireExperienceYears(experienceYears);
    }

    public static Skill create(
            UserId userId,
            TechnologyId technologyId,
            ProficiencyLevel proficiencyLevel,
            BigDecimal experienceYears
    ) {
        return new Skill(SkillId.generate(), userId, technologyId, proficiencyLevel, experienceYears);
    }

    public static Skill reconstruct(
            SkillId id,
            UserId userId,
            TechnologyId technologyId,
            ProficiencyLevel proficiencyLevel,
            BigDecimal experienceYears
    ) {
        return new Skill(id, userId, technologyId, proficiencyLevel, experienceYears);
    }

    public void update(ProficiencyLevel proficiencyLevel, BigDecimal experienceYears) {
        this.proficiencyLevel = Objects.requireNonNull(proficiencyLevel);
        this.experienceYears = requireExperienceYears(experienceYears);
    }

    private static BigDecimal requireExperienceYears(BigDecimal experienceYears) {
        Objects.requireNonNull(experienceYears, "経験年数は必須です");
        if (experienceYears.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("経験年数は0以上である必要があります");
        }
        return experienceYears;
    }

    public SkillId id() {
        return id;
    }

    public UserId userId() {
        return userId;
    }

    public TechnologyId technologyId() {
        return technologyId;
    }

    public ProficiencyLevel proficiencyLevel() {
        return proficiencyLevel;
    }

    public BigDecimal experienceYears() {
        return experienceYears;
    }
}
