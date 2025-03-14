package org.schlunzis.vigilia.gui.fx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

import java.util.Comparator;

public class FileTreeItem extends CheckBoxTreeItem<TreeFile> {

    private boolean isFile;
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;

    public FileTreeItem(TreeFile file) {
        super(file);
    }

    @Override
    public ObservableList<TreeItem<TreeFile>> getChildren() {
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
            TreeFile f = getValue();
            isFile = f.isFile();
        }
        return isFile;
    }


    private ObservableList<TreeItem<TreeFile>> buildChildren() {
        TreeFile f = this.getValue();
        if (f != null && f.isDirectory()) {
            TreeFile[] files = f.listFiles();
            ObservableList<TreeItem<TreeFile>> children = FXCollections.observableArrayList();
            for (TreeFile childFile : files) {
                // TODO this ignores hidden files and files that are not supported to make the update of the view faster; this is not done in the core so there are different expectations of what will be indexed
                if (childFile.isHidden() || childFile.isFile() && !childFile.isSupportedFileType())
                    continue;
                children.add(new FileTreeItem(childFile));
            }
            children.sort(Comparator.comparing(TreeItem::getValue));
            return children;
        }
        return FXCollections.emptyObservableList();
    }

}
