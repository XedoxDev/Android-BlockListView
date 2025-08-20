package org.xedox.blocklist.util;

import org.xedox.blocklist.R;

public class BlockItem extends BaseItem {
    private final String name;

    public BlockItem(String name) {
        this.name = name;
        backgroundId = R.drawable.block_bg;
        backgroundColor = 0xFF5BBFE4;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getItemLayout() {
        return R.layout.block_item;
    }
}
