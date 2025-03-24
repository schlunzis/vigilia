package org.schlunzis.vigilia.cli;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.commands.VigCommand;
import picocli.CommandLine;

@CustomLog
public class CLIApplication {

    public static void main(String[] args) {
        CommandLine.Help.ColorScheme colorScheme = new CommandLine.Help.ColorScheme.Builder()
                .commands(CommandLine.Help.Ansi.Style.bold, CommandLine.Help.Ansi.Style.underline)
                .options(CommandLine.Help.Ansi.Style.fg_magenta)
                .parameters(CommandLine.Help.Ansi.Style.fg_magenta)
                .optionParams(CommandLine.Help.Ansi.Style.italic)
                .errors(CommandLine.Help.Ansi.Style.fg_red, CommandLine.Help.Ansi.Style.bold)
                .stackTraces(CommandLine.Help.Ansi.Style.italic)
                .applySystemProperties() // optional: allow end users to customize
                .ansi(CommandLine.Help.Ansi.ON)
                .build();
        CommandLine cli = new CommandLine(new VigCommand());
        cli.setColorScheme(colorScheme);
        int exitCode = cli.execute(args);
        System.exit(exitCode);
    }

}
