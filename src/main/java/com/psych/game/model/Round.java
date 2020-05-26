package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.psych.game.exceptions.InvalidGameActionException;
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

    public Round(){}

    public Round(Game game, Question question, int roundNumber) {
        this.game = game;
        this.question = question;
        this.roundNumber = roundNumber;
    }

    public void submitAnswer(Player player,String answer) throws InvalidGameActionException {

        if(playerAnswers.containsKey(player))
            throw new InvalidGameActionException("player has already submitted the answer");
        for(PlayerAnswer playerAnswer:playerAnswers.values()){
            if(playerAnswer.getPlayerAnswer().equals(answer))
                throw new InvalidGameActionException("duplicate answer!");
        }
        playerAnswers.put(player,new PlayerAnswer(answer,player,this));
    }

    public boolean allAnswersSubmitted(int playerSize){
        return playerAnswers.size()== playerSize;
    }

    public void selectAnswer(Player player, PlayerAnswer selectedAnswer) throws InvalidGameActionException {


        if(selectedAnswers.containsKey(player))
            throw new InvalidGameActionException("player has already selected the answer");
        if(selectedAnswer.getPlayer().equals(player))
            throw new InvalidGameActionException("you cannot select your own answer");
        selectedAnswers.put(player,selectedAnswer);
    }


    public boolean allAnswersSelected(int playerSize) {
        return playerAnswers.size()==playerSize;
    }



    private Round(Builder builder) {
        setQuestion(builder.question);
        setPlayerAnswers(builder.playerAnswers);
        setEllenAnswer(builder.ellenAnswer);
        setRoundNumber(builder.roundNumber);
    }

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
