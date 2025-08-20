package org.xedox.blocklist.util;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.PopupMenu;

public final class OverflowMenu {

    public static PopupMenu create(View view, int menuId, OnItemClickListener oicl) {
        Context context = view.getContext();
        PopupMenu menu = new PopupMenu(context, view);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(menuId, menu.getMenu());

        menu.setOnMenuItemClickListener(item -> {
            if (oicl != null) {
                oicl.onItemClick(item);
                return true;
            }
            return false;
        });

        try {
            java.lang.reflect.Field field = menu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            Object menuPopupHelper = field.get(menu);
            menuPopupHelper.getClass()
                .getDeclaredMethod("setForceShowIcon", boolean.class)
                .invoke(menuPopupHelper, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menu;
    }

    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }
}