package com.example.diceroller2.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DiceDao {

    @Transaction
    @Query("SELECT * FROM diceset")
    DiceSetWithDice getDiceSetWithDice();

    @Query("SELECT * FROM dice WHERE diceSetID IS :diceSetId")
    List<Dice> getADiceSetDice(long diceSetId);

    @Insert
    long addDice(Dice dice);
}
