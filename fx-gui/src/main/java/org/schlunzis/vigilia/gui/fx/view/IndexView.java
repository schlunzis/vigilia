package org.schlunzis.vigilia.gui.fx.view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.CheckTreeView;
import org.controlsfx.control.TaskProgressView;
import org.schlunzis.vigilia.gui.fx.service.IndexTask;

import java.io.File;

public class IndexView extends HBox {

    @Getter
    private final TaskProgressView<IndexTask> taskProgressView;
    @Getter
    private final CheckTreeView<TreeFile> checkTreeView;

    @Setter
    private Runnable onIndexButtonPressed = null;

    public IndexView() {
        VBox vBox = new VBox();
        this.getChildren().add(vBox);

        // TODO customize the root directory; also consider different drives on windows
        FileTreeItem root = new FileTreeItem(new TreeFile(new File(System.getProperty("user.home"))));
        checkTreeView = new CheckTreeView<>(root);
        VBox.setVgrow(checkTreeView, Priority.ALWAYS);
        checkTreeView.setCellFactory(_ -> new FileTreeCell());

        Button indexButton = new Button("Index");
        indexButton.setOnAction(_ -> {
            if (onIndexButtonPressed != null) {
                onIndexButtonPressed.run();
            }
        });
        Button unselectAllButton = new Button("Unselect all");
        unselectAllButton.setOnAction(_ -> checkTreeView.getCheckModel().clearChecks());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(indexButton, unselectAllButton);
        vBox.getChildren().addAll(checkTreeView, hBox);

        this.taskProgressView = new TaskProgressView<>();
        taskProgressView.setRetainTasks(true);
        this.getChildren().add(taskProgressView);
    }

}
