package org.schlunzis.vigilia.gui.fx.lib;

import javafx.scene.control.Tab;
import lombok.Getter;

class MainController<T extends TabType> {

    @Getter
    private final MainView view;

    private final TabbedStagesManagerImpl<T> tabbedStagesManager;

    MainController(TabbedStagesManagerImpl<T> tabbedStagesManager, MainView view) {
        this.tabbedStagesManager = tabbedStagesManager;
        this.view = view;
        view.setOnAddTab(this::onAddTabPressed);
    }

    void addTab(Tab tab) {
        view.addTab(tab);
    }

    private void onAddTabPressed() {
        tabbedStagesManager.addTab(tabbedStagesManager.getDefaultTabType());
    }

}
