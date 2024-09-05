package com.mapyourown.Learning.payload.request;

public class StudentLessonRequest {


    private Long lessonId;
    private Long userId;

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
