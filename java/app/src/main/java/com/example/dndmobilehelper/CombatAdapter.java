package com.example.dndmobilehelper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// learned much of this from https://www.youtube.com/watch?v=Vyqz_-sJGFk

public class CombatAdapter extends RecyclerView.Adapter<CombatAdapter.CombatViewHolder> {

    private static final String TAG = "combatAdapter";
    public static final String COMBAT_EXTRA = "DnDCombatTracker.combatIndex";

    private ArrayList<Combat> mcombats;
    private Context mContext;

    /**
     * Constructor
     * @param context the context to use
     * @param mcombats the combas to add to the recyclerview
     */
    public CombatAdapter(Context context, ArrayList<Combat> mcombats) {
        this.mcombats = mcombats;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CombatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.combat_list_item, parent, false);

        return new CombatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CombatViewHolder combatViewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        combatViewHolder.combat_name.setText(mcombats.get(position).getName());
        combatViewHolder.combat_character_count.setText(Integer.toString(mcombats.get(position).getCharacters().size()));

        combatViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mcombats.get(position).getName());

                Combat combat = mcombats.get(position);

                Toast.makeText(mContext, combat.getName(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, CombatActivity.class);
                intent.putExtra(CombatAdapter.COMBAT_EXTRA, position);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mcombats.size();
    }

    /**
     * Subclass to extend ViewHolder
     */
    public class CombatViewHolder extends RecyclerView.ViewHolder
    {
        TextView combat_name;
        TextView combat_character_count;
        ConstraintLayout parentLayout;

        /**
         * Constructor
         * @param itemView the view to apply
         */
        public CombatViewHolder(@NonNull View itemView) {
            super(itemView);
            combat_name = itemView.findViewById(R.id.combat_name);
            combat_character_count = itemView.findViewById(R.id.combat_character_count);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
