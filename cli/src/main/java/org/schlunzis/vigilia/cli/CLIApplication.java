package org.schlunzis.vigilia.cli;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.commands.RootCommand;

@CustomLog
public class CLIApplication {

    public static void main(String[] args) {
        RootCommand rootCommand = new RootCommand(args);
        rootCommand.execute();
    }

}
