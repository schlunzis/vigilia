package org.schlunzis.vigilia.gui.fx.controller;

import javafx.scene.control.TreeItem;
import org.schlunzis.vigilia.gui.fx.service.IndexTask;
import org.schlunzis.vigilia.gui.fx.view.IndexView;
import org.schlunzis.vigilia.gui.fx.view.TreeFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IndexController {

    private final IndexView view;

    public IndexController(IndexView view) {
        this.view = view;
        view.setOnIndexButtonPressed(this::index);
    }

    public void index() {
        List<TreeItem<TreeFile>> checkedItems = view.getCheckTreeView().getCheckModel().getCheckedItems();
        checkedItems = reduceSize(checkedItems);

        List<String> checkedPaths = checkedItems.stream()
                .map(TreeItem::getValue)
                .map(TreeFile::file)
                .map(File::getAbsolutePath)
                .toList();

        IndexTask task = new IndexTask(checkedPaths);
        view.getTaskProgressView().getTasks().add(task);
        new Thread(task).start();
    }

    /**
     * Reduces the size of the list by removing files and subfolders that are in other folders.
     *
     * @param checkedItems the list of checked items
     * @return the reduced list
     */
    private List<TreeItem<TreeFile>> reduceSize(List<TreeItem<TreeFile>> checkedItems) {
        List<TreeItem<TreeFile>> result = new ArrayList<>();
        for (TreeItem<TreeFile> checkedItem : checkedItems) {
            result.removeIf(item -> item.getValue().isIn(checkedItem.getValue()));
            if (result.stream().noneMatch(item -> checkedItem.getValue().isIn(item.getValue())))
                result.add(checkedItem);
        }
        return result;
    }

}
