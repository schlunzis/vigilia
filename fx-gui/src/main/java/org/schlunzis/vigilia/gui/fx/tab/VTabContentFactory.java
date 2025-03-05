package org.schlunzis.vigilia.gui.fx.tab;

import javafx.scene.Node;
import org.schlunzis.vigilia.gui.fx.controller.SearchController;
import org.schlunzis.vigilia.gui.fx.controller.SettingsController;
import org.schlunzis.vigilia.gui.fx.lib.TabContentFactory;
import org.schlunzis.vigilia.gui.fx.lib.TabbedStagesManager;
import org.schlunzis.vigilia.gui.fx.view.SearchView;
import org.schlunzis.vigilia.gui.fx.view.SettingsView;

public class VTabContentFactory implements TabContentFactory<Type> {

    private final TabbedStagesManager<Type> tabbedStagesManager;
    private final SettingsView settingsView = new SettingsView();

    public VTabContentFactory(TabbedStagesManager<Type> tabbedStagesManager) {
        this.tabbedStagesManager = tabbedStagesManager;
        new SettingsController(settingsView);
    }

    @Override
    public Node create(Type tabType) {
        return switch (tabType) {
            case SEARCH -> {
                SearchView searchView = new SearchView();
                new SearchController(searchView, tabbedStagesManager);
                yield searchView;
            }
            case SETTINGS -> settingsView;
        };
    }

}
