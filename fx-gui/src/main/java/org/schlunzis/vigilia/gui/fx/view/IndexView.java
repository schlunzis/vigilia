package org.schlunzis.vigilia.gui.fx.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.TaskProgressView;
import org.schlunzis.vigilia.gui.fx.service.IndexTask;

public class IndexView extends VBox {

    @Getter
    private final TaskProgressView<IndexTask> taskProgressView;

    @Setter
    private Runnable onIndexButtonPressed = null;

    public IndexView() {
        Button button = new Button("Index files...");
        button.setOnAction(_ -> {
            if (onIndexButtonPressed != null) {
                onIndexButtonPressed.run();
            }
        });
        this.getChildren().add(button);

        this.taskProgressView = new TaskProgressView<>();
        taskProgressView.setRetainTasks(true);
        this.getChildren().add(taskProgressView);
    }

}
