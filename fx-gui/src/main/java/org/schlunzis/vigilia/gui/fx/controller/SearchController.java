package org.schlunzis.vigilia.gui.fx.controller;

import lombok.Getter;
import org.schlunzis.vigilia.gui.fx.lib.TabbedStagesManager;
import org.schlunzis.vigilia.gui.fx.service.SearchService;
import org.schlunzis.vigilia.gui.fx.tab.Type;
import org.schlunzis.vigilia.gui.fx.view.SearchView;

public class SearchController {

    private static final SearchService searchService = SearchService.getInstance();

    private final TabbedStagesManager<Type> tabbedStagesManager;

    @Getter
    private final SearchView view;

    public SearchController(SearchView view, TabbedStagesManager<Type> tabbedStagesManager) {
        this.view = view;
        this.tabbedStagesManager = tabbedStagesManager;
        view.setOnSettingsPressed(this::onOpenSettingsButtonPressed);
        view.setOnIndexPressed(this::onOpenIndexButtonPressed);
    }

    public void onOpenSettingsButtonPressed() {
        tabbedStagesManager.addTab(Type.SETTINGS);
    }

    public void onOpenIndexButtonPressed() {
        tabbedStagesManager.addTab(Type.INDEX);
    }

}
