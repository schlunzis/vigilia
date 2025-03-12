package org.schlunzis.vigilia.gui.fx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.CheckModel;

// FIXME method implementations are not correct
public class FileCheckModel implements CheckModel<FileTreeItem> {

    private final ObservableList<FileTreeItem> checkedFiles;

    private boolean allChecked = false;

    public FileCheckModel() {
        this.checkedFiles = FXCollections.observableArrayList();
    }

    @Override
    public int getItemCount() {
        System.out.println("getItemCount");
        return checkedFiles.size();
    }

    @Override
    public ObservableList<FileTreeItem> getCheckedItems() {
        System.out.println("getCheckedItems");
        return FXCollections.unmodifiableObservableList(checkedFiles);
    }

    @Override
    public void checkAll() {
        System.out.println("checkAll");
        checkedFiles.clear();
        allChecked = true;
    }

    @Override
    public void clearCheck(FileTreeItem item) {
        System.out.println("clearCheck");
        checkedFiles.remove(item);
        allChecked = false;
    }

    @Override
    public void clearChecks() {
        System.out.println("clearChecks");
        checkedFiles.clear();
        allChecked = false;
    }

    @Override
    public boolean isEmpty() {
        System.out.println("isEmpty");
        return checkedFiles.isEmpty() && !allChecked;
    }

    @Override
    public boolean isChecked(FileTreeItem item) {
        System.out.println("isChecked");
        return checkedFiles.contains(item) || allChecked;
    }

    @Override
    public void check(FileTreeItem item) {
        System.out.println("check");
        checkedFiles.add(item);
    }

    @Override
    public void toggleCheckState(FileTreeItem item) {
        System.out.println("toggleCheckState");
        if (checkedFiles.contains(item)) {
            checkedFiles.remove(item);
            allChecked = false;
        } else {
            checkedFiles.add(item);
        }
    }

}
