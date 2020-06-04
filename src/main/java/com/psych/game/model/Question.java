package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="question")
public class Question extends Auditable{

    public Question(){}

    public Question(String question, String correctAnswer, GameMode gameMode) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.gameMode = gameMode;
    }

    @Getter
    @Setter
    @NotNull
    private String question;

//    @OneToMany
//    private Round round;   May be not needed
//
    @NotNull
    @Getter
    @Setter
    private String correctAnswer;    // no need of Answer Class

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    @NotNull
    private Set<EllenAnswer> ellenAnswers = new HashSet<>();

    @Getter
    @Setter
    @JsonIdentityReference
    private GameMode gameMode;
}
