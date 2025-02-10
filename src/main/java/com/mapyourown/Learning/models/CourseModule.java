package com.mapyourown.Learning.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course_modules",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class CourseModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;


    @OneToMany(mappedBy = "courseModule")
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizList = new ArrayList<>();

    public CourseModule(){

    }

    public CourseModule(String name, Course course) {

        this.name = name;
        this.course = course;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
        for (Lesson l: lessons){
            l.setCourseModule(this);
        }
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
        for (Quiz q : quizList){
            q.setCourseModule(this);
        }
    }

    @Override
    public String toString() {
        return "CourseModule [id=" + id + ", name=" + name + "]";
    }

}
