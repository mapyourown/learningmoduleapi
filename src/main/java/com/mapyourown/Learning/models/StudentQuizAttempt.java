package com.mapyourown.Learning.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "student_quiz_attempt")
public class StudentQuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "attempt_datetime")
    private Date attemptDatetime;
    @Column(name = "score_achieved")
    private Long scoreAchieved;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public StudentQuizAttempt(){

    }
    public StudentQuizAttempt(Date attemptDatetime, Long scoreAchieved, Quiz quiz, User user){
        this.attemptDatetime = attemptDatetime;
        this.scoreAchieved = scoreAchieved;
        this.quiz = quiz;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAttemptDatetime() {
        return attemptDatetime;
    }

    public void setAttemptDatetime(Date attemptDatetime) {
        this.attemptDatetime = attemptDatetime;
    }

    public Long getScoreAchieved() {
        return scoreAchieved;
    }

    public void setScoreAchieved(Long scoreAchieved) {
        this.scoreAchieved = scoreAchieved;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
