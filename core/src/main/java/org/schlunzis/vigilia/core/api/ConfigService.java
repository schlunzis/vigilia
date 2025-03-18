package org.schlunzis.vigilia.core.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.CoreApplication;
import org.schlunzis.vigilia.core.model.DatabaseConfigDTO;
import org.schlunzis.vigilia.core.settings.IUserSettings;
import org.schlunzis.vigilia.core.settings.Setting;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigService implements ConfigApiDelegate {

    private final IUserSettings userSettings;

    @Override
    public ResponseEntity<Void> updateDatabaseConfig(DatabaseConfigDTO databaseConfigDTO) {
        log.info("Updating database config: {}", databaseConfigDTO);
        userSettings.putString(Setting.JDBC_URL, databaseConfigDTO.getUrl());
        userSettings.putString(Setting.JDBC_USERNAME, databaseConfigDTO.getUsername());
        userSettings.putString(Setting.JDBC_PASSWORD, databaseConfigDTO.getPassword());
        log.info("Restarting application to apply changes");
        CoreApplication.restart();
        log.info("Application restarted");
        return ResponseEntity.ok().build();

    }
}
