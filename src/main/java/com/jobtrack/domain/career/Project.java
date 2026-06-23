package com.jobtrack.domain.career;

import com.jobtrack.domain.shared.DateRange;
import com.jobtrack.domain.shared.DomainException;
import com.jobtrack.domain.technology.TechnologyId;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Project {

    private final ProjectId id;
    private String projectName;
    private int displayOrder;
    private String role;
    private Integer teamSize;
    private DateRange period;
    private String description;
    private final Set<TechnologyId> technologyIds;

    private Project(
            ProjectId id,
            String projectName,
            int displayOrder,
            String role,
            Integer teamSize,
            DateRange period,
            String description,
            Set<TechnologyId> technologyIds
    ) {
        this.id = Objects.requireNonNull(id);
        this.projectName = requireProjectName(projectName);
        this.displayOrder = requireDisplayOrder(displayOrder);
        this.role = role;
        this.teamSize = teamSize;
        this.period = period;
        this.description = description;
        this.technologyIds = new HashSet<>(technologyIds);
    }

    public static Project create(
            String projectName,
            int displayOrder,
            String role,
            Integer teamSize,
            DateRange period,
            String description
    ) {
        return new Project(
                ProjectId.generate(),
                projectName,
                displayOrder,
                role,
                teamSize,
                period,
                description,
                Set.of()
        );
    }

    public static Project reconstruct(
            ProjectId id,
            String projectName,
            int displayOrder,
            String role,
            Integer teamSize,
            DateRange period,
            String description,
            Set<TechnologyId> technologyIds
    ) {
        return new Project(id, projectName, displayOrder, role, teamSize, period, description, technologyIds);
    }

    public void update(
            String projectName,
            int displayOrder,
            String role,
            Integer teamSize,
            DateRange period,
            String description
    ) {
        this.projectName = requireProjectName(projectName);
        this.displayOrder = requireDisplayOrder(displayOrder);
        this.role = role;
        if (teamSize != null && teamSize < 1) {
            throw new DomainException("チーム規模は1以上である必要があります");
        }
        this.teamSize = teamSize;
        this.period = period;
        this.description = description;
    }

    public void addTechnology(TechnologyId technologyId) {
        Objects.requireNonNull(technologyId);
        technologyIds.add(technologyId);
    }

    public void removeTechnology(TechnologyId technologyId) {
        technologyIds.remove(technologyId);
    }

    private static String requireProjectName(String projectName) {
        Objects.requireNonNull(projectName, "プロジェクト名は必須です");
        if (projectName.isBlank()) {
            throw new DomainException("プロジェクト名は必須です");
        }
        return projectName;
    }

    private static int requireDisplayOrder(int displayOrder) {
        if (displayOrder < 0) {
            throw new DomainException("表示順は0以上である必要があります");
        }
        return displayOrder;
    }

    public ProjectId id() {
        return id;
    }

    public String projectName() {
        return projectName;
    }

    public int displayOrder() {
        return displayOrder;
    }

    public String role() {
        return role;
    }

    public Integer teamSize() {
        return teamSize;
    }

    public DateRange period() {
        return period;
    }

    public String description() {
        return description;
    }

    public Set<TechnologyId> technologyIds() {
        return Collections.unmodifiableSet(technologyIds);
    }
}
