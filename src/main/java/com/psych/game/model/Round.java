package com.psych.game.model;

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
    private Map<Player,PlayerAnswer> selectedAnswers= new HashMap<>();

    @Getter
    @Setter
    @ManyToOne
    private EllenAnswer ellenAnswer;

    @Getter
    @Setter
    @NotNull
    private int roundNumber;
}
