package org.xedox.blocklist.util;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onSwiped(RecyclerView.ViewHolder viewHolder, int dir);
    void onItemMoveStarted(int position); 
    void onClearView(RecyclerView recycler, RecyclerView.ViewHolder holder);
}