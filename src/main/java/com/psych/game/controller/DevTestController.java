package com.psych.game.controller;

import com.psych.game.model.*;
import com.psych.game.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/dev-test")
public class DevTestController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private PlayerAnswerRepository playerAnswerRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    String hello(){
        return "Hello World";
    }

    @GetMapping("/populate")
    public String populateDB(){


        gameRepository.deleteAll();

        roundRepository.deleteAll();
        playerAnswerRepository.deleteAll();
        questionRepository.deleteAll();
        playerRepository.deleteAll();
        Player Ramesh = new Player.Builder()
                .alias("Ram")
                .email("ramesh@gmail.com")
                .saltedHashedPassword("Ram@1996")
                .build();
        playerRepository.save(Ramesh);

        Player Arun = new Player.Builder()
                .alias("Deadpool")
                .email("Arun@gmail.com")
                .saltedHashedPassword("Arun@1996")
                .build();
        playerRepository.save(Arun);

        Player Gayathri = new Player.Builder()
                .alias("Gaya")
                .email("gayathri@gmail.com")
                .saltedHashedPassword("Gayathri@1996")
                .build();
        playerRepository.save(Gayathri);

        Player Monica = new Player.Builder()
                .alias("Moni")
                .email("monica@gmail.com")
                .saltedHashedPassword("Monica@1996")
                .build();
       playerRepository.save(Monica);

        Player Seeta = new Player.Builder()
                .alias("S")
                .email("seeta@gmail.com")
                .saltedHashedPassword("Seeta@1996")
                .build();
        playerRepository.save(Seeta);


        /* Sai = new Player.Builder()
                .alias("Sai")
                .email("sai@gmail.com")
                .saltedHashedPassword("Sai96")
                .build();
        // playerRepository.save(Arun);

        Player Krishna = new Player.Builder()
                .alias("Krishna")
                .email("krishna@gmail.com")
                .saltedHashedPassword("Krishna@1996")
                .build();
        //playerRepository.save(Gayathri);

        Player Suresh = new Player.Builder()
                .alias("Suri")
                .email("suresh@gmail.com")
                .saltedHashedPassword("Suresh@1996")
                .build();
        // playerRepository.save(Monica);
        Player Siva = new Player.Builder()
                .alias("siva")
                .email("siva@gmail.com")
                .saltedHashedPassword("Siva96")
                .build();
        // playerRepository.save(Arun);

        Player Mahesh = new Player.Builder()
                .alias("Mahesh")
                .email("mahesh@gmail.com")
                .saltedHashedPassword("Mahesh@1996")
                .build();
        //playerRepository.save(Gayathri);

        Player Sumanth = new Player.Builder()
                .alias("Sumanth")
                .email("sumanth@gmail.com")
                .saltedHashedPassword("Sumanth@1996")
                .build();
    */
        Question question1= new Question(
                "what is the vey important poneglyph",
                "Rio poneglyph",
                GameMode.IS_THIS_A_FACT
        );
       questionRepository.save(question1);

        Question question2= new Question(
                "if Gayathri were IKEA's furniture," +
                        "what is it called? ",
                "Flower vase",
                GameMode.IS_THIS_A_FACT
        );
        questionRepository.save(question2);
        Game game1= new Game(GameMode.IS_THIS_A_FACT,true,10,Ramesh);
        gameRepository.save(game1);
//        Game game1 = new Game.Builder()
//                .leader(Ramesh)
//                .players(new HashSet<>(Arrays.asList(Ramesh,Monica,Gayathri)))
//                .hasEllen(true)
//                .round(5)
//                .gameMode(GameMode.IS_THIS_A_FACT)
//                .build();
//        gameRepository.save(game1);
//
//        PlayerAnswer rameshAnswer = new PlayerAnswer.Builder().
//                player(Ramesh).
//                playerAnswer("Large Sofa")
//                .build();
//        playerAnswerRepository.save(rameshAnswer);
//        PlayerAnswer monicaAnswer = new PlayerAnswer.Builder().
//                player(Monica).
//                playerAnswer("Mirror")
//                .build();
//
//        playerAnswerRepository.save(monicaAnswer);
//        PlayerAnswer gayathriAnswer = new PlayerAnswer.Builder().
//                player(Gayathri).
//                playerAnswer("Flower vase")
//                .build();
//
//        playerAnswerRepository.save(gayathriAnswer);
//        Map<Player,PlayerAnswer> playerAnswers1= new HashMap<>(){
//            {
//                put(Ramesh, rameshAnswer);
//                put(Monica, monicaAnswer);
//                put(Gayathri, gayathriAnswer);
//            }};
//        //playerAnswerRepository.save(playerAnswers1);
//        Round round1= new Round.Builder()
//                .roundNumber(1)
//                .question(question2)
//                .playerAnswers(playerAnswers1)
//                .build();
//        roundRepository.save(round1);
//
//
       return "populated";
    }

    @GetMapping("/questions")
    public List<Question> getAllQuestions(){

        return questionRepository.findAll();
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers(){

        return playerRepository.findAll();
    }

    @GetMapping("/games")
     public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    @GetMapping("/rounds")
    public List<Round> getAllRounds(){
        return roundRepository.findAll();
    }

    @GetMapping("/player/{id}")
    public Player getPlayerById(@PathVariable(name="id") Long id ){
        return playerRepository.findById(id).orElseThrow();
    }

    @GetMapping("/playerAnswers")
    public List<PlayerAnswer> getAllPlayerAnswers(){
        return playerAnswerRepository.findAll();
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
