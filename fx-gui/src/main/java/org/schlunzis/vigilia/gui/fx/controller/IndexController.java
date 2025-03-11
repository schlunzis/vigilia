package org.schlunzis.vigilia.gui.fx.controller;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.schlunzis.vigilia.gui.fx.service.IndexTask;
import org.schlunzis.vigilia.gui.fx.view.IndexView;

import java.io.File;
import java.util.Collections;

public class IndexController {

    private final IndexView view;
    private final FileChooser fileChooser = new FileChooser();

    public IndexController(IndexView view) {
        this.view = view;

        String home = System.getProperty("user.home");
        if (home != null)
            fileChooser.setInitialDirectory(new File(home));
        fileChooser.setTitle("Select files to index");
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("All files", "*.*")
        );

        view.setOnIndexButtonPressed(this::index);
    }

    public void index() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(view.getScene().getWindow());
        if (file != null) {
            IndexTask task = new IndexTask(Collections.singletonList(file.getAbsolutePath()));
            view.getTaskProgressView().getTasks().add(task);
            new Thread(task).start();
        }
    }


}
