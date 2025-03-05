package org.schlunzis.vigilia.gui.fx.tab;

import javafx.scene.Node;
import javafx.scene.control.Label;
import org.schlunzis.vigilia.gui.fx.controller.SearchController;
import org.schlunzis.vigilia.gui.fx.lib.TabContentFactory;
import org.schlunzis.vigilia.gui.fx.view.SearchView;

public class VTabContentFactory implements TabContentFactory<Type> {

    @Override
    public Node create(Type tabType) {
        return switch (tabType) {
            case SEARCH -> {
                SearchView searchView = new SearchView();
                new SearchController(searchView);
                yield searchView;
            }
            case SETTINGS -> new Label("Settings");
        };
    }

}
