package com.jobtrack.domain.skill;

import com.jobtrack.domain.shared.DomainException;

public enum ProficiencyLevel {
    BEGINNER(1, "初級"),
    ELEMENTARY(2, "初中級"),
    INTERMEDIATE(3, "中級"),
    ADVANCED(4, "上級"),
    EXPERT(5, "エキスパート");

    private final int code;
    private final String label;

    ProficiencyLevel(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static ProficiencyLevel fromCode(int code) {
        for (ProficiencyLevel level : values()) {
            if (level.code == code) {
                return level;
            }
        }
        throw new DomainException("不明な習熟度コード: " + code);
    }
}
