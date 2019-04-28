package com.example.dndmobilehelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

public abstract class DnDRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "DnDRecyclerAdapter";
    public static final String RECYCLER_EXTRA = "DnDMobileHelper.RecyclerIndex";

    private ArrayList<DnDScrollable> mItems;
    private Context mContext;

    public DnDRecyclerAdapter(Context context, ArrayList<DnDScrollable> mItems)
    {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder dnDViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
