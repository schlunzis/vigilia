package org.schlunzis.vigilia.cli.ui;

import lombok.CustomLog;
import org.schlunzis.vigilia.cli.util.AnsiProvider;
import org.schlunzis.vigilia.cli.util.NumbersConsole;
import picocli.CommandLine;

import java.io.IOException;
import java.io.InputStream;

@CustomLog
public class OptionPresenter {

    private static final CommandLine.Help.Ansi ansi = AnsiProvider.getAnsi();

    private final String prompt;
    private final String[] options;

    public OptionPresenter(String prompt, String... options) {
        this.prompt = prompt;
        this.options = options;
    }

    public int present() {
        log.log(prompt);

        try {
            NumbersConsole.setTerminalToCBreak();
        } catch (IOException e) {
            log.log("Error setting terminal to cbreak: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.log("Interrupted while setting terminal to cbreak");
        }

        try {
            drawOptions(0);
            try {
                int selected = presentInternal();
                removeOptions();
                drawOption(selected, true);
                return selected;
            } catch (IOException e) {
                log.log("Error reading input: " + e.getMessage());
                return -1;
            }
        } finally {
            try {
                NumbersConsole.reset();
            } catch (Exception e) {
                log.log("Exception restoring tty config");
            }
        }
    }

    private int presentInternal() throws IOException {
        InputStream in = System.in;

        int choice = 0;
        while (true) {
            char input = (char) in.read();
            if (input == '\n')
                return choice;

            if (input != 0x1B // ESC
                    || in.read() != '[')
                continue;

            input = (char) in.read();

            switch (input) {
                case 'A': // UP
                    choice = Math.max(0, choice - 1);
                    redrawOptions(choice);
                    break;
                case 'B': // DOWN
                    choice = Math.min(options.length - 1, choice + 1);
                    redrawOptions(choice);
                    break;
                default:
            }
        }
    }

    private void redrawOptions(int choice) {
        removeOptions();
        drawOptions(choice);
    }

    private void removeOptions() {
        System.out.print("\033[K\033[A".repeat(options.length)); // NOSONAR
    }

    private void drawOptions(int choice) {
        for (int i = 0; i < options.length; i++) {
            drawOption(i, i == choice);
        }
    }

    private void drawOption(int index, boolean selected) {
        if (selected) {
            System.out.println(ansi.string("@|magenta > " + options[index] + "|@")); // NOSONAR
        } else {
            System.out.println("  " + options[index]); // NOSONAR
        }
    }

}
