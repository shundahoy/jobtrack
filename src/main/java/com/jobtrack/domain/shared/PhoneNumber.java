package com.jobtrack.domain.shared;

import java.util.Objects;
import java.util.regex.Pattern;

public record PhoneNumber(String value) {

    private static final Pattern PATTERN = Pattern.compile("^[0-9\\-+() ]{1,20}$");

    public PhoneNumber {
        Objects.requireNonNull(value, "電話番号は必須です");
        if (value.isBlank()) {
            throw new DomainException("電話番号は必須です");
        }
        if (!PATTERN.matcher(value).matches()) {
            throw new DomainException("電話番号の形式が正しくありません");
        }
    }

    public static PhoneNumber of(String value) {
        return new PhoneNumber(value);
    }
}
