package com.jobtrack.domain.user;

import com.jobtrack.domain.career.CareerId;
import com.jobtrack.domain.certification.CertificationId;
import com.jobtrack.domain.education.EducationId;
import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.shared.Email;
import com.jobtrack.domain.shared.PersonName;
import com.jobtrack.domain.shared.PhoneNumber;
import com.jobtrack.domain.skill.SkillId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User {

    private final UserId id;
    private PersonName name;
    private Email email;
    private PhoneNumber phone;
    private LocalDate birthDate;
    private String address;
    private String desiredWorkLocation;
    private Integer desiredSalary;
    private String selfPr;
    private final List<SkillId> skillIds;
    private final List<CertificationId> certificationIds;
    private final List<EducationId> educationIds;
    private final List<CareerId> careerIds;

    private User(
            UserId id,
            PersonName name,
            Email email,
            PhoneNumber phone,
            LocalDate birthDate,
            String address,
            String desiredWorkLocation,
            Integer desiredSalary,
            String selfPr,
            List<SkillId> skillIds,
            List<CertificationId> certificationIds,
            List<EducationId> educationIds,
            List<CareerId> careerIds
    ) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.phone = Objects.requireNonNull(phone);
        this.birthDate = Objects.requireNonNull(birthDate, "生年月日は必須です");
        this.address = address;
        this.desiredWorkLocation = desiredWorkLocation;
        this.desiredSalary = desiredSalary;
        this.selfPr = selfPr;
        this.skillIds = new ArrayList<>(skillIds);
        this.certificationIds = new ArrayList<>(certificationIds);
        this.educationIds = new ArrayList<>(educationIds);
        this.careerIds = new ArrayList<>(careerIds);
    }

    public static User register(
            PersonName name,
            Email email,
            PhoneNumber phone,
            LocalDate birthDate
    ) {
        return new User(
                UserId.generate(),
                name,
                email,
                phone,
                birthDate,
                null,
                null,
                null,
                null,
                List.of(),
                List.of(),
                List.of(),
                List.of()
        );
    }

    public static User reconstruct(
            UserId id,
            PersonName name,
            Email email,
            PhoneNumber phone,
            LocalDate birthDate,
            String address,
            String desiredWorkLocation,
            Integer desiredSalary,
            String selfPr,
            List<SkillId> skillIds,
            List<CertificationId> certificationIds,
            List<EducationId> educationIds,
            List<CareerId> careerIds
    ) {
        return new User(
                id,
                name,
                email,
                phone,
                birthDate,
                address,
                desiredWorkLocation,
                desiredSalary,
                selfPr,
                skillIds,
                certificationIds,
                educationIds,
                careerIds
        );
    }

    public void updateProfile(
            PersonName name,
            Email email,
            PhoneNumber phone,
            LocalDate birthDate,
            String address,
            String desiredWorkLocation,
            Integer desiredSalary
    ) {
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email);
        this.phone = Objects.requireNonNull(phone);
        this.birthDate = Objects.requireNonNull(birthDate, "生年月日は必須です");
        if (desiredSalary != null && desiredSalary < 0) {
            throw new DomainException("希望年収は0以上である必要があります");
        }
        this.address = address;
        this.desiredWorkLocation = desiredWorkLocation;
        this.desiredSalary = desiredSalary;
    }

    public void updateSelfPr(String selfPr) {
        this.selfPr = selfPr;
    }

    public void linkSkill(SkillId skillId) {
        Objects.requireNonNull(skillId);
        if (!skillIds.contains(skillId)) {
            skillIds.add(skillId);
        }
    }

    public void unlinkSkill(SkillId skillId) {
        skillIds.remove(skillId);
    }

    public void linkCertification(CertificationId certificationId) {
        Objects.requireNonNull(certificationId);
        if (!certificationIds.contains(certificationId)) {
            certificationIds.add(certificationId);
        }
    }

    public void unlinkCertification(CertificationId certificationId) {
        certificationIds.remove(certificationId);
    }

    public void linkEducation(EducationId educationId) {
        Objects.requireNonNull(educationId);
        if (!educationIds.contains(educationId)) {
            educationIds.add(educationId);
        }
    }

    public void unlinkEducation(EducationId educationId) {
        educationIds.remove(educationId);
    }

    public void linkCareer(CareerId careerId) {
        Objects.requireNonNull(careerId);
        if (!careerIds.contains(careerId)) {
            careerIds.add(careerId);
        }
    }

    public void unlinkCareer(CareerId careerId) {
        careerIds.remove(careerId);
    }

    public UserId id() {
        return id;
    }

    public PersonName name() {
        return name;
    }

    public Email email() {
        return email;
    }

    public PhoneNumber phone() {
        return phone;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public String address() {
        return address;
    }

    public String desiredWorkLocation() {
        return desiredWorkLocation;
    }

    public Integer desiredSalary() {
        return desiredSalary;
    }

    public String selfPr() {
        return selfPr;
    }

    public List<SkillId> skillIds() {
        return Collections.unmodifiableList(skillIds);
    }

    public List<CertificationId> certificationIds() {
        return Collections.unmodifiableList(certificationIds);
    }

    public List<EducationId> educationIds() {
        return Collections.unmodifiableList(educationIds);
    }

    public List<CareerId> careerIds() {
        return Collections.unmodifiableList(careerIds);
    }
}
