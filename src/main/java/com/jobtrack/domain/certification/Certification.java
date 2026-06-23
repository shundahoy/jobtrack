package com.jobtrack.domain.certification;

import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.user.UserId;

import java.time.LocalDate;
import java.util.Objects;

public class Certification {

    private final CertificationId id;
    private final UserId userId;
    private String name;
    private LocalDate acquiredAt;

    private Certification(CertificationId id, UserId userId, String name, LocalDate acquiredAt) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.name = requireName(name);
        this.acquiredAt = Objects.requireNonNull(acquiredAt, "取得日は必須です");
    }

    public static Certification create(UserId userId, String name, LocalDate acquiredAt) {
        return new Certification(CertificationId.generate(), userId, name, acquiredAt);
    }

    public static Certification reconstruct(CertificationId id, UserId userId, String name, LocalDate acquiredAt) {
        return new Certification(id, userId, name, acquiredAt);
    }

    public void update(String name, LocalDate acquiredAt) {
        this.name = requireName(name);
        this.acquiredAt = Objects.requireNonNull(acquiredAt, "取得日は必須です");
    }

    private static String requireName(String name) {
        Objects.requireNonNull(name, "資格名は必須です");
        if (name.isBlank()) {
            throw new DomainException("資格名は必須です");
        }
        return name;
    }

    public CertificationId id() {
        return id;
    }

    public UserId userId() {
        return userId;
    }

    public String name() {
        return name;
    }

    public LocalDate acquiredAt() {
        return acquiredAt;
    }
}
