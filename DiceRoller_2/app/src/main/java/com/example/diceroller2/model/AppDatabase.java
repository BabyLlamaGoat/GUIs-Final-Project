package com.example.diceroller2.model;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Character.class, DiceSet.class, Dice.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CharacterDao getCharacterDao();
    public abstract DiceSetDao getDiceSetDao();
    public abstract DiceDao getDiceDao();
}
