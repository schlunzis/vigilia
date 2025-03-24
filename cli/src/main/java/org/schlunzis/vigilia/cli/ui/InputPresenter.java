package org.schlunzis.vigilia.cli.ui;

import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.schlunzis.vigilia.cli.util.AnsiProvider;
import picocli.CommandLine;

import java.util.Scanner;

@CustomLog
@RequiredArgsConstructor
public class InputPresenter {

    private static final CommandLine.Help.Ansi ansi = AnsiProvider.getAnsi();

    private final String prompt;

    public String present() {
        log.log(prompt);
        System.out.print(ansi.string("@|magenta > |@")); // NOSONAR

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        return input;
    }

}
