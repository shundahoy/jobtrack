package com.jobtrack.domain.career;

import com.jobtrack.domain.shared.DateRange;
import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.user.UserId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Career {

    private final CareerId id;
    private final UserId userId;
    private String companyName;
    private String departmentName;
    private String position;
    private EmploymentType employmentType;
    private DateRange period;
    private final List<Project> projects;

    private Career(
            CareerId id,
            UserId userId,
            String companyName,
            String departmentName,
            String position,
            EmploymentType employmentType,
            DateRange period,
            List<Project> projects
    ) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.companyName = requireCompanyName(companyName);
        this.departmentName = departmentName;
        this.position = position;
        this.employmentType = employmentType;
        this.period = period;
        this.projects = new ArrayList<>(projects);
    }

    public static Career create(
            UserId userId,
            String companyName,
            String departmentName,
            String position,
            EmploymentType employmentType,
            DateRange period
    ) {
        return new Career(
                CareerId.generate(),
                userId,
                companyName,
                departmentName,
                position,
                employmentType,
                period,
                List.of()
        );
    }

    public static Career reconstruct(
            CareerId id,
            UserId userId,
            String companyName,
            String departmentName,
            String position,
            EmploymentType employmentType,
            DateRange period,
            List<Project> projects
    ) {
        return new Career(id, userId, companyName, departmentName, position, employmentType, period, projects);
    }

    public void update(
            String companyName,
            String departmentName,
            String position,
            EmploymentType employmentType,
            DateRange period
    ) {
        this.companyName = requireCompanyName(companyName);
        this.departmentName = departmentName;
        this.position = position;
        this.employmentType = employmentType;
        this.period = period;
    }

    public void addProject(Project project) {
        Objects.requireNonNull(project);
        boolean duplicateOrder = projects.stream()
                .anyMatch(p -> p.id().equals(project.id()) || p.displayOrder() == project.displayOrder());
        if (duplicateOrder && projects.stream().noneMatch(p -> p.id().equals(project.id()))) {
            throw new DomainException("同じ表示順のプロジェクトが既に存在します");
        }
        projects.removeIf(p -> p.id().equals(project.id()));
        projects.add(project);
        projects.sort((a, b) -> Integer.compare(a.displayOrder(), b.displayOrder()));
    }

    public void removeProject(ProjectId projectId) {
        projects.removeIf(p -> p.id().equals(projectId));
    }

    public Project findProject(ProjectId projectId) {
        return projects.stream()
                .filter(p -> p.id().equals(projectId))
                .findFirst()
                .orElseThrow(() -> new DomainException("プロジェクトが見つかりません: " + projectId.value()));
    }

    private static String requireCompanyName(String companyName) {
        Objects.requireNonNull(companyName, "会社名は必須です");
        if (companyName.isBlank()) {
            throw new DomainException("会社名は必須です");
        }
        return companyName;
    }

    public CareerId id() {
        return id;
    }

    public UserId userId() {
        return userId;
    }

    public String companyName() {
        return companyName;
    }

    public String departmentName() {
        return departmentName;
    }

    public String position() {
        return position;
    }

    public EmploymentType employmentType() {
        return employmentType;
    }

    public DateRange period() {
        return period;
    }

    public List<Project> projects() {
        return Collections.unmodifiableList(projects);
    }
}
