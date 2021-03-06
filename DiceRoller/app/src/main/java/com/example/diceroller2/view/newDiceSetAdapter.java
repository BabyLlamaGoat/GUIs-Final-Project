package com.example.diceroller2.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diceroller2.R;
import com.example.diceroller2.model.Dice;
import com.example.diceroller2.viewmodel.diceSetViewModel;
import com.example.diceroller2.viewmodel.diceViewModel;

public class newDiceSetAdapter extends RecyclerView.Adapter<newDiceSetAdapter.newDiceSetViewHolder> {

    public interface onDeleteDice{
        void onDelete(Dice dice);
    }

    ObservableArrayList<Dice> dice;
    diceViewModel viewModel;
    onDeleteDice onDeleteClick;

    public newDiceSetAdapter(ObservableArrayList<Dice> newDiceList,
                             diceViewModel diceViewModel,
                             onDeleteDice onDeleteDice) {
        this.dice = newDiceList;
        this.viewModel = diceViewModel;
        this.onDeleteClick = onDeleteDice;
        dice.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Dice>>() {
            @Override
            public void onChanged(ObservableList<Dice> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Dice> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Dice> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Dice> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Dice> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @NonNull
    @Override
    public newDiceSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_dice_view,parent,false);
        return new newDiceSetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newDiceSetViewHolder holder, int position) {
        Dice die = dice.get(position);

        holder.itemView.findViewById(R.id.removeDiceButton).setOnClickListener(button ->{
            onDeleteClick.onDelete(die);
        });

        EditText numberOfSides = holder.itemView.findViewById(R.id.numberOfSidesInput);
        EditText numberOfDice = holder.itemView.findViewById(R.id.numberOfDiceInput);


        numberOfSides.removeTextChangedListener(holder.textSidesWatcher);
        numberOfDice.removeTextChangedListener(holder.textDiceWatcher);
        holder.textDiceWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    die.count = Integer.parseInt(editable.toString());
                    viewModel.updateDie(die);
                } catch (Exception ignored){}
            }
        };

        holder.textSidesWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    die.sides = Integer.parseInt(editable.toString());
                    viewModel.updateDie(die);
                } catch (Exception ignored){}
            }
        };

        numberOfDice.addTextChangedListener(holder.textDiceWatcher);
        numberOfSides.addTextChangedListener(holder.textSidesWatcher);

        numberOfDice.setText(String.valueOf(die.count));
        numberOfSides.setText(String.valueOf(die.sides));
    }


    @Override
    public int getItemCount() {
        return dice.size();
    }

    public class newDiceSetViewHolder extends RecyclerView.ViewHolder{
        public newDiceSetViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public TextWatcher textSidesWatcher;
        public TextWatcher textDiceWatcher;
    }
}
