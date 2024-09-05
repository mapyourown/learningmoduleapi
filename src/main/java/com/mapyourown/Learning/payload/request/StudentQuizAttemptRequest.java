package com.mapyourown.Learning.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mapyourown.Learning.models.Quiz;
import com.mapyourown.Learning.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class StudentQuizAttemptRequest {

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
    private Date attemptDatetime;

    private Long scoreAchieved;

    private Long quizId;

    private Long userId;

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

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
