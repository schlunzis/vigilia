package org.schlunzis.vigilia.cli.commands;

import picocli.CommandLine;

public class VersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() {
        return new String[]{System.getProperty("vigilia.version")};
    }

}
