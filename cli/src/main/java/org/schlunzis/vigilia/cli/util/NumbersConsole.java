package org.schlunzis.vigilia.cli.util;

import lombok.CustomLog;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

// https://darkcoding.net/software/non-blocking-console-io-is-not-possible/
// implement for windows with ffm: https://learn.microsoft.com/en-us/windows/console/setconsolemode
@CustomLog
@UtilityClass
public class NumbersConsole {

    private static String ttyConfig;

    public static void setTerminalToCBreak() throws IOException, InterruptedException {

        ttyConfig = stty("-g");

        // set the console to be character-buffered instead of line-buffered
        stty("-icanon min 1");

        // disable character echoing
        stty("-echo");
    }

    public static void reset() {
        try {
            stty(ttyConfig.trim());
        } catch (IOException e) {
            log.log("Exception restoring tty config");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.log("Interrupted while restoring tty config");
        }
    }

    /**
     * Execute the stty command with the specified arguments
     * against the current active terminal.
     */
    private static String stty(final String args)
            throws IOException, InterruptedException {
        String cmd = "stty " + args + " < /dev/tty";

        return exec(new String[]{
                "sh",
                "-c",
                cmd
        });
    }

    /**
     * Execute the specified command and return the output
     * (both stdout and stderr).
     */
    private static String exec(final String[] cmd)
            throws IOException, InterruptedException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        Process p = Runtime.getRuntime().exec(cmd);
        int c;
        InputStream in = p.getInputStream();

        while ((c = in.read()) != -1) {
            bout.write(c);
        }

        in = p.getErrorStream();

        while ((c = in.read()) != -1) {
            bout.write(c);
        }

        p.waitFor();

        return bout.toString();
    }

}
