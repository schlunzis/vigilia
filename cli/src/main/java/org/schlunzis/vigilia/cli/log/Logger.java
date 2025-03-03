package org.schlunzis.vigilia.cli.log;

import java.text.MessageFormat;

public class Logger {

    public void log(String message) {
        System.out.println(message); // NOSONAR
    }

    public void log(Object o) {
        System.out.println(o); // NOSONAR
    }

    public void log(String message, Object... args) {
        System.out.println(MessageFormat.format(message, args)); // NOSONAR
    }

    public void log(String message, Throwable t) {
        System.out.println(message); // NOSONAR
        //noinspection CallToPrintStackTrace
        t.printStackTrace();
    }

}
