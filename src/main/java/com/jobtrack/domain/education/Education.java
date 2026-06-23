package com.jobtrack.domain.education;

import com.jobtrack.domain.shared.DateRange;
import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.user.UserId;

import java.util.Objects;

public class Education {

    private final EducationId id;
    private final UserId userId;
    private String schoolName;
    private String faculty;
    private DateRange period;

    private Education(EducationId id, UserId userId, String schoolName, String faculty, DateRange period) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.schoolName = requireSchoolName(schoolName);
        this.faculty = faculty;
        this.period = Objects.requireNonNull(period, "在学期間は必須です");
    }

    public static Education create(UserId userId, String schoolName, String faculty, DateRange period) {
        return new Education(EducationId.generate(), userId, schoolName, faculty, period);
    }

    public static Education reconstruct(
            EducationId id,
            UserId userId,
            String schoolName,
            String faculty,
            DateRange period
    ) {
        return new Education(id, userId, schoolName, faculty, period);
    }

    public void update(String schoolName, String faculty, DateRange period) {
        this.schoolName = requireSchoolName(schoolName);
        this.faculty = faculty;
        this.period = Objects.requireNonNull(period, "在学期間は必須です");
    }

    private static String requireSchoolName(String schoolName) {
        Objects.requireNonNull(schoolName, "学校名は必須です");
        if (schoolName.isBlank()) {
            throw new DomainException("学校名は必須です");
        }
        return schoolName;
    }

    public EducationId id() {
        return id;
    }

    public UserId userId() {
        return userId;
    }

    public String schoolName() {
        return schoolName;
    }

    public String faculty() {
        return faculty;
    }

    public DateRange period() {
        return period;
    }
}
