package org.schlunzis.vigilia.cli.commands;

import picocli.CommandLine;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class VersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() {
        Properties myProperties = new Properties();
        try {
            myProperties.load(getClass().getResourceAsStream("/version.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        String theVersion = Objects.requireNonNull((String) myProperties.get("version"));
        return new String[]{theVersion};
    }

}
