package org.schlunzis.vigilia.gui.fx.lib;

import javafx.scene.control.Tab;

public class DefaultTabFactory<T extends TabType> implements TabFactory<T> {

    @Override
    public Tab createTab(T tabType) {
        Tab tab = new Tab();
        String name = tabType == null ? "default" : tabType.getTabName();
        tab.setText(name);
        return tab;
    }

}
