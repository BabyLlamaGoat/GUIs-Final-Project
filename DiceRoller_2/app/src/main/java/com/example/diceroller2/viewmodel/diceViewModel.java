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
            diceViewModel.this.dice.addAll(Dice);
        });
        return this.dice;
    }

    public void addDice(long diceSetID) {
        repository.addDice(diceSetID, newDice ->{
            dice.add(newDice);
            System.out.println(dice.toString());
        });

    }

    public void deleteDie(Dice deletedDie) {
        dice.remove(deletedDie);
        repository.deleteDie(deletedDie);
    }

    public void updateDie(Dice die) {
        repository.updateDieDiceNumber(die);
    }
}
