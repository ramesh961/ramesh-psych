package com.psych.game;

import org.springframework.util.ResourceUtils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static int Count_To_Read=20;
    public static Map<String,String> QA_FILES= new HashMap<>();
    static{

        QA_FILES.put("classpath:data/qa_facts.txt" , "IS_THIS_A_FACT?");
        QA_FILES.put("classpath:data/qa_unscramble.txt" , "Un-Scramble");
        QA_FILES.put("classpath:data/qa_word_up.txt" , "WORD UP");

    }
}
