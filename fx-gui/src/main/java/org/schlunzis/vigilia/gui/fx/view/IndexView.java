package org.schlunzis.vigilia.gui.fx.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.CheckTreeView;
import org.controlsfx.control.TaskProgressView;
import org.schlunzis.vigilia.gui.fx.service.IndexTask;

import java.io.File;

public class IndexView extends VBox {

    @Getter
    private final TaskProgressView<IndexTask> taskProgressView;
    private final CheckTreeView<File> checkTreeView;

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

        FileTreeItem root = new FileTreeItem(new File(System.getProperty("user.home")));
        this.checkTreeView = new CheckTreeView<>(root);

        this.checkTreeView.setCheckModel(new FileCheckModel());
        this.checkTreeView.setCellFactory(_ -> new FileTreeCell());
        this.getChildren().add(checkTreeView);
    }

}
