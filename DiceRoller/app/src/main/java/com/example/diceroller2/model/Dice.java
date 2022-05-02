package com.example.diceroller2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Random;

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

    public String roll(){
        StringBuilder sb = new StringBuilder();
        sb.append(count);
        sb.append("d");
        sb.append(sides);
        sb.append(": ");
        Random random = new Random();
        int total = 0;
        for (int i = 0; i < count; i++) {
            int result = random.nextInt(sides) + 1;
            total += result;
            sb.append(result);
            if (i < count - 1) {
                sb.append(", ");
            }
        }
        if (count > 1){
            sb.append(" = ");
            sb.append(total);
        }

        sb.append("\n");
        return sb.toString();
    }
}
