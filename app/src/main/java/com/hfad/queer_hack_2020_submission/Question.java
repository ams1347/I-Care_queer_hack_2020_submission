package com.hfad.queer_hack_2020_submission;

public class Question {
    private String question;
    private String type;
    private Boolean isHabit;

    public Question() {
    }

    public Question(String question) {
        this.question = question;
        this.type = "yesNo";
        this.isHabit = false;
    }

    public Question(String question, String type) {
        this.question = question;
        this.type = type;
        this.isHabit = false;
    }


    public Question(String question, String type, Boolean isHabit) {
        this.question = question;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
