package org.schlunzis.vigilia.cli.util;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class PathUtil {

    public static String convertToAbsolutePath(String path) {
        return Path.of(path).toAbsolutePath().toString();
    }

}
