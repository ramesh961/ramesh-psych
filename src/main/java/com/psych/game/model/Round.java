package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name="rounds")
public class Round extends Auditable {
    @Getter
    @Setter
    @ManyToOne
    @NotNull
    @JsonBackReference
    private Game game;

    @Getter
    @Setter
    @NotNull
    @ManyToOne
    private Question question;

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    private Map<Player,PlayerAnswer> playerAnswers = new HashMap<>();

    @Getter
    @Setter
    @ManyToMany(cascade=CascadeType.ALL)
    @JsonManagedReference
    private Map<Player,PlayerAnswer> selectedAnswers= new HashMap<>();

    @Getter
    @Setter
    @ManyToOne
    private EllenAnswer ellenAnswer;

    @Getter
    @Setter
    @NotNull
    private int roundNumber;

    private Round(Builder builder) {
        setQuestion(builder.question);
        setPlayerAnswers(builder.playerAnswers);
        setEllenAnswer(builder.ellenAnswer);
        setRoundNumber(builder.roundNumber);
    }

    public Round(){}
    public static final class Builder {
        private Question question;
        private Map<Player, PlayerAnswer> playerAnswers;
        private EllenAnswer ellenAnswer;
        private int roundNumber;

        public Builder() {
        }

        public Builder question(Question val) {
            question = val;
            return this;
        }

        public Builder playerAnswers(Map<Player, PlayerAnswer> val) {
            playerAnswers = val;
            return this;
        }

        public Builder ellenAnswer(EllenAnswer val) {
            ellenAnswer = val;
            return this;
        }

        public Builder roundNumber(int val) {
            roundNumber = val;
            return this;
        }

        public Round build() {
            return new Round(this);
        }
    }
}
