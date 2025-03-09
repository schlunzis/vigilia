package org.schlunzis.vigilia.gui.fx.lib;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import lombok.Setter;

@Setter
class MainView extends TabPane {

    private Runnable onAddTab;

    public MainView() {
        this.setTabDragPolicy(TabDragPolicy.REORDER);
        this.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
        addAddTabButton();
    }

    public void addTab(Tab tab) {
        this.getTabs().add(this.getTabs().size() - 1, tab);
        this.getSelectionModel().select(this.getTabs().size() - 2);
    }

    private void addAddTabButton() {
        Tab addTab = new Tab("+");
        addTab.setClosable(false);
        this.getSelectionModel().selectedItemProperty().addListener((_, _, selectedTab) -> {
            if (getTabs().size() > 1 && selectedTab == addTab && onAddTab != null) {
                onAddTab.run();
            }
        });
        this.getTabs().add(addTab);
    }

    public boolean isHeaderIntersects(double x, double y) {
        double headerHeight = ((StackPane) getSkin().getNode().lookup(".tab-header-area")).getHeight();
        double screenX = localToScreen(0, 0).getX();
        double screenY = localToScreen(0, 0).getY();
        Rectangle2D tabHeader = new Rectangle2D(screenX, screenY, getWidth(), headerHeight);
        return tabHeader.intersects(x, y, 1, 1);
    }

}
