package com.example.diceroller2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diceroller2.R;
import com.example.diceroller2.viewmodel.diceSetViewModel;
import com.example.diceroller2.viewmodel.diceViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class newDiceSetsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_dice_set, container, false);
        Bundle bundle = getArguments();
        long characterID = (long) bundle.get("characterID");
        long diceSetID = (long) bundle.get("diceSetID");
        System.out.println("DiceSetID in newDiceSetsFragment is " + diceSetID);


        diceViewModel diceViewModel = new ViewModelProvider(this).get(diceViewModel.class);
        diceSetViewModel diceSetViewModel = new ViewModelProvider(this).get(diceSetViewModel.class);

        view.findViewById(R.id.newDiceButton).setOnClickListener(button ->{
            System.out.println("Adding new Dice");
            System.out.println(diceSetID);
            diceViewModel.addDice(diceSetID);
        });

        EditText setTitle = view.findViewById(R.id.setTitleInput);
        EditText setDescriptor = view.findViewById(R.id.setDescriptorInput);

        view.findViewById(R.id.saveDiceSetButton).setOnClickListener(button ->{
            diceSetViewModel.saveDiceSet(diceSetID, setTitle.getText().toString(), setDescriptor.getText().toString());
            NavHostFragment.findNavController(this).popBackStack();
        });

        RecyclerView recyclerView = view.findViewById(R.id.diceRecyclerView);
        recyclerView.setAdapter(new newDiceSetAdapter(diceViewModel.getDiceList(diceSetID), diceViewModel));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;

    }

}
