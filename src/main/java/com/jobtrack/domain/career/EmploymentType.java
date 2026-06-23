package com.jobtrack.domain.career;

public enum EmploymentType {
    FULL_TIME(1, "正社員"),
    CONTRACT(2, "契約社員"),
    PART_TIME(3, "パート・アルバイト"),
    DISPATCH(4, "派遣"),
    FREELANCE(5, "業務委託");

    private final int code;
    private final String label;

    EmploymentType(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static EmploymentType fromCode(int code) {
        for (EmploymentType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("不明な雇用形態コード: " + code);
    }
}
