package com.mapyourown.Learning.payload.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;

public class LessonRequest {

    @NotBlank
    private String name;

    private Long number;

    private String videoUrl;

    private String lessonDetails;

    private Long courseOrders;

    private Long moduleId;

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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getLessonDetails() {
        return lessonDetails;
    }

    public void setLessonDetails(String lessonDetails) {
        this.lessonDetails = lessonDetails;
    }

    public Long getCourseOrders() {
        return courseOrders;
    }

    public void setCourseOrders(Long courseOrders) {
        this.courseOrders = courseOrders;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
}
