package org.schlunzis.vigilia.gui.fx.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Setter
public class SearchView extends Pane {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private Runnable onSettingsPressed;

    public SearchView() {
        this.getChildren().add(new Label("TabView " + counter.incrementAndGet()));
        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(_ -> {
            if (onSettingsPressed != null) {
                onSettingsPressed.run();
            }
        });
        this.getChildren().add(settingsButton);
    }

}
