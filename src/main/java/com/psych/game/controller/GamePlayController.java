package com.psych.game.controller;

import com.psych.game.exceptions.InvalidGameActionException;
import com.psych.game.model.Game;
import com.psych.game.model.Player;
import com.psych.game.model.PlayerAnswer;
import com.psych.game.repositories.PlayerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class GamePlayController {
    private PlayerRepository playerRepository;
    @GetMapping("/")
    public Object play(Authentication authentication){
        return authentication.getCredentials();
    }

    @GetMapping("/addPlayer")
    public void addPlayer(Player player) throws Exception {
        Game currentGame = new Game(); // current game
        currentGame.addPlayer(player);
    }

    @GetMapping("/submitAnswer/{answer}")
    public void submitAnswer(Authentication authentication,@PathVariable(name="answer") String answer) throws InvalidGameActionException {
        Player player= playerRepository.findByEmail( authentication.getName() ).orElseThrow();
        player.getCurrentGame().submitAnswer(player,answer);
    }
    @GetMapping("/removePlayer")
    public void removePlayer(Player playerToBeRemoved) throws Exception {
        //Player currentGameLeader= playerRepository.findByEmail(authentication.getName()).orElseThrow();
        Game currentGame=playerToBeRemoved.getCurrentGame();
        currentGame.removePlayer(playerToBeRemoved);
    }

    @GetMapping("/startGame")
    public void startGame(Player player) throws InvalidGameActionException {
        player.getCurrentGame().startGame(player);
    }

    @GetMapping("/submitAnswer")
    public void submitAnswer(Player player,String answer) throws InvalidGameActionException {
        player.getCurrentGame().submitAnswer(player,answer);
    }

    @GetMapping("/selectAnswer")
    public void selectAnswer(Player player, PlayerAnswer playerAnswer) throws InvalidGameActionException {
        player.getCurrentGame().selectAnswer(player,playerAnswer);
    }

    @GetMapping("/playerIsReady")
    public void playerIsReady(Player player) throws InvalidGameActionException {
        player.getCurrentGame().playerIsReady(player);

    }
    @GetMapping("/playerIsNotReady")
    public void playerIsNotReady(Player player) throws InvalidGameActionException {
        player.getCurrentGame().playerIsNotReady(player);
    }

    @GetMapping("/endGame")
    public void endGame(Player player) throws InvalidGameActionException {
        player.getCurrentGame().endGame(player);
    }




}
