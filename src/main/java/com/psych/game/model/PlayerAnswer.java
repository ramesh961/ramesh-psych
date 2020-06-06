package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="playerAnswers")
public class PlayerAnswer extends Auditable {

    @Getter
    @Setter
    @NotBlank
    private String playerAnswer;

    @Getter
    @Setter
    @ManyToOne
    @JsonIdentityReference
    private Player player;

    @Getter
    @Setter
    @ManyToOne
    @JsonBackReference
    @NotNull
    private Round round;


    public PlayerAnswer(){}

    public PlayerAnswer(String playerAnswer, Player player, Round round) {
        this.playerAnswer = playerAnswer;
        this.player = player;
        this.round = round;
    }


}
