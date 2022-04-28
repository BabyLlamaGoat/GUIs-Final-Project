package com.example.diceroller2.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CharacterWithDiceSets {
    @Embedded
    public Character character;

    @Relation(
            parentColumn = "characterID",
            entityColumn = "diceSetID"
    )
    public List<DiceSet> diceSets;

    @Override
    public String toString() {
        return "CharacterWithDiceSets{" +
                "character=" + character +
                ", diceSets=" + diceSets +
                '}';
    }
}
