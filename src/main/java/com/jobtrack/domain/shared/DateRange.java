package com.jobtrack.domain.shared;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public record DateRange(LocalDate start, LocalDate end) {

    public DateRange {
        Objects.requireNonNull(start, "開始日は必須です");
        if (end != null && end.isBefore(start)) {
            throw new DomainException("終了日は開始日以降である必要があります");
        }
    }

    public static DateRange of(LocalDate start, LocalDate end) {
        return new DateRange(start, end);
    }

    public static DateRange openEnded(LocalDate start) {
        return new DateRange(start, null);
    }

    public Optional<LocalDate> endOptional() {
        return Optional.ofNullable(end);
    }

    public boolean isOngoing() {
        return end == null;
    }
}
