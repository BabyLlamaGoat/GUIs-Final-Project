package com.example.diceroller2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dice {

    @PrimaryKey(autoGenerate = true)
    public long diceID;

    @ColumnInfo
    public long diceSetID;

    @ColumnInfo
    public int sides;

    @ColumnInfo
    public int count;

    @Override
    public String toString() {
        return "Dice{" +
                "diceID=" + diceID +
                ", diceSetID=" + diceSetID +
                ", sides=" + sides +
                ", count=" + count +
                '}';
    }
}
