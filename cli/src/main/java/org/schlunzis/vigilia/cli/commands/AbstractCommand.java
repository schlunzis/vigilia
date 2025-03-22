package org.schlunzis.vigilia.cli.commands;

import java.util.Optional;

public abstract class AbstractCommand implements Command {

    private static final String[] HELP_OPTIONS = new String[]{"h", "help", "-h", "--help"};

    protected String[] args;

    protected AbstractCommand(String[] args) {
        this.args = args;
    }

    @Override
    public void execute() {
        if (isHelp()) {
            printHelp();
            System.exit(0);
        }
    }

    protected boolean isHelp() {
        if (args == null || args.length == 0)
            return false;

        String firstArg = args[0];
        for (String helpOption : HELP_OPTIONS) {
            if (helpOption.equals(firstArg))
                return true;
        }
        return false;
    }

    protected Optional<String> popFirstArg() {
        if (args == null || args.length == 0)
            return Optional.empty();
        String firstArg = args[0];
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);
        this.args = newArgs;
        return Optional.of(firstArg);
    }

}
