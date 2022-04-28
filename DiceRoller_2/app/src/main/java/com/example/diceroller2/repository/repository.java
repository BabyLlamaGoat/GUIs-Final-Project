package com.example.diceroller2.repository;

import android.content.Context;
import android.os.Handler;

import androidx.room.Room;

import com.example.diceroller2.model.AppDatabase;
import com.example.diceroller2.model.Character;
import com.example.diceroller2.model.CharacterWithDiceSets;
import com.example.diceroller2.model.Dice;
import com.example.diceroller2.model.DiceSet;
import com.example.diceroller2.model.DiceSetWithDice;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class repository {

    AppDatabase db;
    ArrayList<Character> characters;
    CharacterWithDiceSets CharacterWithDiceSets;
    DiceSetWithDice diceSetWithDice;
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
        new Thread(() ->{
            diceSet.diceSetID = db.getDiceSetDao().createNewDiceSet(diceSet);
            handler.post(() ->{
                callback.call(diceSet);
            });
        }).start();

    }

    public void saveDiceSet(long diceSetID, String title, String descriptor) {

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
        void call(CharacterWithDiceSets characterWithDiceSets);
    }

    public void getDiceSets(long characterId, DiceSetCallback callback){
        if (CharacterWithDiceSets == null){
            new Thread(()->{
                CharacterWithDiceSets = (CharacterWithDiceSets) db.getDiceSetDao().getCharacterWithDiceSets();
                handler.post(() ->{
                    callback.call(CharacterWithDiceSets);
                });
            }).start();
        }
    }

    public interface DiceCallBack {
        void call(DiceSetWithDice diceSetWithDice);
    }
    public void getDice(long diceSetId, DiceCallBack callBack){
        if (diceSetWithDice == null){
            new Thread(() ->{
                diceSetWithDice = (DiceSetWithDice) db.getDiceDao().getDiceSetWithDice();
                handler.post(() ->{
                    callBack.call(diceSetWithDice);
                });
            }).start();
        }
    }

}
