package org.schlunzis.vigilia.core.db;


import lombok.RequiredArgsConstructor;
import org.schlunzis.vigilia.core.settings.IUserSettings;
import org.schlunzis.vigilia.core.settings.Setting;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceFactory {

    private final IUserSettings userSettings;

    @Bean
    public DataSource createDataSource() {
        return DataSourceBuilder.create()
                .url(userSettings.getString(Setting.JDBC_URL))
                .username(userSettings.getString(Setting.JDBC_USERNAME))
                .password(userSettings.getString(Setting.JDBC_PASSWORD))
                // .driverClassName(userSettings.getString(Setting.JDBC_DRIVER_CLASS_NAME))
                .build();
    }

}
