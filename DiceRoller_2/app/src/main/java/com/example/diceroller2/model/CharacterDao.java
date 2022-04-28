package com.example.diceroller2.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CharacterDao {

    @Query("SELECT * FROM character")
    List<Character> getCharacters();

    @Insert
    long saveNewCharacter(Character character);

    @Delete
    void deleteCharacter(Character character);

}
