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
//            TODO finish the rolling portion
                }, (editDiceSet) ->{
            bundle.putLong("diceSetID", editDiceSet.diceSetID);
            bundle.putString("name", editDiceSet.name);
            bundle.putString("descriptor", editDiceSet.descriptor);
            System.out.println("Edit the dice");
            NavHostFragment.findNavController(this).navigate(R.id.action_diceSetFragment_to_newDiceSetsFragment,bundle);
            }, (deletedSet) ->{
            diceSetViewModel.deleteDiceSet(deletedSet);
            }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        view.findViewById(R.id.newDiceSet).setOnClickListener(button ->{
            diceSetViewModel.createNewDiceSet(characterId);

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
