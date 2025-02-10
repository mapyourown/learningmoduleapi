package com.mapyourown.Learning.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @NotBlank
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @Column(name = "is_progress")
    private boolean isProgress;
    @Column(name = "is_published")
    private  boolean isPublished;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseModule> courseModules = new ArrayList<>();


    public Course() {

    }

    public Course(String name, String description, boolean isPublished, double price, boolean isProgress) {
        this.name = name;
        this.description = description;
        this.isPublished = isPublished;
        this.price = price;
        this.isProgress = isProgress;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isProgress() {
        return isProgress;
    }

    public void setProgress(boolean progress) {
        isProgress = progress;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
        for(Enrollment e : enrollments) {
            e.setCourse(this);
        }
    }
    public List<CourseModule> getModules() {
        return courseModules;
    }

    public void setModules(List<CourseModule> courseModules) {
        this.courseModules = courseModules;
        for(CourseModule m : courseModules) {
            m.setCourse(this);
        }
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + ", desc=" + description + ", isPublished=" + isPublished + "]";
    }
}
