package com.example.dndmobilehelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// learned much of this from https://www.youtube.com/watch?v=Vyqz_-sJGFk

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    public final static String CHARACTER_EXTRA = "DnDCombatTracker.characterIndex";
    private static final String TAG = "CharacterAdapter";

    private ArrayList<Character> mCharacters;
    private Context mContext;

    private Character mRecentlyDeletedCharacter;
    private int mRecentlyDeletedCharacterPosition;

    /**
     * Constructor
     * @param context the context passed in
     * @param mCharacters the characters to put in the recyclerview
     */
    public CharacterAdapter(Context context, ArrayList<Character> mCharacters) {
        this.mCharacters = mCharacters;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_list_item, parent, false);

        CharacterViewHolder holder = new CharacterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder characterViewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        characterViewHolder.character_name.setText(mCharacters.get(position).getCharacterName());
        characterViewHolder.character_ac.setText(Integer.toString(mCharacters.get(position).getArmorClass()));
        characterViewHolder.character_init.setText(Integer.toString(mCharacters.get(position).getCurrentInitiative()));

        characterViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Character character = mCharacters.get(position);
                Log.d(TAG, "onClick: clicked on: " + character.getCharacterName());

                Toast.makeText(mContext,character.getCharacterName(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, CharacterActivity.class);
                //intent.putExtra(CHARACTER_EXTRA, CharacterMasterList.getInstance().getmCharacterTemplates().indexOf(character));
                intent.putExtra(CHARACTER_EXTRA, MasterList.getInstance().getmAllCharacters().indexOf(character));

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }

    /**
     * Subclass for the ViewHolder
     */
    public class CharacterViewHolder extends RecyclerView.ViewHolder
    {

        TextView character_ac;
        TextView character_name;
        TextView character_init;
        ConstraintLayout parentLayout;

        /**
         * Constructor
         * @param itemView the view to apply
         */
        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            character_ac = itemView.findViewById(R.id.character_ac);
            character_name = itemView.findViewById(R.id.character_name);
            character_init = itemView.findViewById(R.id.character_init);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public Context getmContext() {
        return mContext;
    }

    public void deleteItem(int position)
    {




        Character character = mCharacters.get(position);

        if(!character.getInCombat())
        {
            mRecentlyDeletedCharacter = character;
            mRecentlyDeletedCharacterPosition = position;
            // TODO this next line should be refactored for async or modified for better code
            //CharacterMasterList.getInstance().removeCharacter(character);
            mCharacters.remove(character);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,mCharacters.size());

            showUndoSnakbar();
        }
        else
        {
            badCharacterDeleteDialog("Cannot delete characters currently in combat. Remove them and try again.");
            notifyDataSetChanged();
        }
    }

    private void showUndoSnakbar()
    {
        Activity activity = (Activity) getmContext();
        View view = activity.findViewById(R.id.parent_layout);
        Snackbar snackbar = Snackbar.make(view, "Character deleted.", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete()
    {
            mCharacters.add(mRecentlyDeletedCharacter);
            notifyItemInserted(mCharacters.indexOf(mRecentlyDeletedCharacter));
    }

    private void badCharacterDeleteDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Warning");
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "onClick: Bad Character Dialog");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
