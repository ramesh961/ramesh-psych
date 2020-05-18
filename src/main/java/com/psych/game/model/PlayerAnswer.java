package com.psych.game.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

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
    private Round round;
}
