package org.schlunzis.vigilia.gui.fx.view;

import org.schlunzis.vigilia.gui.fx.ApiException;
import org.schlunzis.vigilia.gui.fx.api.DefaultApi;
import org.schlunzis.vigilia.gui.fx.model.SupportedMediaTypesDTO;
import org.schlunzis.vigilia.gui.fx.service.ApiProvider;

import java.io.File;
import java.util.List;

public record TreeFile(File file) implements Comparable<TreeFile> {

    private static final String[] SUPPORTED_FILE_TYPES;

    static {
        // TODO: This will default to an empty array if the API call fails. This is not ideal.
        String[] temp;
        DefaultApi api = ApiProvider.getDefaultApi();
        try {
            SupportedMediaTypesDTO supportedMediaTypesDTO = api.getMediaTypes();
            List<String> supportedFileTypes = supportedMediaTypesDTO.getAudio();
            supportedFileTypes.addAll(supportedMediaTypesDTO.getVideo());
            supportedFileTypes.addAll(supportedMediaTypesDTO.getImage());
            supportedFileTypes.addAll(supportedMediaTypesDTO.getDocument());
            temp = supportedFileTypes.toArray(new String[0]);
        } catch (ApiException _) {
            temp = new String[0];
        }
        SUPPORTED_FILE_TYPES = temp;
    }


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
