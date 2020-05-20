package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="playerAnswers")
public class PlayerAnswer extends Auditable {

    @Getter
    @Setter
    @NotNull
    private String playerAnswer;

    @Getter
    @Setter
    @ManyToOne
    private Player player;

    @Getter
    @Setter
    @ManyToOne
    @JsonBackReference
    private Round round;


    public PlayerAnswer(){}

    private PlayerAnswer(Builder builder) {
        setPlayerAnswer(builder.playerAnswer);
        setPlayer(builder.player);
        setRound(builder.round);
    }

    public static final class Builder {
        private String playerAnswer;
        private Player player;
        private Round round;

        public Builder() {
        }

        public Builder playerAnswer(String val) {
            playerAnswer = val;
            return this;
        }

        public Builder player(Player val) {
            player = val;
            return this;
        }

        public Builder round(Round val) {
            round = val;
            return this;
        }

        public PlayerAnswer build() {
            return new PlayerAnswer(this);
        }
    }
}
