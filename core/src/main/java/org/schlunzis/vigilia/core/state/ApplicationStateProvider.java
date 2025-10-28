package org.schlunzis.vigilia.core.state;

import org.springframework.stereotype.Component;

@Component
public class ApplicationStateProvider {

    private final Object lock = new Object();

    private ApplicationState applicationState = new ApplicationState(IndexingState.IDLE);

    public void setApplicationState(ApplicationState applicationState) {
        synchronized (lock) {
            this.applicationState = applicationState;
            lock.notifyAll();
        }
    }

    public void setIndexingState(IndexingState indexingState) {
        synchronized (lock) {
            this.applicationState = this.applicationState.withIndexingState(indexingState);
            lock.notifyAll();
        }
    }

    public ApplicationState awaitNewApplicationState(ApplicationState old) throws InterruptedException {
        synchronized (lock) {
            while (applicationState.equals(old)) {
                lock.wait();
            }
            return applicationState;
        }
    }

}
