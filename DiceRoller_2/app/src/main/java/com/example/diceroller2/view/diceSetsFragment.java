package com.example.diceroller2.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diceroller2.R;
import com.example.diceroller2.viewmodel.characterViewModel;
import com.example.diceroller2.viewmodel.diceSetViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class diceSetsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dice_sets, container, false);

        Bundle bundle = getArguments();
        long characterId = (long) bundle.get("characterID");
        System.out.println(characterId);

        diceSetViewModel diceSetViewModel = new ViewModelProvider(this).get(diceSetViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.diceSetRecyclerView);
        recyclerView.setAdapter(new diceSetsAdapter(diceSetViewModel.getDiceSets(characterId),
                (rollDiceSet) ->{
                    System.out.println("rolling dice set");
                }, (editDiceSet) ->{
            System.out.println("Edit the dice");

        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        view.findViewById(R.id.newDiceSet).setOnClickListener(button ->{
            System.out.println("Adding a new DiceSet");
            long diceSetID = diceSetViewModel.createNewDiceSet(characterId);
            System.out.println("The new dice set id in diceSetsFragment is " + diceSetID);
            bundle.putLong("diceSetID", diceSetID);

            NavHostFragment.findNavController(this).navigate(R.id.action_diceSetFragment_to_newDiceSetsFragment,bundle);
        });


        characterViewModel characterViewModel = new ViewModelProvider(this).get(characterViewModel.class);
        String characterName = characterViewModel.getCharacterName(characterId);

        TextView characterNameView = view.findViewById(R.id.characterDiceSetName);
        characterNameView.setText(characterName);
        characterNameView.setTextSize(30);
        characterNameView.setTextColor(Color.BLACK);

        return view;
    }
}
