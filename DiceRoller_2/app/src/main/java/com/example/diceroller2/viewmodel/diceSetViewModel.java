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

    public void saveDiceSet(long diceSetID, String title, String descriptor) {
        System.err.printf("DiceSetID = %d\nTitle = %s\nDescriptor = %s\n", diceSetID, title, descriptor);
        for (DiceSet diceSet :
                diceSets) {
            if (diceSet.diceSetID == diceSetID){
                diceSet.name = title;
                diceSet.descriptor = descriptor;
                repository.saveDiceSet(diceSet);
            }
        }
    }

    public void deleteDiceSet(DiceSet deletedSet) {
        System.out.println("Before removing DiceSet " + diceSets.toString());
        this.diceSets.remove(deletedSet);
        System.out.println("After remove DiceSet " + diceSets.toString());
        this.repository.deleteDiceSet(deletedSet);
    }
}
