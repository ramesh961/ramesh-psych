package com.psych.game;

import com.psych.game.config.ApplicationContextProvider;
import com.psych.game.config.SpringConfiguration;
import com.psych.game.model.EllenAnswer;
import com.psych.game.model.GameMode;
import com.psych.game.model.Question;
import com.psych.game.repositories.EllenAnswerRepository;
import com.psych.game.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.aspectj.SpringConfiguredConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.swing.text.Utilities;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    private static QuestionRepository questionRepository;
    private static EllenAnswerRepository ellenAnswerRepository;
    private static List<String> wordsList;
    private static Map<String,Integer> wordIndices;
    static {
        questionRepository= (QuestionRepository) ApplicationContextProvider.getApplicationContext().getBean("questionRepository");
        ellenAnswerRepository= (EllenAnswerRepository) ApplicationContextProvider.getApplicationContext().getBean("ellenAnswerRepository");

        try {
            BufferedReader br = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:data/words.txt")));
            String word;
            int count=0;
            wordsList = new ArrayList<>();
            wordIndices= new HashMap<>();
            do{
                word=br.readLine();
                if(word==null) break;
                word= word.strip();
                wordIndices.put(word,count);
                wordsList.add(word);
                count++;
            }while(word != null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Question getRandomQuestion(GameMode gameMode) {
        return questionRepository.getRandomQuestion(gameMode.getId());
    }

    public static EllenAnswer getRandomEllenAnswer(Question question) {
        return ellenAnswerRepository.getRandomEllenAnswer(question.getId());
    }


    public String getSecretCodeFromGameId(Long gameId){
        int base= wordsList.size();
        String secretCode="";
        while(gameId>0){
            secretCode = secretCode + " " + gameId%base;
            gameId/=base;
        }
        return secretCode.substring(0,wordsList.size()-1);
    }
    public int getGameIdFromSecretCode(String secretCode){
        String []words = secretCode.split("");
        //reverse the list
        int gameId=0;
        int base= words.length;
        for(String word:wordsList) {
            int index = wordIndices.get(word);
            gameId = gameId * base + index;
        }
        return gameId;
    }
    public static Map<String,String> readDataFromFile(String fileName){
        Map<String,String> questionAnswers= new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(ResourceUtils.getFile(fileName)));
            String questionText;
            String answerText;
            int count=0;

            do{
                questionText= br.readLine();
                answerText = br.readLine();
                if(questionText==null || answerText==null) break;
                questionText = questionText.strip();
                answerText= answerText.strip();
                questionAnswers.put(questionText,answerText);
                count++;
            }while(questionText!=null && count<Constants.Count_To_Read);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionAnswers;
    }

}
