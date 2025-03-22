package org.schlunzis.vigilia.cli.ui;

import lombok.CustomLog;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@CustomLog
@RequiredArgsConstructor
public abstract class LoopingAnimation extends AbstractAnimation {

    private final String prefix;
    private final String[] frames;
    private final int frameDelay;
    private volatile boolean stopped = false;
    private Future<?> future;

    @Override
    public void start() {
        future = runAsync(() -> {
            int frameIndex = 0;
            while (!stopped) {
                System.out.print("\r" + prefix + frames[frameIndex]); // NOSONAR
                try {
                    //noinspection BusyWait
                    Thread.sleep(frameDelay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                frameIndex = (frameIndex + 1) % frames.length;
            }
            stopped = false;
        });
    }

    @Override
    public void stop() {
        stopped = true;
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            log.log("Error stopping animation", e);
        } finally {
            log.log("\r");
        }
    }

}
