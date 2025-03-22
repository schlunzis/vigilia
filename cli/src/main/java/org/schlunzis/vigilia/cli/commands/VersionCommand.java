package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.CLIApplication;

@CustomLog
public class VersionCommand extends AbstractCommand {

    protected VersionCommand(String[] args) {
        super(args);
    }

    @Override
    public void execute() {
        String version = CLIApplication.class.getPackage().getImplementationVersion();
        log.log(version);
        System.exit(0);
    }

    @Override
    public void printHelp() {
        // not needed
    }

}
