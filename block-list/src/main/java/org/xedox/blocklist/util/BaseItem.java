package org.xedox.blocklist.util;

public abstract class BaseItem {
    protected int id = 0x0;
    protected int backgroundId;
    protected int backgroundColor;

    public abstract String getName();

    public abstract int getItemLayout();

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean hasBackgroundId() {
        return backgroundId != 0;
    }

    public boolean hasBackgroundColor() {
        return backgroundColor != 0;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        return other.hashCode() == hashCode();
    }
}
