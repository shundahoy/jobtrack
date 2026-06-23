package com.jobtrack.domain.certification;

import java.util.Objects;
import java.util.UUID;

public record CertificationId(UUID value) {

    public CertificationId {
        Objects.requireNonNull(value, "資格IDは必須です");
    }

    public static CertificationId generate() {
        return new CertificationId(UUID.randomUUID());
    }

    public static CertificationId of(UUID value) {
        return new CertificationId(value);
    }
}
