package org.xedox.blocklist.util;

import java.util.ArrayList;
import java.util.List;
import org.xedox.blocklist.R;

public class EventItem extends BaseItem {

    private final String name;
    private boolean isOpen;
    private List<BlockItem> blocks;

    public EventItem(String name) {
        this.name = name;
        this.isOpen = true;
        this.blocks = new ArrayList<>();
        backgroundId = R.drawable.event_bg;
        backgroundColor = 0xFFFB9441;
    }

    public void addBlock(BlockItem block) {
        if (block != null) {
            blocks.add(block);
        }
    }
    
    public void addBlock(int position, BlockItem block) {
        if (block != null) {
            blocks.add(position, block);
        }
    }

    public void removeBlock(BlockItem block) {
        blocks.remove(block);
    }

    public void removeBlock(int position) {
        blocks.remove(position);
    }

    public BlockItem getBlock(int position) {
        return blocks.get(position);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public List<BlockItem> getBlocks() {
        return blocks;
    }

    public void clearBlocks() {
        blocks.clear();
    }

    public int blockCount() {
        return blocks.size();
    }

    public int getItemLayout() {
        return R.layout.event_item;
    }

}
