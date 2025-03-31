package org.schlunzis.vigilia.cli.ui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class AbstractAnimation implements Animation {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(false);
        return thread;
    });

    protected final Future<?> runAsync(Runnable runnable) {
        return EXECUTOR.submit(runnable);
    }

}
