package com.psych.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="gameMode")
public class GameMode extends Auditable {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String pic;
    @Getter
    @Setter
    private String description;

    public GameMode(){}

    public GameMode(String name, String pic, String description) {
        this.name = name;
        this.pic = pic;
        this.description = description;
    }
}
