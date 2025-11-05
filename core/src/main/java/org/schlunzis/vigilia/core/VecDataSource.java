package org.schlunzis.vigilia.core;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class VecDataSource extends DriverManagerDataSource {

    @Override
    protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
        Connection con = super.getConnectionFromDriverManager(url, props);

        System.out.println("Loading SQLite extensions...");
        loadExtension(con);
        System.out.println("SQLite extensions loaded.");

        return con;
    }

    private void loadExtension(Connection connection) {
        loadExtensionFromResource("vec0.so", connection); // FIXME detect platform
    }

    private void loadExtensionFromResource(String resourcePath, Connection connection) {
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }

            Path tempFile = Files.createTempFile("vec0", ".so");
            tempFile.toFile().deleteOnExit();
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);

            Statement stmt = connection.createStatement();
            stmt.execute(String.format("SELECT load_extension('%s')", tempFile.toAbsolutePath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
