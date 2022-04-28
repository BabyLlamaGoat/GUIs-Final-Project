package com.example.diceroller2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Character {

    @PrimaryKey(autoGenerate = true)
    public long characterID;

    @ColumnInfo
    public String name;


}
