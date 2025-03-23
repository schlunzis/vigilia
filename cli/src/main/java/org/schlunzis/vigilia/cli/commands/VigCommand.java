package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;
import picocli.CommandLine;

@CustomLog
@CommandLine.Command(
        name = "vig",
        mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        description = "CLI for the vigilia ecosystem",
        subcommands = {
                IndexCommand.class,
                QueryCommand.class
        }
)
public class VigCommand implements Runnable {

    @Override
    public void run() {
        log.log("running stuff");
    }

}
