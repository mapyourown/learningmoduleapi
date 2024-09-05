package com.mapyourown.Learning.payload.request;

import jakarta.validation.constraints.NotBlank;

public class ModuleRequest {

    @NotBlank
    private String name;

    private Long courseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
