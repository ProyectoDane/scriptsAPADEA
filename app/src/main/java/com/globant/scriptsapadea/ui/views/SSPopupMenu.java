package com.globant.scriptsapadea.ui.views;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.globant.scriptsapadea.R;

/**
 * @author nicolas.quartieri
 */
public class SSPopupMenu extends PopupMenu implements PopupMenu.OnMenuItemClickListener {

    private static final int EDIT = 1;
    private static final int REMOVE = 2;
    private static final int COPY = 3;

    public SSPopupMenu(Context context, View anchor) {
        super(context, anchor);

        getMenu().add(Menu.NONE, EDIT, Menu.NONE, R.string.popupmenu_edit);
        getMenu().add(Menu.NONE, REMOVE, Menu.NONE, R.string.popupmenu_remove);
        getMenu().add(Menu.NONE, COPY, Menu.NONE, R.string.popupmenu_copy);

        setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO Finish actions menu item
        switch (item.getItemId()) {
            case EDIT:
                break;
            case REMOVE:
                break;
            case COPY:
                break;
        }

        return false;
    }
}
