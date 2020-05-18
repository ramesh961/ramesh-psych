package com.psych.game;

import com.psych.game.model.GameMode;
import com.psych.game.model.Player;
import com.psych.game.model.Question;
import com.psych.game.repositories.PlayerRepository;
import com.psych.game.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dev-test")
public class HelloWorldController {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    String hello(){
        return "Hello World";
    }

    @GetMapping("/populate")
    public String populateDB(){
        Player Luffy = new Player.Builder()
                .alias("B Leffy Nayak")
                .email("luffy@gmail.com")
                .saltedHashedPassword("luffy")
                .build();
        playerRepository.save(Luffy);

        Player Robin = new Player.Builder()
                .alias("Robin Sharma")
                .email("robin@gmail.com")
                .saltedHashedPassword("robin")
                .build();
        playerRepository.save(Robin);

        questionRepository.save(new Question(
                "what is the vey important poneglyph",
                "Rio poneglyph",
                GameMode.IS_THIS_A_FACT
        ));
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

    @GetMapping("/player/{id}")
    public Player getPlayerById(@PathVariable(name="id") Long id ){
        return playerRepository.findById(id).orElseThrow();
    }


}
