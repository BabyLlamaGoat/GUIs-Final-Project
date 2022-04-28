package com.example.diceroller2.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DiceSetWithDice {
    @Embedded
    public DiceSet diceSet;

    @Relation(
            parentColumn = "diceSetID",
            entityColumn = "diceID"
    )
    public List<Dice> dice;
}
