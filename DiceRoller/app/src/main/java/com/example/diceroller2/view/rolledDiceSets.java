package com.example.diceroller2.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diceroller2.R;
import com.example.diceroller2.viewmodel.diceSetViewModel;
import com.example.diceroller2.viewmodel.diceViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class rolledDiceSets extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rolled_dice_sets, container, false);

        Bundle bundle = getArguments();

        TextView setTitle = view.findViewById(R.id.rolledSetTitle);
        setTitle.setText(bundle.getString("name"));

        diceViewModel diceVM = new ViewModelProvider(this).get(diceViewModel.class);

        TextView results = view.findViewById(R.id.rolledResult);

        long diceSetID = (long) bundle.getLong("diceSetID");

        diceVM.rollDice(diceSetID).observe(this, (rolls) ->{
            results.setText(rolls);
        });



        return view;
    }
}