package com.stefan.password_generator;

/**
 * JPA entity for a saved password hash.
 * The generated ID identifies each stored record.
 */
import jakarta.persistence.*;

@Entity
public class PasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;

    public PasswordEntity(){}

    /** Returns the hash stored in this entity. */
    public String GetPassword(){
        return password;
    }

    /** Sets the hash to be persisted for this entity. */
    public void SetPassword(String password){
        this.password = password;
    }
}
