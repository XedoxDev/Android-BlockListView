package org.xedox.blocklist.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import org.xedox.blocklist.R;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.xedox.blocklist.adapter.BlocksAdapter;
import org.xedox.blocklist.util.BaseItem;
import org.xedox.blocklist.util.BlockItem;
import org.xedox.blocklist.util.EventItem;
import org.xedox.blocklist.util.ItemTouchHelperAdapter;

public class BlockListView extends RecyclerView {

    private BlocksAdapter adapter;
    private ItemTouchHelper.Callback itemTouchCallback;
    private ItemTouchHelper touchHelper;

    public BlockListView(Context context) {
        this(context, null);
    }

    public BlockListView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public BlockListView(Context context, AttributeSet attr, int defstyle) {
        super(context, attr, defstyle);
        initialize(context, attr);
    }

    private void initialize(Context context, AttributeSet attr) {
        setLayoutManager(new LinearLayoutManager(context));
        adapter = new BlocksAdapter();
        setAdapter(adapter);

        itemTouchCallback = new BlockTouchCallBack(adapter);
        touchHelper = new ItemTouchHelper(itemTouchCallback);
        touchHelper.attachToRecyclerView(this);
    }

    public void setEvents(List<EventItem> events) {
        adapter.setEvents(events);
    }

    public List<BaseItem> getItems() {
        return adapter.getAllItems();
    }

    public void toggleEvent(EventItem event) {
        adapter.toggleEvent(event);
    }

    public void remove(BaseItem item) {
        int position = adapter.indexOf(item);
        if (position != -1) {
            adapter.remove(position);
        }
    }

    public void remove(int position) {
        adapter.remove(position);
    }

    public void addEvent(EventItem event) {
        adapter.add(event);
    }

    public void addBlockToEvent(EventItem event, BlockItem block) {
        event.addBlock(block);
        if (event.isOpen()) {
            int eventPosition = adapter.indexOf(event);
            if (eventPosition != -1) {
                adapter.add(eventPosition + event.blockCount(), block);
            }
        }
    }

    public EventItem findEventByChild(BlockItem block) {
        return adapter.findEventByChild(block);
    }

    public EventItem findEventByChild(int position) {
        return adapter.findEventByChild(position);
    }

    public void clear() {
        adapter.clear();
    }

    public int getItemCount() {
        return adapter.getItemCount();
    }

    public BaseItem getItem(int position) {
        return adapter.get(position);
    }

    public int indexOf(BaseItem item) {
        return adapter.indexOf(item);
    }

    public class BlockTouchCallBack extends ItemTouchHelper.Callback {
        private final ItemTouchHelperAdapter mAdapter;

        public BlockTouchCallBack(ItemTouchHelperAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.LEFT;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean onMove(
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                RecyclerView.ViewHolder target) {
            mAdapter.onItemMove(
                    viewHolder.getAbsoluteAdapterPosition(), target.getAbsoluteAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.onSwiped(viewHolder, direction);
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);

            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
                mAdapter.onItemMoveStarted(viewHolder.getAbsoluteAdapterPosition());

                viewHolder.itemView.setAlpha(0.7f);
            }
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);

            mAdapter.onClearView(recyclerView, viewHolder);

            viewHolder.itemView.setAlpha(1.0f);
        }
    }
}
