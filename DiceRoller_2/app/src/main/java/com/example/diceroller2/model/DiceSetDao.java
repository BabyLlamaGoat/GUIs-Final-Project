package com.example.diceroller2.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DiceSetDao {

    @Query("SELECT * FROM diceset WHERE characterID IS :characterId")
    List<DiceSet> getACharacterDiceSets(long characterId);

    @Insert
    long createNewDiceSet(DiceSet diceSet);

    @Update
    void updateDiceSet(DiceSet diceSet);

}
