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
    long newestDiceSetID;
    DiceSet newestDiceSet;

    @Inject
    public diceSetViewModel(repository repository){
        this.repository = repository;
    }


    public ObservableArrayList<DiceSet> getDiceSets(long characterId) {
        this.diceSets.clear();
        this.repository.getDiceSets(characterId, DiceSets ->{
            System.out.println("DiceSet in Callbark are " + DiceSets);
            diceSetViewModel.this.diceSets.addAll(DiceSets);
        });
        System.out.println("DiceSets in ViewModel are  " + diceSets);
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
}
