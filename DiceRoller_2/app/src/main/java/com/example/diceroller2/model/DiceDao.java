package com.example.diceroller2.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface DiceDao {

    @Transaction
    @Query("SELECT * FROM diceset")
    DiceSetWithDice getDiceSetWithDice();

    @Insert
    long addDice(Dice dice);
}
