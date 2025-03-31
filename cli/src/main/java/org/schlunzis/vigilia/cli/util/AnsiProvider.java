package org.schlunzis.vigilia.cli.util;

import lombok.experimental.UtilityClass;
import picocli.CommandLine;

@UtilityClass
public class AnsiProvider {

    public static CommandLine.Help.Ansi getAnsi() {
        return CommandLine.Help.Ansi.AUTO;
    }

}
