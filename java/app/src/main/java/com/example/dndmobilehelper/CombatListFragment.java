package com.example.dndmobilehelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONException;

import java.util.ArrayList;

public class CombatListFragment extends Fragment {

    View view;
    private RecyclerView mRecyclerView;
    private ArrayList<Combat> mCombats = new ArrayList<>();
    private CombatAdapter mAdapter;

    private Combat mDialogCombat;
    private ArrayList<String> mDialogNames;

    private static final String TAG = "CombatListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_combat_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new CombatAdapter(getContext(), mCombats);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton fab = view.findViewById(R.id.add_combat_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginCombatDialog();
            }
        });

        FloatingActionButton deleteFab = view.findViewById(R.id.delete_combat_button);
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCombatDialog();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       mCombats = MasterList.getInstance().getmCombats();
    }

    /**
     * nulls the temp combat
     */
    private void clearCombatDialog()
    {
        mDialogCombat = null;
    }

    /**
     * Cancels the dialog chain
     */
    private void cancel()
    {
        clearCombatDialog();
    }

    /**
     * Begins the Combat creation dialog chain
     */
    private void beginCombatDialog()
    {
        clearCombatDialog();
        combatNameDialog();
    }

    /**
     * Combat Name link in dialog chain
     */
    private void combatNameDialog()
    {
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input);
        builder.setTitle("Combat Title");
        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "onClick: Setting Combat Name");

                    mDialogCombat = new Combat(input.getText().toString());
                    addCombat(mDialogCombat);
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
     * Adds a combat to the Master List
     * @param combat the combat to add
     */
    private void addCombat(Combat combat)
    {
        Combat newboi = new Combat(combat);

        try {
            MasterList.getInstance().addCombat(newboi);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter.notifyItemInserted(mCombats.indexOf(newboi));

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Delete combat dialog
     */
    private void deleteCombatDialog()
    {
        mDialogNames = new ArrayList<>();
        for(Combat combat : mCombats)
        {
            String name = combat.getName();
            mDialogNames.add(name);
        }

        CharSequence[] input = mDialogNames.toArray(new CharSequence[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(input, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Combat combat = MasterList.getInstance().getmCombats().get(i);

                for(Character character: combat.getCharacters())
                {
                    character.setInCombat(false, MasterList.NULL_COMBAT);
                }

                MasterList.getInstance().removeCombat(combat);

                mAdapter.notifyItemRemoved(i);
                mAdapter.notifyItemRangeChanged(i,mCombats.size());
            }
        });
        builder.setTitle("Delete Character");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
