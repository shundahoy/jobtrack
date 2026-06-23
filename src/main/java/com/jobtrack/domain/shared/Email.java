package com.jobtrack.domain.shared;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Email {
        Objects.requireNonNull(value, "メールアドレスは必須です");
        if (value.isBlank()) {
            throw new DomainException("メールアドレスは必須です");
        }
        if (value.length() > 255) {
            throw new DomainException("メールアドレスは255文字以内で入力してください");
        }
        if (!PATTERN.matcher(value).matches()) {
            throw new DomainException("メールアドレスの形式が正しくありません");
        }
    }

    public static Email of(String value) {
        return new Email(value);
    }
}
