package org.schlunzis.vigilia.core.config;

import org.springframework.context.annotation.Configuration;

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
public class DBConfig {

//    @Bean
//    @Primary
//    @ConfigurationProperties("app.datasource")
//    public DataSourceProperties dataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties("app.datasource.configuration")
//    public HikariDataSource dataSource(DataSourceProperties properties, @Value("${vigilia.appdir}") String appDir) {
//        File appDirFile = new File(appDir);
//        if (!appDirFile.exists()) {
//            appDirFile.mkdirs();
//        }
//        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
}
