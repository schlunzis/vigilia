package org.schlunzis.vigilia.gui.fx.view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.concurrent.atomic.AtomicInteger;

public class SearchView extends Pane {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public SearchView() {
        this.getChildren().add(new Label("TabView " + counter.incrementAndGet()));
    }

}
