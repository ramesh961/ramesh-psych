package com.psych.game.repositories;

import com.psych.game.model.EllenAnswer;
import com.psych.game.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EllenAnswerRepository extends JpaRepository<EllenAnswer,Long> {
    @Query(value="SELECT * from ellen_answers where question_id= :questionId ORDER BY RANDOM() limit 1", nativeQuery = true) //todo
    EllenAnswer getRandomEllenAnswer(Long questionId);
}
