package com.example.diceroller2.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.example.diceroller2.model.Dice;
import com.example.diceroller2.model.DiceSetWithDice;
import com.example.diceroller2.repository.repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class diceViewModel extends ViewModel {

    repository repository;
    DiceSetWithDice diceSetWithDice;
    ObservableArrayList<Dice> dice = new ObservableArrayList<>();

    @Inject
    public diceViewModel(repository repository){
        this.repository = repository;
    }


    public ObservableArrayList<Dice> getDiceList(long diceSetID) {
        this.dice.clear();
        this.repository.getDice(diceSetID, diceSetWithDice ->{
            this.diceSetWithDice = diceSetWithDice;
            this.dice.addAll(diceSetWithDice.dice);
        });
        return this.dice;
    }

    public void addDice(long diceSetID) {
        repository.addDice(diceSetID, newDice ->{
            dice.add(newDice);
        });

    }
}
