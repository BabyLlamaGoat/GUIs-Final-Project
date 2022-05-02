package com.example.diceroller2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.diceroller2.R;
import com.example.diceroller2.viewmodel.characterViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class newCharacterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_new_character, container, false);
        characterViewModel viewModel = new ViewModelProvider(this).get(characterViewModel.class);

        EditText newCharacterNameInput = view.findViewById(R.id.newCharacterName);

        view.findViewById(R.id.saveCharacterButton).setOnClickListener(saveButton ->{
            String name = newCharacterNameInput.getText().toString();
            viewModel.saveNewCharacter(name);
            NavHostFragment.findNavController(this).popBackStack();
        });



        return view;

    }
}
