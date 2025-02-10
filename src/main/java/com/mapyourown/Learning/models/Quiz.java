package com.mapyourown.Learning.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "number")
    private Long number;

    @Column(name = "course_order")
    private Long courseOrder;

    @Column(name = "min_pass_score")
    private Long minPassScore;

    @Column(name = "is_pass_required")
    private boolean isPassRequired;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_module_id")
    private CourseModule courseModule;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizQuestion> quizQuestions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentQuizAttempt> studentQuizAttempts = new ArrayList<>();

    public Long getId() {
        return Id;
    }

    public Quiz(String name, Long number, Long courseOrder, Long minPassScore, boolean isPassRequired, CourseModule courseModule  ){
        this.name = name;
        this.number = number;
        this.courseOrder = courseOrder;
        this.minPassScore = minPassScore;
        this.isPassRequired = isPassRequired;
        this.courseModule = courseModule;
    }
    public Quiz(){}

    public void setId(Long id) {
        Id = id;
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

    public CourseModule getCourseModule() {
        return courseModule;
    }

    public void setCourseModule(CourseModule courseModule) {
        this.courseModule = courseModule;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public List<StudentQuizAttempt> getStudentQuizAttempts() {
        return studentQuizAttempts;
    }

    public void setStudentQuizAttempts(List<StudentQuizAttempt> studentQuizAttempts) {
        this.studentQuizAttempts = studentQuizAttempts;
    }





}
