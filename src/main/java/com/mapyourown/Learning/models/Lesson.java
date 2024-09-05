package com.mapyourown.Learning.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "lessons",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private Long number;

    @Column(name = "video_url")
    private String videoUrl;

    @Lob
    @Column(name = "lesson_details", length = 65536)
    private String lessonDetails;

    @Column(name = "course_orders")
    private Long courseOrders;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_module_id")
    private CourseModule courseModule;


    public Lesson(){

    }
    public Lesson(String name, Long number, String videoUrl, String lessonDetails, Long courseOrders, CourseModule courseModules){
        this.name = name;
        this.number = number;
        this.videoUrl = videoUrl;
        this.lessonDetails = lessonDetails;
        this.courseOrders = courseOrders;
        this.courseModule = courseModules;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


    public void setCourseModule(CourseModule courseModule) {
        this.courseModule = courseModule;
    }

    @Override
    public String toString() {
        return "Lesson [id=" + id + ", name=" + name + " name=\" + name + \"]";
    }
}
