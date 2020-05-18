package com.psych.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="games")
public class Game extends Auditable {

    @Getter
    @Setter
    @ManyToMany
    private Set<Player> players= new HashSet<>();
    //gameId playerId

    @Getter
    @Setter
    @OneToMany(mappedBy = "game",cascade=CascadeType.ALL)
    private List<Round> rounds = new ArrayList<>();

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private GameMode gameMode;

    @Getter
    @Setter
    @ManyToMany(cascade=CascadeType.ALL)
    private Map<Player,Stat> playerStats = new HashMap<>();

    @Getter
    @Setter
    private Boolean hasEllen = false;

    @Getter
    @Setter
    private int round=10;

    @Getter
    @Setter
    @ManyToOne
    private Player leader;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

}
