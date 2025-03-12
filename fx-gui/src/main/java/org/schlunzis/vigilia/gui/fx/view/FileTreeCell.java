package org.schlunzis.vigilia.gui.fx.view;

import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.File;

public class FileTreeCell extends CheckBoxTreeCell<File> {

    @Override
    public void updateItem(File file, boolean empty) {
        super.updateItem(file, empty);
        if (empty || file == null) {
            setText(null);
        } else {
            setText(file.getName());
        }
    }

}
