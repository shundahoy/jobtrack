package com.jobtrack.domain.career;

import java.util.Objects;
import java.util.UUID;

public record ProjectId(UUID value) {

    public ProjectId {
        Objects.requireNonNull(value, "プロジェクトIDは必須です");
    }

    public static ProjectId generate() {
        return new ProjectId(UUID.randomUUID());
    }

    public static ProjectId of(UUID value) {
        return new ProjectId(value);
    }
}
