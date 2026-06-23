package com.jobtrack.domain.technology;

import com.jobtrack.domain.shared.DomainException;

import java.util.Objects;
import java.util.UUID;

public record TechnologyId(UUID value) {

    public TechnologyId {
        Objects.requireNonNull(value, "テクノロジーIDは必須です");
    }

    public static TechnologyId generate() {
        return new TechnologyId(UUID.randomUUID());
    }

    public static TechnologyId of(UUID value) {
        return new TechnologyId(value);
    }
}
