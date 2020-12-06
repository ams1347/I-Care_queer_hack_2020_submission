package com.hfad.queer_hack_2020_submission;

public class Question {
    private String question;
    private Boolean isHabit;

    public Question() {
    }

    public Question(String question) {
        this.question = question;
        this.isHabit = false;
    }

    public Question(String question, Boolean isHabit) {
        this.question = question;
        this.isHabit = isHabit;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String name) {
        this.question = name;
    }

    public Boolean getHabit() {
        return isHabit;
    }

    public void setHabit(Boolean habit) {
        isHabit = habit;
    }
}
