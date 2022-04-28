package com.example.diceroller2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DiceSet {

    @PrimaryKey(autoGenerate = true)
    public long diceSetID;

    @ColumnInfo
    public long characterID;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String descriptor;

}
