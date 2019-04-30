package com.example.dndmobilehelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import org.json.JSONException;

import java.util.ArrayList;

public class CharacterListFragment extends Fragment {

    //private final Context context = this;
    View view;
    private RecyclerView mRecyclerView;
    public static ArrayList<Character> mCharacters;

    private Character dialogCharacter;
    private CharacterAdapter mAdapter;

    private static final String TAG = "CharacterListFragment";

    ArrayList<String> mDialogNames;
    private Character mRecentlyDeletedCharacter;
    private int mRecentlyDeletedCharacterPosition;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_character_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new CharacterAdapter(getContext(), mCharacters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CharacterListSwipeToDeleteCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton createFab = view.findViewById(R.id.add_character_button);
        createFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: CreateFAB Clicked");
                Context context = v.getContext();
                beginCharacterCreationDialog();
            }
        });

//        FloatingActionButton deleteFab = view.findViewById(R.id.delete_character_button);
//        deleteFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.d(TAG, "onClick: DeleteFAB Clicked");
//                Context context = v.getContext();
//                beginCharacterDeletionDialog();
//            }
//        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCharacters = CharacterMasterList.getInstance().getmCharacterTemplates();
        mCharacters = MasterList.getInstance().getmCharacterTemplates();
    }

    /**
     * Initiates the Character creation dialog chain
     */
    private void beginCharacterCreationDialog()
    {
        dialogCharacter = null;
        characterTypeDialog();
    }

    /**
     * Begins the character deletion dialog chain
     */
    private void beginCharacterDeletionDialog()
    {
        dialogCharacter = null;
        deleteCharacterDialog();
    }

    /**
     * The dialog to delete a character
     */
    private void deleteCharacterDialog()
    {

        mDialogNames = new ArrayList<>();
//        for(Character character : CharacterMasterList.getInstance().getmCharacterTemplates())
//        {
//            String name = character.getCharacterName();
//            mDialogNames.add(name);
//        }

        for(Character character : MasterList.getInstance().getmCharacterTemplates())
        {
            String name = character.getCharacterName();
            mDialogNames.add(name);
        }

        CharSequence[] input = mDialogNames.toArray(new CharSequence[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(input, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Character character = MasterList.getInstance().getmCharacterTemplates().get(i);

                if(!character.getInCombat())
                {
                    // TODO this next line should be refactored for async or modified for better code
                    //CharacterMasterList.getInstance().removeCharacter(character);
                    MasterList.getInstance().removeCharacter(character);
                    mAdapter.notifyItemRemoved(i);
                    mAdapter.notifyItemRangeChanged(i,mCharacters.size());
                }
                else
                {
                    badCharacterDeleteDialog("Cannot delete characters currently in combat. Remove them and try again.");
                }


            }
        });
        builder.setTitle("Delete Character");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Calls an alert to prevent character deletion
     * @param message reason the character cannot be deleted
     */
    private void badCharacterDeleteDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    /**
     * Character name link in dialog chain
     */
    private void characterNameDialog()
    {
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input);
        builder.setTitle("Character Name");
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: Setting Character Name");

                        if(dialogCharacter!=null) {
                            dialogCharacter.setCharacterName(input.getText().toString());
                            characterACDialog();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: Cancel");
                        cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Character type in dialog chain
     */
    private void characterTypeDialog()
    {
    final CharSequence[] items = {Character.MONSTER,Character.NPC,Character.PC,Character.MOB};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Character Type");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogCharacter = Character.characterFactory("", items[i].toString(), 0, 1, 0);
                characterNameDialog();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Character AC in dialog chain
     */
    private void characterACDialog()
    {
        final NumberPicker input = new NumberPicker(getContext());
        input.setMaxValue(60);
        input.setMinValue(1);
        input.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input);
        builder.setTitle("Character AC");
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "onClick: Setting Character AC");

                if(dialogCharacter!=null) {
                    dialogCharacter.setArmorClass(input.getValue());
                    characterHPDialog();
                }
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: Cancel");
                        cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Character hp in dialog chain
     */
    private void characterHPDialog()
    {
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input);
        builder.setTitle("Character HP");
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "onClick: Setting Character HP");

                if(dialogCharacter!=null) {
                    String intString = input.getText().toString();
                    int health;
                    if(!intString.equals("")) {
                        health = Integer.parseInt(intString);
                    }
                    else
                    {
                        health = 1;
                    }
                    dialogCharacter.setMaxHealth(health);
                    dialogCharacter.setCurrentHealth(health);
                    characterInitiativeModDialog();
                }
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: Cancel");
                        cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Character initiative modifier in dialog chain
     */
    private void characterInitiativeModDialog()
    {
        final NumberPicker input = new NumberPicker(getContext());
        input.setMaxValue(12);
        input.setMinValue(0);
        input.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input);
        builder.setTitle("Character Initiative Mod");
        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "onClick: Setting Character Initiative Modifier");

                if(dialogCharacter!=null) {
                    dialogCharacter.setInitiativeModifier(input.getValue());
                    // add character to list
                    addCharacter(dialogCharacter);
                    clearDialogCharacter();
                }
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: Cancel");
                        cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * adds a character to the global character list
     * @param character
     */
    public void addCharacter(Character character)
    {
        // copy the character and add it to list
        Character newGuy = Character.copy(character);
        try {
            //CharacterMasterList.getInstance().addCharacter(newGuy);
            MasterList.getInstance().addCharacter(newGuy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // update the ui
        mAdapter.notifyItemInserted(mCharacters.indexOf(newGuy));
    }

    /**
     * cancels character creation
     */
    private void cancel()
    {
        // set all dialog variables to null
        clearDialogCharacter();

    }

    /**
     * null the dialog temp character
     */
    private void clearDialogCharacter()
    {
        dialogCharacter = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    public void deleteCharacter(int position)
    {
        mRecentlyDeletedCharacter = mCharacters.get(position);
        mRecentlyDeletedCharacterPosition = position;
        Character character = MasterList.getInstance().getmCharacterTemplates().get(position);

        // TODO this next line should be refactored for async or modified for better code
        MasterList.getInstance().removeCharacter(character);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(position,mCharacters.size());
    }
}
