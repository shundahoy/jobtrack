package com.jobtrack.domain.shared;

import java.util.Objects;

public record PersonName(
        String lastName,
        String firstName,
        String lastNameKana,
        String firstNameKana
) {

    public PersonName {
        lastName = requireNamePart(lastName, "姓");
        firstName = requireNamePart(firstName, "名");
        lastNameKana = requireNamePart(lastNameKana, "姓（カナ）");
        firstNameKana = requireNamePart(firstNameKana, "名（カナ）");
    }

    private static String requireNamePart(String value, String label) {
        Objects.requireNonNull(value, label + "は必須です");
        if (value.isBlank()) {
            throw new DomainException(label + "は必須です");
        }
        if (value.length() > 50) {
            throw new DomainException(label + "は50文字以内で入力してください");
        }
        return value;
    }

    public static PersonName of(String lastName, String firstName, String lastNameKana, String firstNameKana) {
        return new PersonName(lastName, firstName, lastNameKana, firstNameKana);
    }

    public String fullName() {
        return lastName + " " + firstName;
    }
}
