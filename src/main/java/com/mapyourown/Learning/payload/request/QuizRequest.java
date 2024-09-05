package com.mapyourown.Learning.payload.request;

import jakarta.validation.constraints.NotBlank;

public class QuizRequest {

    @NotBlank
    private String name;

    private Long number;

    private Long courseOrder;

    private Long minPassScore;

    private boolean isPassRequired;

    private Long courseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getCourseOrder() {
        return courseOrder;
    }

    public void setCourseOrder(Long courseOrder) {
        this.courseOrder = courseOrder;
    }

    public Long getMinPassScore() {
        return minPassScore;
    }

    public void setMinPassScore(Long minPassScore) {
        this.minPassScore = minPassScore;
    }

    public boolean isPassRequired() {
        return isPassRequired;
    }

    public void setPassRequired(boolean passRequired) {
        isPassRequired = passRequired;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
