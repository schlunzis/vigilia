package org.schlunzis.vigilia.cli.commands;

import lombok.CustomLog;

import java.util.Optional;

@CustomLog
public class RootCommand extends AbstractCommand {

    public RootCommand(String[] args) {
        super(args);
    }

    @Override
    public void execute() {
        super.execute();

        Optional<String> command = popFirstArg();
        if (command.isEmpty()) {
            printHelp();
            System.exit(0);
        }

        switch (command.get()) {
            case "version", "v", "--version", "-v":
                new VersionCommand(args).execute();
                break;
            case "index", "i", "--index", "-i":
                new IndexCommand(args).execute();
                break;
            case "query", "q", "--query", "-q":
                new QueryCommand(args).execute();
                break;
            default:
                log.log("Unknown command: " + command.get());
                printHelp();
        }
    }

    @Override
    public void printHelp() {
        log.log("Usage: vig <command>");
        log.log("Commands:");
        log.log("  help: print this message");
        log.log("  index <paths>: index a directory or file");
        log.log("  query <query>: query the indexed files");
        log.log("  version: print the version of the CLI");
    }

}
