package com.example.diceroller2.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diceroller2.R;
import com.example.diceroller2.model.Character;

import java.util.ArrayList;

public class charactersAdapter extends RecyclerView.Adapter<charactersAdapter.characterViewHolder> {
    public interface OnCharacterClick{
        void onClick(Character character);
    }

    public interface deleteCharacter{
        void onDelete(Character character);
    }

    public ArrayList<Character> characters;
    OnCharacterClick onCharacterClick;
    deleteCharacter onDeleteClick;



    public charactersAdapter(ArrayList<Character> characters,
                             OnCharacterClick characterClick,
                             deleteCharacter deleteCharacterClick) {
        this.characters = characters;
        this.onCharacterClick = characterClick;
        this.onDeleteClick = deleteCharacterClick;
    }

    @NonNull
    @Override
    public characterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_view, parent, false);
        return new characterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull characterViewHolder holder, int position) {
        Character character = characters.get(position);
        TextView nameView = holder.itemView.findViewById(R.id.character_name);
        nameView.setText(character.name);
        ImageView imageView = holder.itemView.findViewById(R.id.character_image);
        imageView.setImageResource(R.drawable.ic_launcher_foreground);

        imageView.setOnClickListener(button ->{
            onCharacterClick.onClick(character);
        });

        Button deleteButton = holder.itemView.findViewById(R.id.delete_Character_Button);
        deleteButton.setOnClickListener(button ->{
            onDeleteClick.onDelete(character);
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public class characterViewHolder extends RecyclerView.ViewHolder{
        public characterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
