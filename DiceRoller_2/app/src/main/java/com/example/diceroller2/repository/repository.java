package com.example.diceroller2.repository;

import android.content.Context;
import android.os.Handler;

import androidx.room.Room;

import com.example.diceroller2.model.AppDatabase;
import com.example.diceroller2.model.Character;
import com.example.diceroller2.model.Dice;
import com.example.diceroller2.model.DiceSet;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class repository {

    AppDatabase db;
    ArrayList<Character> characters;
    ArrayList<DiceSet> DiceSet;
    ArrayList<Dice> dice;
    private Handler handler = new Handler();

    @Inject
    public repository(@ApplicationContext Context context){
        db = Room.databaseBuilder(context, AppDatabase.class, "dataBase").build();
    }


    public void saveNewCharacter(String name) {
        System.out.println("Saving newCharacter " + name);
        new Thread(()->{
            Character newCharacter = new Character();
            newCharacter.name = name;
            newCharacter.characterID = db.getCharacterDao().saveNewCharacter(newCharacter);
            characters.add(newCharacter);
        }).start();

    }

    public void deleteCharacter(Character character) {
        characters.remove(character);
        new Thread(() ->{
            db.getCharacterDao().deleteCharacter(character);
        }).start();
    }

    public String getCharacterName(long characterId) {
        for (Character character :
                characters) {
            if (character.characterID == characterId) {
                return character.name;
            }
        }
        return "Character Doesn't Exist";
    }

    public interface addDiceCallback{
        void call(Dice newDice);
    }

    public void addDice(long diceSetID, addDiceCallback callback) {
        Dice newDice = new Dice();
        newDice.diceSetID = diceSetID;
        System.out.println("DiceSetID is " + diceSetID);
        System.out.println(diceSetID);
        new Thread(() ->{
            newDice.diceID = db.getDiceDao().addDice(newDice);
            System.out.println("Creating new dice " + newDice);
            handler.post(() ->{
                System.out.println("Callback with new dice " + newDice);
                callback.call(newDice);
            });
        }).start();
    }

    public interface newDiceSetCallback {
        void call(DiceSet diceSet);
    }
    public void createNewDiceSet(long characterID, newDiceSetCallback callback) {
        DiceSet diceSet = new DiceSet();
        diceSet.characterID = characterID;
        new Thread(() ->{
            diceSet.diceSetID = db.getDiceSetDao().createNewDiceSet(diceSet);
            System.out.println("Creating new dice set " + diceSet);
            handler.post(() ->{
                callback.call(diceSet);
            });
        }).start();

    }

    public void saveDiceSet(DiceSet updatedDiceSet) {
        System.out.println("Saving dice");
        new Thread(() ->{
            db.getDiceSetDao().updateDiceSet(updatedDiceSet);
        }).start();
    }


    public interface CharacterCallback {
        void call(ArrayList<Character> characters);
    }

    public void getCharacters(CharacterCallback callback){
        if (characters == null){
            new Thread(() -> {
                characters = (ArrayList<Character>) db.getCharacterDao().getCharacters();
                handler.post(() ->{
                    callback.call(characters);
                });
            }).start();
        } else {
            callback.call(characters);
        }
    }

    public interface DiceSetCallback {
        void call(ArrayList<DiceSet> diceSets);
    }

    public void getDiceSets(long characterId, DiceSetCallback callback){
        new Thread(()->{
            DiceSet = (ArrayList<DiceSet>) db.getDiceSetDao().getACharacterDiceSets(characterId);
            handler.post(() ->{
                callback.call(DiceSet);
            });
        }).start();

    }

    public interface DiceCallBack {
        void call(ArrayList<Dice> Dice);
    }
    public void getDice(long diceSetId, DiceCallBack callBack){
        System.out.println("Getting the diceList for " + diceSetId);
        new Thread(() ->{
            dice = (ArrayList<Dice>) db.getDiceDao().getADiceSetDice(diceSetId);
            handler.post(() ->{
                System.out.println("Dice list in repo " + dice.toString());
                callBack.call(dice);
            });
        }).start();
    }

}
