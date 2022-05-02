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

    public void deleteDie(Dice deletedDie) {
        dice.remove(deletedDie);
        new Thread(() ->{
            db.getDiceDao().deleteDie(deletedDie);
        }).start();
    }

    public void deleteDiceSet(DiceSet deletedSet) {
        DiceSet.remove(deletedSet);
        new Thread(() ->{
            db.getDiceSetDao().deleteDiceSet(deletedSet);
        }).start();
    }

    public void updateDieDiceNumber(Dice die) {
        new Thread(() ->{
            db.getDiceDao().updateDie(die);
        }).start();
    }

    public interface addDiceCallback{
        void call(Dice newDice);
    }

    public void addDice(long diceSetID, addDiceCallback callback) {
        Dice newDice = new Dice();
        newDice.diceSetID = diceSetID;
        System.out.println(diceSetID);
        new Thread(() ->{
            newDice.diceID = db.getDiceDao().addDice(newDice);
            handler.post(() ->{
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
        diceSet.name = "New Dice Set";
        diceSet.descriptor = "Edit the Dice Set to add Dice";
        new Thread(() ->{
            diceSet.diceSetID = db.getDiceSetDao().createNewDiceSet(diceSet);
            handler.post(() ->{
                callback.call(diceSet);
            });
        }).start();

    }

    public void saveDiceSet(DiceSet updatedDiceSet) {
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
        new Thread(() ->{
            dice = (ArrayList<Dice>) db.getDiceDao().getADiceSetDice(diceSetId);
            handler.post(() ->{
                callBack.call(dice);
            });
        }).start();
    }

}
