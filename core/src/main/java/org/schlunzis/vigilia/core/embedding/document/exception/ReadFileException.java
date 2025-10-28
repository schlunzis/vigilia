package org.schlunzis.vigilia.core.embedding.document.exception;

public class ReadFileException extends RuntimeException {

    public ReadFileException(String message) {
        super(message);
    }

    public ReadFileException(String message, Throwable cause) {
        super(message, cause);
    }

}
