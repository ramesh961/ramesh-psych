package com.psych.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User extends Auditable {

    @NotBlank
    @Column(unique=true)
    @Email
    @Getter
    @Setter
    private String email;

    @NotBlank
    @Getter
    @Setter
    private String saltedHashedPassword;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles= new HashSet<>();

    public User(){}

    public User(User user) {
        email= user.email;
        saltedHashedPassword=user.saltedHashedPassword;
        roles=user.getRoles();
    }
}
