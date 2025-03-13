package org.schlunzis.vigilia.gui.fx.view;

import javafx.scene.control.cell.CheckBoxTreeCell;

public class FileTreeCell extends CheckBoxTreeCell<TreeFile> {

    @Override
    public void updateItem(TreeFile file, boolean empty) {
        super.updateItem(file, empty);
        if (empty || file == null) {
            setText(null);
        } else {
            setText(file.getName());
        }
    }

}
