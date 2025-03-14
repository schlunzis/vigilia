package org.schlunzis.vigilia.gui.fx.view;

import java.io.File;

public record TreeFile(File file) implements Comparable<TreeFile> {

    // TODO store this at a central place like a common module or request this from the core
    private static final String[] SUPPORTED_FILE_TYPES = new String[]{".md"};

    public String getName() {
        return file.getName();
    }

    public boolean isFile() {
        return file.isFile();
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }

    public TreeFile[] listFiles() {
        File[] files = file.listFiles();
        if (files == null) {
            return new TreeFile[0];
        }
        TreeFile[] treeFiles = new TreeFile[files.length];
        for (int i = 0; i < files.length; i++) {
            treeFiles[i] = new TreeFile(files[i]);
        }
        return treeFiles;
    }

    public boolean isHidden() {
        return file.isHidden() || file.getName().startsWith(".");
    }

    @Override
    public int compareTo(TreeFile o) {
        return this.isDirectory() == o.isDirectory() ? this.file.compareTo(o.file) : Boolean.compare(this.isFile(), o.isFile());
    }

    public boolean isSupportedFileType() {
        for (String supportedFileType : SUPPORTED_FILE_TYPES) {
            if (file.getName().endsWith(supportedFileType)) {
                return true;
            }
        }
        return false;
    }

    public boolean isIn(TreeFile parent) {
        return file.getAbsolutePath().startsWith(parent.file.getAbsolutePath());
    }

}
