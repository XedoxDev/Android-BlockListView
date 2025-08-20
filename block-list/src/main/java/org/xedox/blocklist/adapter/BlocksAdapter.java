package org.xedox.blocklist.adapter;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.Collections;
import java.util.List;
import org.xedox.blocklist.R;
import org.xedox.blocklist.util.BaseItem;
import org.xedox.blocklist.util.BlockItem;
import org.xedox.blocklist.util.EventItem;
import org.xedox.blocklist.util.ItemTouchHelperAdapter;
import org.xedox.blocklist.util.OverflowMenu;

public class BlocksAdapter extends BaseAdapter<BaseItem, BlocksAdapter.BaseViewHolder>
        implements ItemTouchHelperAdapter {

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return get(position).getItemLayout();
    }

    @Override
    public void remove(int position) {
        BaseItem item = get(position);
        if (item instanceof BlockItem) {
            super.remove(position);
        } else {
            EventItem event = (EventItem) item;
            if (event.isOpen()) {
                removeRange(position, event.blockCount());
            } else {
                super.remove(position);
            }
        }
    }

    public void toggleEvent(EventItem event) {
        int position = indexOf(event);
        if (event.isOpen()) {
            for (int i = event.blockCount() - 1; i >= 0; --i) {
                super.remove(position + i + 1);
            }
            event.setIsOpen(false);
        } else {
            addAll(position + 1, event.getBlocks());
            event.setIsOpen(true);
        }
    }

    public void setEvents(List<EventItem> events) {
        clear();
        for (EventItem event : events) {
            items.add(event);
            if (event.isOpen()) {
                items.addAll(event.getBlocks());
            }
        }
        notifyDataSetChanged();
    }

    public EventItem findEventByChild(BlockItem block) {
        return findEventByChild(indexOf(block));
    }

    public EventItem findEventByChild(int position) {
        BaseItem current = null;
        for (int i = position; i >= 0; --i) {
            current = get(i);
            if (current instanceof EventItem) return (EventItem) current;
        }
        return null;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        BaseItem from = get(fromPosition);
        BaseItem to = get(toPosition);

        if (from instanceof BlockItem && toPosition == 0) {
            return false;
        }

        if (from instanceof BlockItem) {
            EventItem fromEvent = findEventByChild(fromPosition);
            EventItem toEvent = findEventByChild(toPosition);

            // moving inside event
            if (indexOf(fromEvent) == indexOf(toEvent)) {
                int fromIndexInEvent = fromPosition - indexOf(fromEvent);
                int toIndexInEvent = toPosition - indexOf(toEvent);

                List<BlockItem> blocks = fromEvent.getBlocks();
                if (toIndexInEvent < fromEvent.blockCount()) {
                    Collections.swap(blocks, fromIndexInEvent, toIndexInEvent);
                    move(fromPosition, toPosition);
                    return true;
                }
            }
            // move to other event
            BlockItem block = (BlockItem) from;
            fromEvent.removeBlock(block);
            if (indexOf(fromEvent) < indexOf(toEvent)) toEvent.addBlock(0, block);
            else toEvent.addBlock(block);
            move(fromPosition, toPosition);
            return true;
        }

        move(fromPosition, toPosition);
        return true;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }

        public void bind(BaseItem item) {
            name.setText(item.getName());
            Drawable bg = itemView.getContext().getDrawable(item.getBackgroundId());
            bg.setColorFilter(
                    new BlendModeColorFilter(item.getBackgroundColor(), BlendMode.DST_IN));
            itemView.setBackground(bg);
            itemView.setOnClickListener(
                    v -> {
                        int menu =
                                item instanceof EventItem
                                        ? R.menu.event_actions
                                        : R.menu.block_actions;
                        OverflowMenu.create(itemView, menu, (mItem) -> handleMenu(item, mItem))
                                .show();
                    });
        }
    }

    private void handleMenu(BaseItem item, MenuItem mItem) {
        int id = mItem.getItemId();
        if (id == R.id.remove) {
            remove(item);
        }
        if (item instanceof EventItem) {
            EventItem event = (EventItem) item;
            if (id == R.id.toggle) {
                toggleEvent(event);
            }
        }
    }

    private EventItem draggingEvent = null;
    private boolean beenOpen = false;

    @Override
    public void onItemMoveStarted(int position) {
        if (get(position) instanceof EventItem) {
            draggingEvent = (EventItem) get(position);
            if (draggingEvent.isOpen()) {
                toggleEvent(draggingEvent);
                beenOpen = true;
            }
        }
    }

    @Override
    public void onClearView(RecyclerView recycler, RecyclerView.ViewHolder holder) {
        if (draggingEvent != null && beenOpen) {
            toggleEvent(draggingEvent);
            beenOpen = false;
            draggingEvent = null;
        }
    }

    @Override
    public void onSwiped(ViewHolder viewHolder, int dir) {
        if(dir == ItemTouchHelper.LEFT) {
            remove(viewHolder.getAbsoluteAdapterPosition());
        }
    }
}
