package com.example.dndmobilehelper;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class CharacterListSwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private Drawable icon;
    //private final ColorDrawable background = new ColorDrawable(Color.RED);
    private CharacterAdapter mAdapter;

    public CharacterListSwipeToDeleteCallback(CharacterAdapter mAdapter)
    {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mAdapter = mAdapter;
        icon = ContextCompat.getDrawable(mAdapter.getmContext(), R.drawable.ic_home_black_24dp);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);

    }

//    @Override
//    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        View itemView = viewHolder.itemView;
//        int backgroundCornerOffset = 20;
//
//        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
//        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
//        int iconBottom = iconTop + icon.getIntrinsicHeight();
//
//        if (dX > 0) { // Swiping to the right
//            int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
//            int iconRight = itemView.getLeft() + iconMargin;
//            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
//
////            background.setBounds(itemView.getLeft(), itemView.getTop(),
////                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
////                    itemView.getBottom());
//        } else if (dX < 0) { // Swiping to the left
//            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
//            int iconRight = itemView.getRight() - iconMargin;
//            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
//
////            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
////                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
//        } else { // view is unSwiped
//            //background.setBounds(0, 0, 0, 0);
//        }
//
//        background.draw(c);
//        icon.draw(c);
//    }
}
