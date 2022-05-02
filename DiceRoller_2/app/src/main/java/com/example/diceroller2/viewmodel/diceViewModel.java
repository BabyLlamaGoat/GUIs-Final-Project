package com.example.diceroller2.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.example.diceroller2.model.Dice;
import com.example.diceroller2.repository.repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class diceViewModel extends ViewModel {

    repository repository;
    ObservableArrayList<Dice> dice = new ObservableArrayList<>();

    @Inject
    public diceViewModel(repository repository){
        this.repository = repository;
    }


    public ObservableArrayList<Dice> getDiceList(long diceSetID) {
        dice.clear();
        repository.getDice(diceSetID, Dice ->{
            System.out.println("DiceList in callBack is " + Dice.toString());
            diceViewModel.this.dice.addAll(Dice);
        });
        System.out.println("DiceList in ViewModel is " + dice.toString());
        return this.dice;
    }

    public void addDice(long diceSetID) {
        System.out.println("View model DiceSetID before repo is " + diceSetID);
        repository.addDice(diceSetID, newDice ->{
            System.out.println("View model adding new dice in callback " + newDice);
            dice.add(newDice);
            System.out.println(dice.toString());
        });

    }
}
