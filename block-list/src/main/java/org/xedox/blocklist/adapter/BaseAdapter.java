package org.xedox.blocklist.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected final List<T> items = new ArrayList<>();

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T get(int position) {
        return items.get(position);
    }

    public void add(T item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void add(int position, T item) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void addAll(Collection<? extends T> collection) {
        int startPosition = items.size();
        items.addAll(collection);
        notifyItemRangeInserted(startPosition, collection.size());
    }

    public void addAll(int position, Collection<? extends T> collection) {
        items.addAll(position, collection);
        notifyItemRangeInserted(position, collection.size());
    }

    public void replaceAll(Collection<? extends T> collection) {
        items.clear();
        items.addAll(collection);
        notifyDataSetChanged();
    }

    public void update(int position, T item) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void remove(T item) {
        int position = items.indexOf(item);
        if (position != -1) {
            remove(position);
        }
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void removeRange(int startPosition, int count) {
        for (int i = startPosition + count - 1; i >= startPosition; i--) {
            items.remove(i);
        }
        notifyItemRangeRemoved(startPosition, count);
    }

    public void clear() {
        int itemCount = items.size();
        items.clear();
        notifyItemRangeRemoved(0, itemCount);
    }

    public List<T> getAllItems() {
        return new ArrayList<>(items);
    }

    public int indexOf(T item) {
        return items.indexOf(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public void move(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    @Override
    public abstract VH onCreateViewHolder(ViewGroup container, int viewType);
}
