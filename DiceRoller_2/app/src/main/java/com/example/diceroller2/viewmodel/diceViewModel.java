package com.example.diceroller2.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.diceroller2.model.Dice;
import com.example.diceroller2.repository.repository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class diceViewModel extends ViewModel {

    repository repository;
    ObservableArrayList<Dice> dice = new ObservableArrayList<>();
    MutableLiveData<String> result = new MutableLiveData<>();

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

    public MutableLiveData<String> rollDice(long diceSetID) {
        StringBuilder sb = new StringBuilder();
        repository.getDice(diceSetID, dice ->{
            for (Dice die :
                    dice) {
                sb.append(die.roll());
            }
            result.postValue(sb.toString());
        });
        return result;
    }
}
