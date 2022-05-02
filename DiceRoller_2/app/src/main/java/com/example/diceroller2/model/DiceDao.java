package com.example.diceroller2.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DiceDao {

    @Query("SELECT * FROM dice WHERE diceSetID IS :diceSetId")
    List<Dice> getADiceSetDice(long diceSetId);

    @Insert
    long addDice(Dice dice);

    @Delete
    void deleteDie(Dice deletedDie);

    @Update
    void updateDie(Dice die);
}
