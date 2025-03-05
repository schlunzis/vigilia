package org.schlunzis.vigilia.gui.fx.lib;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Setter;

@Setter
class MainView extends TabPane {

    private Runnable onAddTab;

    public MainView() {
        Tab addTab = new Tab("+");
        addTab.setClosable(false);
        this.getSelectionModel().selectedItemProperty().addListener((_, _, selectedTab) -> {
            if (selectedTab == addTab && onAddTab != null) {
                onAddTab.run();
            }
        });
        this.getTabs().add(addTab);
    }

    public void addTab(Tab tab) {
        this.getTabs().add(this.getTabs().size() - 1, tab);
        this.getSelectionModel().select(this.getTabs().size() - 2);
    }

}
