package org.schlunzis.vigilia.cli.commands;

import org.schlunzis.vigilia.cli.CLIApplication;
import picocli.CommandLine;

public class VersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() {
        return new String[]{CLIApplication.class.getPackage().getImplementationVersion()};
    }

}
