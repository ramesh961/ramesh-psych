package com.psych.game.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.psych.game.exceptions.InvalidGameActionException;
import com.psych.game.model.Game;
import com.psych.game.model.GameMode;
import com.psych.game.model.Player;
import com.psych.game.model.PlayerAnswer;
import com.psych.game.repositories.GameModeRepository;
import com.psych.game.repositories.GameRepository;
import com.psych.game.repositories.PlayerRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/play")
public class GamePlayController {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameModeRepository gameModeRepository;

    @Autowired
    private GameRepository gameRepository;

    public Player getCurrentPlayer(Authentication authentication){
        Player player= playerRepository.findByEmail(authentication.getName()).orElseThrow();

        //System.out.println(player.getAlias());
        return player;
    }

//    private JSONObject getData(Player player){
//
//    }
    @GetMapping("/createGame")
    public JSONObject createGame(Authentication authentication,
                                 @RequestParam(name="mode") String mode,
                                 @RequestParam(name="rounds") Integer numRounds,
                                 @RequestParam(name="hasEllen") Boolean hasEllen){
        Player leader=  getCurrentPlayer(authentication);
        GameMode gameMode= gameModeRepository.findByName(mode).orElseThrow();
        gameRepository.save(new Game(gameMode,hasEllen,numRounds,leader));
       return play(authentication);
    }



    @GetMapping("/")
    public JSONObject play(Authentication authentication){
        Player player=getCurrentPlayer(authentication);
        Game currentGame= player.getCurrentGame();
        JSONObject response= new JSONObject();
        response.put("playerAlias",player.getAlias());
        response.put("hello","world");
        response.put("currentGame",currentGame==null?null:currentGame.getId());

        if(currentGame==null){
            JSONArray gameModes= new JSONArray();
            for(GameMode mode:gameModeRepository.findAll()){
                JSONObject gameMode= new JSONObject();
                gameMode.put("title",mode.getName());
                gameMode.put("pic",mode.getPic());
                gameMode.put("description",mode.getDescription());
                gameModes.add(gameMode);
            }
            response.put("gameModes",gameModes);
        }
        else{
             response.put("gameState",currentGame.getGameState());
        }



        return response;


    }

    @GetMapping("/addPlayer")
    public JSONObject addPlayer(Authentication authentication,
                                @RequestParam(name="gameId") Long gameId) throws Exception {
        Game currentGame = gameRepository.findById(gameId).orElseThrow();
        Player joiningPlayer= getCurrentPlayer(authentication);
        //Game currentGame = new Game(); // current game

        currentGame.addPlayer(joiningPlayer);
        JSONObject jsonPlayerList= new JSONObject();
        JSONArray players= new JSONArray();
        System.out.println("players in the game are");
        for(Player playerInGame: currentGame.getPlayers()){
            JSONObject player= new JSONObject();
            player.put("name",playerInGame.getAlias());
            System.out.println(playerInGame.getAlias());
            players.add(player);
        }
        jsonPlayerList.put("players",players);
        return jsonPlayerList;
    }

//    @GetMapping("/start-game")
//    public String startGame(){
//
//    }


    @GetMapping("/ram-submit")
    public String ramSubmit() throws InvalidGameActionException {
        Player ramesh= playerRepository.findByEmail("ramesh@gmail.com").orElseThrow();
        System.out.println("player name 1"+ramesh.getAlias());
        Game game= ramesh.getCurrentGame();
        System.out.println("game leader "+game.getLeader().getAlias());
        game.submitAnswer(ramesh,"answer");
        return "done";
    }
    @GetMapping("/arun-submit")
    public String arunSubmit() throws InvalidGameActionException {
        Player arun= playerRepository.findByEmail("Arun@gmail.com").orElseThrow();
        System.out.println("player name 2"+arun.getAlias());
        Game game= arun.getCurrentGame();
        System.out.println("game leader "+game.getLeader().getAlias());
        game.submitAnswer(arun,"answer");
        return "done";
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
    public void startGame(Authentication authentication) throws InvalidGameActionException {
        Player player= getCurrentPlayer(authentication);
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
