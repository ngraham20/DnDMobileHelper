package com.example.dndmobilehelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class CombatActivity extends AppCompatActivity {

    private static final String TAG = "CombatActivity";

    RecyclerView mRecyclerView;
    CharacterAdapter mAdapter;
    Combat mCombat;
    ArrayList<Character> mCharacters;

    Character mDialogCharacter;
    ArrayList<String> mDialogNames;

    private Character dialogCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        TextView title = (TextView) findViewById(R.id.combat_title);

        int index = (int) getIntent().getSerializableExtra(CombatAdapter.COMBAT_EXTRA);
        mCombat = MasterList.getInstance().getmCombats().get(index);
        mCharacters = mCombat.getCharacters();

        String name = mCombat.getName();
        title.setText(name);

        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new CharacterAdapter(this, mCharacters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CharacterListSwipeToDeleteCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton fab = findViewById(R.id.add_character_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: Add FAB Clicked");
               addCharacterDialog();

            }
        });

        FloatingActionButton deleteFab = findViewById(R.id.delete_character_button);
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: DeleteFAB Clicked");
                Context context = v.getContext();
                beginCharacterDeletionDialog();
            }
        });

    }

    /**
     * Begin the character deletion dialog chain
     */
    private void beginCharacterDeletionDialog()
    {
        dialogCharacter = null;
        deleteCharacterDialog();
    }

    /**
     * Delete character dialog
     */
    private void deleteCharacterDialog()
    {

        mDialogNames = new ArrayList<>();
        for(Character character : mCharacters)
        {
            String name = character.getCharacterName();
            mDialogNames.add(name);
        }

        CharSequence[] input = mDialogNames.toArray(new CharSequence[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(input, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                removeCharacter(i);
            }
        });
        builder.setTitle("Delete Character");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Add Character dialog
     */
    private void addCharacterDialog()
    {
        mDialogNames = new ArrayList<>();

        for(Character character : MasterList.getInstance().getmCharacterTemplates())
        {
            String name = character.getCharacterName();
            mDialogNames.add(name);
        }

        CharSequence[] input = mDialogNames.toArray(new CharSequence[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(input, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialogCharacter = MasterList.getInstance().getmCharacterTemplates().get(i);

                if(mDialogCharacter.getCharacterType().equals(Character.MOB))
                {
                    // TODO get mobs to work
                    selectMobCountDialog();
                }
                else
                {
                    if(!mDialogCharacter.getInCombat()) // if this character is not in a combat
                    {
                        addCharacter(mDialogCharacter);
                    }
                    else if(mCharacters.contains(mDialogCharacter))
                    {
                        badCharacterAddDialog("Characters cannot be added to the same combat more than once.");
                    }
                    else
                    {
                        badCharacterAddDialog("Characters cannot be added to more than one combat.");
                    }
                }
            }
        });
        builder.setTitle("Add Character");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void selectMobCountDialog()
    {
        final NumberPicker input = new NumberPicker(this);
        input.setMaxValue(60);
        input.setMinValue(1);
        input.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(input);
        builder.setTitle("Number of Mobs to Add");
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "onClick: Setting number of Mobs");

                // finish and create mobs
                for(int j = 0; j < input.getValue(); j++) {
                    addCharacter(mDialogCharacter); // will end up making copies
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Dialog to prevent deletion of invalid character
     * @param message the reason for invalid deletion
     */
    private void badCharacterAddDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    private void addCharacter(Character character)
    {
        MasterList.getInstance().addCharacterToCombat(character, mCombat);

        mAdapter.notifyItemInserted(mCharacters.indexOf(character));
        mCharacters = mCombat.getCharacters();
        Collections.sort(mCharacters);
        mAdapter.notifyDataSetChanged();
    }

    private void removeCharacter(int index)
    {
        Character character = mCharacters.get(index);
        MasterList.getInstance().removeCharacterFromCombat(character, mCombat);
        mAdapter.notifyItemRemoved(index);
        mAdapter.notifyItemRangeChanged(index,mCharacters.size());
    }

    @Override
    public void onResume() {
        super.onResume();
        Collections.sort(mCharacters);
        mAdapter.notifyDataSetChanged();
    }

}
