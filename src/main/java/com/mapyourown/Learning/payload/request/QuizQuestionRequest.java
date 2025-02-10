package com.mapyourown.Learning.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mapyourown.Learning.models.Quiz;
import com.mapyourown.Learning.models.QuizAnswer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestionRequest {
    @NotBlank
    private String questionTitle;

    private int questionType;
    private Long quizId;

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }


}
