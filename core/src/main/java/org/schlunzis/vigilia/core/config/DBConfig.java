package org.schlunzis.vigilia.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.File;

@Configuration
public class DBConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource dataSource(@Value("${vigilia.appdir}") String appDir) {
        File appDirFile = new File(appDir);
        if (!appDirFile.exists()) {
            appDirFile.mkdirs();
        }
        return DataSourceBuilder.create().build();
    }


}
