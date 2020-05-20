package com.psych.game.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ellenAnswers")
public class EllenAnswer extends Auditable{

    @Getter
    @Setter
    @NotNull
    private String ellenAnswer;

    @Getter
    @Setter
    @ManyToOne
    @JsonBackReference
    private Question question;

    @Getter
    @Setter
    private Long votes= 0L;

}
