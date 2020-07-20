package com.example.vendingmachine.stats;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stat {

    @Id
    @GeneratedValue
    Integer statId;
    @Column(unique = true)
    @Enumerated(value = EnumType.STRING)
    StatKey key;
    String description;
    String value;

    public static Stat of(StatKey key, String description, String value){
        return new Stat(null, key, description, value);
    }
}
