package com.simon.backend_portfolio.dto;


import java.util.Set;

public class ProjectSkillRequest {

    private Long projectId;
    private Set<Long> skillIds;

    public ProjectSkillRequest() {
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Set<Long> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(Set<Long> skillIds) {
        this.skillIds = skillIds;
    }
}