package com.example.diceroller2.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.example.diceroller2.model.Character;
import com.example.diceroller2.repository.repository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class characterViewModel extends ViewModel {

    repository repository;
    ObservableArrayList<Character> characters = new ObservableArrayList<>();

    @Inject
    public characterViewModel(repository repository){
        this.repository = repository;
    }

    public void saveNewCharacter(String name) {
        this.repository.saveNewCharacter(name);

    }

    public ObservableArrayList<Character> getCharacters() {
        this.characters.clear();
        this.repository.getCharacters(characters -> {
            this.characters.addAll(characters);
        });
        return this.characters;
    }

    public void deleteCharacter(Character character) {
        this.characters.remove(character);
        this.repository.deleteCharacter(character);
    }

    public String getCharacterName(long characterId) {
        return repository.getCharacterName(characterId);
    }
}
