package org.schlunzis.vigilia.core.io;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum SupportedFile {

    MARKDOWN(MediaType.DOCUMENT, "md", "markdown");

    private final MediaType mediaType;
    private final String[] fileExtensions;

    SupportedFile(MediaType mediaType, String... fileExtensions) {
        this.mediaType = mediaType;
        this.fileExtensions = fileExtensions;
    }

    public static Optional<SupportedFile> getSupportedFile(String extension) {
        for (SupportedFile supportedFile : SupportedFile.values()) {
            for (String fileExtension : supportedFile.fileExtensions) {
                if (fileExtension.equals(extension)) {
                    return Optional.of(supportedFile);
                }
            }
        }
        return Optional.empty();
    }
}
