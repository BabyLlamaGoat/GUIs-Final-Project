package com.example.diceroller2.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public interface DiceSetDao {

    @Transaction
    @Query("SELECT * FROM character")
    CharacterWithDiceSets getCharacterWithDiceSets();

    @Insert
    long createNewDiceSet(DiceSet diceSet);

    @Update
    void updateDiceSet(DiceSet diceSet);

}
