package com.jobtrack.domain.skill;

import java.util.Objects;
import java.util.UUID;

public record SkillId(UUID value) {

    public SkillId {
        Objects.requireNonNull(value, "スキルIDは必須です");
    }

    public static SkillId generate() {
        return new SkillId(UUID.randomUUID());
    }

    public static SkillId of(UUID value) {
        return new SkillId(value);
    }
}
