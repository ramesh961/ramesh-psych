package com.psych.game.repositories;

import com.psych.game.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {
   Optional<Player> findByEmail(String name);
}
