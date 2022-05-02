package com.example.diceroller2.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diceroller2.R;
import com.example.diceroller2.model.Dice;
import com.example.diceroller2.model.DiceSet;

import java.util.ArrayList;

public class diceSetsAdapter extends RecyclerView.Adapter<diceSetsAdapter.diceSetsViewHolder> {
    public interface OnRollClick{
        void onClick(DiceSet diceSet);
    }

    public interface OnEditClick{
        void onClick(DiceSet diceSet);
    }

    public interface onDeleteSet{
        void onClick(DiceSet diceSet);
    }

    private ObservableArrayList<DiceSet> diceSets;
    OnRollClick rollClickListener;
    OnEditClick editClickListener;
    onDeleteSet deleteClickListener;

    public diceSetsAdapter(ObservableArrayList<DiceSet> diceSets,
                           OnRollClick rollClickListener,
                           OnEditClick editClickListener,
                           onDeleteSet deleteClickListener){
        this.diceSets = diceSets;
        this.rollClickListener = rollClickListener;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
        this.diceSets.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<DiceSet>>() {
            @Override
            public void onChanged(ObservableList<DiceSet> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<DiceSet> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<DiceSet> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<DiceSet> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<DiceSet> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @NonNull
    @Override
    public diceSetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_view, parent,false);
        return new diceSetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull diceSetsViewHolder holder, int position) {
        DiceSet diceSet = diceSets.get(position);
        TextView setTitle = holder.itemView.findViewById(R.id.setTitle);
        setTitle.setText(diceSet.name);

        TextView setDescriptor = holder.itemView.findViewById(R.id.setDescriptor);
        setDescriptor.setText(diceSet.descriptor);

        Button rollButton = holder.itemView.findViewById(R.id.rollSetButton);
        rollButton.setOnClickListener(roll ->{
            System.out.println("rolled dice set");
            rollClickListener.onClick(diceSet);
        });

        Button editButton = holder.itemView.findViewById(R.id.editSetButton);
        editButton.setOnClickListener(edit ->{
            System.out.println("editing dice set");
            editClickListener.onClick(diceSet);
        });

        Button deleteButton = holder.itemView.findViewById(R.id.deleteDiceSet);
        deleteButton.setOnClickListener(delete ->{
            deleteClickListener.onClick(diceSet);
        });


    }

    @Override
    public int getItemCount() {
        return diceSets.size();
    }

    public class diceSetsViewHolder extends RecyclerView.ViewHolder{
        public diceSetsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
