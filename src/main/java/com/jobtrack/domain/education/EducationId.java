package com.jobtrack.domain.education;

import java.util.Objects;
import java.util.UUID;

public record EducationId(UUID value) {

    public EducationId {
        Objects.requireNonNull(value, "学歴IDは必須です");
    }

    public static EducationId generate() {
        return new EducationId(UUID.randomUUID());
    }

    public static EducationId of(UUID value) {
        return new EducationId(value);
    }
}
