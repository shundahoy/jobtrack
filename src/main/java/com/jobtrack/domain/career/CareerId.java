package com.jobtrack.domain.career;

import java.util.Objects;
import java.util.UUID;

public record CareerId(UUID value) {

    public CareerId {
        Objects.requireNonNull(value, "職歴IDは必須です");
    }

    public static CareerId generate() {
        return new CareerId(UUID.randomUUID());
    }

    public static CareerId of(UUID value) {
        return new CareerId(value);
    }
}
