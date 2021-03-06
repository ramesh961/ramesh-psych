package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.psych.game.Utils;
import com.psych.game.exceptions.InvalidGameActionException;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.hibernate.engine.internal.Cascade;
import org.hibernate.loader.plan.build.internal.spaces.JoinImpl;

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
    @OrderBy(value="round_number asc")
    @OneToMany(mappedBy = "game",cascade=CascadeType.ALL)
    private List<Round> rounds = new ArrayList<>();

    @Getter
    @Setter
    @NotNull
    @ManyToOne   /// also check one game can also have multiple game modes, in b/w i can change gamemode
    @JsonIdentityReference
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
    private int numRound=10;

    @Getter
    @Setter
    @ManyToOne
    @NotNull
    @JsonIdentityReference
    private Player leader;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Getter
    @Setter
    @ManyToMany
    private Set<Player> readyPlayers;


    public Game(){}
    public Game(GameMode gameMode, Boolean hasEllen, int numRound, Player leader) {
        this.gameMode = gameMode;
        this.hasEllen = hasEllen;
        this.numRound = numRound;
        this.leader = leader;
        players.add(leader);
        this.leader.setCurrentGame(this);
        this.gameStatus=GameStatus.PLAYERS_JOINING;
    }


    public void addPlayer(Player player) throws Exception{
        if(!gameStatus.equals(GameStatus.PLAYERS_JOINING))
            throw new InvalidGameActionException("game is already started");
        players.add(player);
        player.setCurrentGame(this);
    }
    public void removePlayer(Player player) throws Exception{
        if(!players.contains(player))
            throw new InvalidGameActionException("player is not in the game");
        players.remove(player);
        if(player.getCurrentGame().equals(this))
            player.setCurrentGame(null);
        if( (players.size()==0) || ( players.size()==1 && !gameStatus.equals(GameStatus.PLAYERS_JOINING)))
            endGame(leader);
            
    }

    public void endGame(Player player) throws InvalidGameActionException {
        if(!leader.equals(player)){
            throw new InvalidGameActionException("only leader is allowed to end the game");
        }
        gameStatus=GameStatus.ENDED;
        for(Player eachPlayer:players){
            if(eachPlayer.getCurrentGame().equals(this))
                eachPlayer.setCurrentGame(null);
        }
    }

    public void startGame(Player player) throws InvalidGameActionException {
        if(!players.contains(player))
            throw new InvalidGameActionException("player is not in the game");
        if(!leader.equals(player))
            throw new InvalidGameActionException("only leader is allowed to start the game");
        if(!gameStatus.equals(GameStatus.PLAYERS_JOINING))
            throw new InvalidGameActionException("Game already started");

        startNewRound();
    }

    private void startNewRound() {
        Question question = Utils.getRandomQuestion(gameMode);
        Round newRound= new Round(this, question,rounds.size()+1);

        if(hasEllen){
            newRound.setEllenAnswer(Utils.getRandomEllenAnswer(question));
        }
        rounds.add(newRound);
        gameStatus= GameStatus.SUBMITTING_ANSWERS;

    }
    
    public void submitAnswer(Player player, String answer) throws InvalidGameActionException {
        if(answer.length()==0)
            throw new InvalidGameActionException("answer can't be empty");
        if(!players.contains(player))
            throw new InvalidGameActionException("player is not there in the game");
        if(!gameStatus.equals(GameStatus.SUBMITTING_ANSWERS))
            throw new InvalidGameActionException("game is not accepting the answers at present");
        // duplicate answers check
        // if already submitted, he cannot submit
        Round currentRound= getCurrentRound(); /// current round
        System.out.println(" game id "+this.getId());
        System.out.println(" round no "+currentRound.getRoundNumber());
        currentRound.submitAnswer(player,answer);
        if(currentRound.allAnswersSubmitted(players.size()) )
            gameStatus= GameStatus.SELECTING_ANSWERS;
    }
    public void selectAnswer(Player player,PlayerAnswer playerAnswer) throws InvalidGameActionException {
        if(!players.contains(player))
            throw new InvalidGameActionException("player is not there in the game");
        if(!gameStatus.equals(GameStatus.SELECTING_ANSWERS))
            throw new InvalidGameActionException("you cannot select the answers at present");
        Round currentRound = getCurrentRound();
        currentRound.selectAnswer(player,playerAnswer);
        if(currentRound.allAnswersSelected(players.size()))
            if(rounds.size()==numRound)
                endGame(leader);
            else
                gameStatus=GameStatus.WAITING_FOR_READY;
    }

    private Round getCurrentRound() throws InvalidGameActionException {
        if(rounds.size()==0)
            throw new InvalidGameActionException("game not started");
        return rounds.get(rounds.size()-1);
     }

    public void playerIsReady(Player player) throws InvalidGameActionException {
        if(!players.contains(player))
            throw new InvalidGameActionException("no such player is in the game");
        if(!gameStatus.equals(GameStatus.WAITING_FOR_READY))
            throw new InvalidGameActionException("game is not waiting to be ready");
        readyPlayers.add(player);
        if(readyPlayers.size()==players.size())
            startNewRound();
    }



    public void playerIsNotReady(Player player) throws InvalidGameActionException {
         if(!players.contains(player))
             throw new InvalidGameActionException("no such player is in the game");
         if(!gameStatus.equals(GameStatus.WAITING_FOR_READY))
             throw new InvalidGameActionException("you cannot be not ready");
         readyPlayers.remove(player);

     }


    public JSONObject getGameState(){
        JSONObject state= new JSONObject();
        state.put("id",getId());
        state.put("rounds",getNumRound());
        state.put("gameMode",getGameMode().getName());
        JSONArray playerData= new JSONArray();
        for(Player gamePlayer: getPlayers()){
            JSONObject player = new JSONObject();
            player.put("playerAlias",gamePlayer.getAlias());
            playerData.add(player);
        }
        state.put("players",playerData);
        return state;
    }




}
