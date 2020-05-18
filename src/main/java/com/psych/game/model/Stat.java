package com.psych.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="stats")
public class Stat extends Auditable {
    @Getter
    @Setter
    private long gotPsychedCount=0L;

    @Getter
    @Setter
    private long psychedOthersCount=0L;

    @Getter
    @Setter
    private Long correctAnswers=0L;

}
