package org.schlunzis.vigilia.cli.ui;

import lombok.CustomLog;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Semaphore;

@CustomLog
@RequiredArgsConstructor
public abstract class LoopingAnimation extends AbstractAnimation {

    private final String prefix;
    private final String[] frames;
    private final int frameDelay;
    private final Semaphore semaphore = new Semaphore(0);
    private volatile boolean stopped = false;
    private Thread thread;

    @Override
    public void start() {
        thread = Thread.ofVirtual().start(() -> {
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
            semaphore.release();
        });
    }

    @Override
    public void stop() {
        if (stopped || thread == null)
            return;
        stopped = true;
        thread.interrupt();
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.print("\r"); // NOSONAR
        }
        thread = null;
    }

}
