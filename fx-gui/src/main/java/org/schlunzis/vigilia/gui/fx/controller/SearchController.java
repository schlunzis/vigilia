package org.schlunzis.vigilia.gui.fx.controller;

import lombok.Getter;
import org.schlunzis.vigilia.gui.fx.service.SearchService;
import org.schlunzis.vigilia.gui.fx.view.SearchView;

public class SearchController {

    private static final SearchService searchService = SearchService.getInstance();

    @Getter
    private final SearchView view;

    public SearchController(SearchView view) {
        this.view = view;
    }

}
