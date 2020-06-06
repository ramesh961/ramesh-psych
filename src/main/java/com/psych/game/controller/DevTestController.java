package com.psych.game.controller;

import com.psych.game.Constants;
import com.psych.game.Utils;
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
    @Autowired
    private GameModeRepository gameModeRepository;

    @GetMapping("/")
    String hello(){
        return "Hello World";
    }

    @GetMapping("/populate")
    public String populateDB() throws Exception {


        for(Player player:playerRepository.findAll()){
            player.setGames(null);
            player.setCurrentGame(null);
            playerRepository.save(player);
        }
        gameRepository.deleteAll();
        roundRepository.deleteAll();
        playerAnswerRepository.deleteAll();
        questionRepository.deleteAll();
        playerRepository.deleteAll();
        gameModeRepository.deleteAll();
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

        GameMode isThisAFact= new GameMode("IS_THIS_A_FACT?","https://img.republicworld.com/republic-prod/stories/promolarge/xxhdpi/lnga5ufojt04dzqv_1585003042.jpeg?tr=w-812,h-464","Is this a fact description");
        gameModeRepository.save(isThisAFact);
        gameModeRepository.save(new GameMode("WORD UP","https://img.republicworld.com/republic-prod/stories/promolarge/xxhdpi/lnga5ufojt04dzqv_1585003042.jpeg?tr=w-812,h-464","word up description"));
        gameModeRepository.save(new GameMode("Un-Scramble","https://img.republicworld.com/republic-prod/stories/promolarge/xxhdpi/lnga5ufojt04dzqv_1585003042.jpeg?tr=w-812,h-464","movie buff description"));
//        Question question1= new Question(
//                "what is the vey important poneglyph",
//                "Rio poneglyph",
//                 isThisAFact
//        );
//       questionRepository.save(question1);
//
//        Question question2= new Question(
//                "if Gayathri were IKEA's furniture," +
//                        "what is it called? ",
//                "Flower vase",
//                isThisAFact
//        );
//        questionRepository.save(question2);



        List<Question> questions = new ArrayList<>();
        for(Map.Entry<String,String> qaFile : Constants.QA_FILES.entrySet()){
            System.out.println("game Mode "+qaFile.getValue());
            GameMode gameMode= gameModeRepository.findByName(qaFile.getValue()).orElseThrow();
            for(Map.Entry<String,String> questionAnswer : Utils.readDataFromFile(qaFile.getKey()).entrySet()){
                questions.add(new Question(questionAnswer.getKey(),questionAnswer.getValue(),gameMode));
            }
        }
        questionRepository.saveAll(questions);

        Game game1= new Game(isThisAFact,true,10,Ramesh);
        game1.addPlayer(Arun);
        gameRepository.save(game1);

        game1.startGame(Ramesh);
        gameRepository.save(game1);
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
