package org.schlunzis.vigilia.core.tray;

import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.state.ApplicationState;
import org.schlunzis.vigilia.core.state.ApplicationStateProvider;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

@Slf4j
@Component
public class TrayIconManager {

    private final Image idleImage = ImageIO.read(getClass().getResourceAsStream("/img/icon.png"));

    private final ApplicationStateProvider applicationStateProvider;
    private final TrayIcon trayIcon;

    public TrayIconManager(ApplicationStateProvider applicationStateProvider) throws IOException {
        this.applicationStateProvider = applicationStateProvider;
        if (!SystemTray.isSupported()) {
            log.error("System tray is not supported");
            trayIcon = null;
            return;
        }

        trayIcon = new TrayIcon(idleImage);
        trayIcon.setImageAutoSize(true);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setup() {
        if (trayIcon == null) {
            return;
        }

        try {
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {
            log.error("Failed to add tray icon", e);
            return;
        }

        new TrayIconThread().start();
    }

    private class TrayIconThread extends Thread {

        private ApplicationState lastState = null;

        @Override
        public void run() {
            if (trayIcon == null)
                return;

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ApplicationState currentState = applicationStateProvider.awaitNewApplicationState(lastState);
                    updateTrayIcon(currentState);
                    lastState = currentState;
                } catch (InterruptedException _) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void updateTrayIcon(ApplicationState applicationState) {
            if (trayIcon == null) {
                return;
            }
        }
    }

}
