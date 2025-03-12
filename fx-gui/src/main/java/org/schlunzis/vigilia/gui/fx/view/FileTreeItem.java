package org.schlunzis.vigilia.gui.fx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

import java.io.File;

public class FileTreeItem extends CheckBoxTreeItem<File> {

    private boolean isFile;
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;

    public FileTreeItem(File file) {
        super(file);
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren());
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            File f = getValue();
            isFile = f.isFile();
        }
        return isFile;
    }


    private ObservableList<TreeItem<File>> buildChildren() {
        File f = this.getValue();
        if (f != null && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();
                for (File childFile : files) {
                    children.add(new FileTreeItem(childFile));
                }
                return children;
            }
        }
        return FXCollections.emptyObservableList();
    }

}
