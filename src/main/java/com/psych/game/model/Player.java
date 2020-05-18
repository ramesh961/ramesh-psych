package com.psych.game.model;

import com.psych.game.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
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

    public Player(){

    }
    private Player(Builder builder) {
        setEmail(builder.email);
        setSaltedHashedPassword(builder.saltedHashedPassword);
        setAlias(builder.alias);
        setPsychFaceURL(builder.psychFaceURL);
        setPicURL(builder.picURL);
    }

    public static final class Builder {
        private @NotBlank @Email String email;
        private @NotBlank String saltedHashedPassword;
        private @NotBlank String alias;
        private String psychFaceURL;
        private String picURL;

        public Builder() {
        }

        public Builder email(@NotBlank @Email String val) {
            email = val;
            return this;
        }

        public Builder saltedHashedPassword(@NotBlank String val) {
            saltedHashedPassword = val;
            return this;
        }

        public Builder alias(@NotBlank String val) {
            alias = val;
            return this;
        }

        public Builder psychFaceURL(String val) {
            psychFaceURL = val;
            return this;
        }

        public Builder picURL(String val) {
            picURL = val;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
//playerid gameid