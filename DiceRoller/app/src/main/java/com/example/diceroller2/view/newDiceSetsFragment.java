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
import com.example.diceroller2.model.DiceSet;
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

        diceViewModel diceVM = new ViewModelProvider(this).get(diceViewModel.class);
        diceSetViewModel diceSetVM = new ViewModelProvider(this).get(diceSetViewModel.class);

        view.findViewById(R.id.newDiceButton).setOnClickListener(button ->{
            System.out.println(diceSetID);
            diceVM.addDice(diceSetID);
        });


        EditText setTitle = view.findViewById(R.id.setTitleInput);
        setTitle.setText((String) bundle.get("name"));
        EditText setDescriptor = view.findViewById(R.id.setDescriptorInput);
        setDescriptor.setText((String) bundle.get("descriptor"));

        view.findViewById(R.id.saveDiceSetButton).setOnClickListener(button ->{

            diceSetVM.saveDiceSet(
                    diceSetID,
                    characterID,
                    setTitle.getText().toString(),
                    setDescriptor.getText().toString());
            NavHostFragment.findNavController(this).popBackStack();
        });

        RecyclerView recyclerView = view.findViewById(R.id.diceRecyclerView);
        recyclerView.setAdapter(new newDiceSetAdapter(diceVM.getDiceList(diceSetID), diceVM,
                deletedDie ->{
            diceVM.deleteDie(deletedDie);
                }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;

    }

}
