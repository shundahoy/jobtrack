package com.jobtrack.domain.technology;

import com.jobtrack.domain.shared.DomainException;

import java.util.Objects;

public class Technology {

    private final TechnologyId id;
    private String name;

    private Technology(TechnologyId id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = requireName(name);
    }

    public static Technology create(String name) {
        return new Technology(TechnologyId.generate(), name);
    }

    public static Technology reconstruct(TechnologyId id, String name) {
        return new Technology(id, name);
    }

    public void rename(String name) {
        this.name = requireName(name);
    }

    private static String requireName(String name) {
        Objects.requireNonNull(name, "テクノロジー名は必須です");
        if (name.isBlank()) {
            throw new DomainException("テクノロジー名は必須です");
        }
        return name;
    }

    public TechnologyId id() {
        return id;
    }

    public String name() {
        return name;
    }
}
