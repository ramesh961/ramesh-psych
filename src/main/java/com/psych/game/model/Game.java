package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="games")
public class Game extends Auditable {

    @Getter
    @Setter
    @ManyToMany
    @JsonIdentityReference
    private Set<Player> players= new HashSet<>();
    //gameId playerId

    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "game",cascade=CascadeType.ALL)
    private List<Round> rounds = new ArrayList<>();

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private GameMode gameMode;

    @Getter
    @Setter
    @ManyToMany(cascade=CascadeType.ALL)
    @JsonIdentityReference
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
    @JsonIdentityReference
    private Player leader;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    private Game(Builder builder) {
        setPlayers(builder.players);
        setRounds(builder.rounds);
        setGameMode(builder.gameMode);
        setHasEllen(builder.hasEllen);
        setRound(builder.round);
        setLeader(builder.leader);
    }

    public Game(){

    }
    public static final class Builder {
        private Set<Player> players;
        private List<Round> rounds;
        private GameMode gameMode;
        private Boolean hasEllen;
        private int round;
        private Player leader;

        public Builder() {
        }

        public Builder players(Set<Player> val) {
            players = val;
            return this;
        }

        public Builder rounds(List<Round> val) {
            rounds = val;
            return this;
        }

        public Builder gameMode(GameMode val) {
            gameMode = val;
            return this;
        }

        public Builder hasEllen(Boolean val) {
            hasEllen = val;
            return this;
        }

        public Builder round(int val) {
            round = val;
            return this;
        }


        public Builder leader(Player val) {
            leader = val;
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
