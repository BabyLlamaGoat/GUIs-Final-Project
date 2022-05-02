package com.example.diceroller2.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.example.diceroller2.model.DiceSet;
import com.example.diceroller2.repository.repository;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class diceSetViewModel extends ViewModel {

    repository repository;
    ObservableArrayList<DiceSet> diceSets = new ObservableArrayList<>();

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


    public void createNewDiceSet(long characterID) {
        repository.createNewDiceSet(characterID,(newDiceSet) ->{
            diceSets.add(newDiceSet);
        });
    }

    public void saveDiceSet(long diceSetID, long characterID, String title, String descriptor) {
        DiceSet editedDiceSet = new DiceSet();
        editedDiceSet.diceSetID = diceSetID;
        editedDiceSet.characterID = characterID;
        editedDiceSet.name = title;
        editedDiceSet.descriptor = descriptor;
        repository.saveDiceSet(editedDiceSet);
    }

    public void deleteDiceSet(DiceSet deletedSet) {
        this.diceSets.remove(deletedSet);
        this.repository.deleteDiceSet(deletedSet);
    }
}
