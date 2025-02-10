package com.mapyourown.Learning.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_question")
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_title")
    @NotBlank
    private String questionTitle;

    @Column (name="questionType")
    private int questionType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<QuizAnswer> quizAnswers = new ArrayList<>();

    public QuizQuestion(){}

    public QuizQuestion(String questionTitle, int questionType,Quiz quiz){
        this.questionTitle = questionTitle;
        this.questionType = questionType;
        this.quiz = quiz;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<QuizAnswer> getQuizAnswers() {
        return quizAnswers;
    }

    public void setQuizAnswers(List<QuizAnswer> quizAnswers)
    {
        this.quizAnswers = quizAnswers;
        for(QuizAnswer aw: quizAnswers){
            aw.setQuizQuestion(this);
        }
    }
    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }
}
