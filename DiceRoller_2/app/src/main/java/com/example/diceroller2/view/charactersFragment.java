package com.example.diceroller2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diceroller2.R;
import com.example.diceroller2.viewmodel.characterViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class charactersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_characters, container, false);
        characterViewModel characterViewModel = new ViewModelProvider(this).get(characterViewModel.class);

        view.findViewById(R.id.newCharacterButton).setOnClickListener(button ->{
            NavHostFragment.findNavController(this).navigate(R.id.action_charactersFragment_to_newCharacterFragment);
        });


        RecyclerView recyclerView = view.findViewById(R.id.character_reycler_view);
        recyclerView.setAdapter(new charactersAdapter(characterViewModel.getCharacters(), (character) ->{
            Bundle bundle = new Bundle();
            bundle.putLong("characterID" ,character.characterID);
            NavHostFragment.findNavController(this).navigate(R.id.action_charactersFragment_to_diceSetFragment,bundle);

        }, (character -> {
            characterViewModel.deleteCharacter(character);
        })));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;

    }

}
