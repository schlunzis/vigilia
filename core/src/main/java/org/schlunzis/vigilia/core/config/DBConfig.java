package org.schlunzis.vigilia.core.config;

import lombok.RequiredArgsConstructor;
import org.schlunzis.vigilia.core.VecDataSource;
import org.schlunzis.vigilia.core.autoconfigure.VigiliaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;

/**
 * Configuration for the database.
 *
 * <p>Using an SQLite database the path to the sqlite file must exist in order to create the db. Since this is not
 * automatically covered by the default DataSource generation, we need to intercept the DataSource creation and create
 * the directory if it does not exist.
 * <p>
 *
 * @author Jonas Pohl
 * @see <a href="https://docs.spring.io/spring-boot/how-to/data-access.html#howto.data-access.configure-custom-datasource">Spring Boot Docs</a>
 */
@Configuration
@RequiredArgsConstructor
public class DBConfig {

    private final VigiliaProperties vigiliaProperties;

    @Bean
    public DataSource dataSource() {
        String appDir = vigiliaProperties.getAppdir();
        File appDirFile = new File(appDir);
        if (!appDirFile.exists()) {
            appDirFile.mkdirs();
        }

        DriverManagerDataSource dataSource = new VecDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl(vigiliaProperties.getDatasource().getUrl());
        return dataSource;
    }

}
