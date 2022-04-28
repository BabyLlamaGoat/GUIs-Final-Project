package com.example.diceroller2.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.example.diceroller2.model.CharacterWithDiceSets;
import com.example.diceroller2.model.DiceSet;
import com.example.diceroller2.repository.repository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class diceSetViewModel extends ViewModel {

    repository repository;
    CharacterWithDiceSets characterWithDiceSets;
    ObservableArrayList<DiceSet> diceSets = new ObservableArrayList<>();
    long newestDiceSetID;
    DiceSet newestDiceSet;

    @Inject
    public diceSetViewModel(repository repository){
        this.repository = repository;
    }


    public ObservableArrayList<DiceSet> getDiceSets(long characterId) {
        this.diceSets.clear();
        this.repository.getDiceSets(characterId, DiceSets ->{
            diceSetViewModel.this.diceSets.addAll(DiceSets);
        });
        return this.diceSets;

    }


    public long createNewDiceSet(long characterID) {
        repository.createNewDiceSet(characterID,(newDiceSet) ->{
            diceSets.add(newDiceSet);
            diceSetViewModel.this.newestDiceSetID = newDiceSet.diceSetID;
            this.newestDiceSet = newDiceSet;
        });
        return newestDiceSetID;
    }

    public void saveDiceSet(long diceSetID, String title, String descriptor) {
//        Should create the dice and then save it again.
    }
}
