package org.schlunzis.vigilia.gui.fx.lib;

import javafx.scene.control.Tab;
import lombok.AccessLevel;
import lombok.Setter;

public class DefaultTabFactory<T extends TabType> implements TabFactory<T> {

    @Setter(AccessLevel.PACKAGE)
    private TabbedStagesManagerImpl<T> tabbedStagesManager;

    @Override
    public Tab createTab(T tabType) {
        String name = tabType == null ? "default" : tabType.getTabName();
        DraggableTab tab = new DraggableTab(name);
        tab.setText(name);
        tab.setOnDraggedOutsideTabPane(point -> tabbedStagesManager.tabDraggedOutsideTabPane(tab, point));
        return tab;
    }

}
