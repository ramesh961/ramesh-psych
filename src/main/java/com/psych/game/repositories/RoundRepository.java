package com.psych.game.repositories;

import com.psych.game.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round,Long> {
}
