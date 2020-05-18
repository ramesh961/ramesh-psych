package com.psych.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="players")
public class Player extends User {
    @NotBlank
    @Getter
    @Setter
    private String alias;

    @Getter
    @Setter
    private String psychFaceURL;

    @Getter
    @Setter
    private String picURL;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "players")
    private Set<Game> games = new HashSet<>();

    @Getter
    @Setter
    @OneToOne(cascade=CascadeType.ALL)
    private Stat stats= new Stat();

}
//playerid gameid