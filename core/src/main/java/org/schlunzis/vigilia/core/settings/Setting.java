package org.schlunzis.vigilia.core.settings;

import lombok.Getter;

import java.io.File;

@Getter
public class Setting<T> {

    public static final Setting<Integer> PORT = new Setting<>("port", 54913);
    public static final Setting<String> DATA_FOLDER = new Setting<>("dataFolder", System.getProperty("user.home") + File.separatorChar + ".vigilia");

    public static final Setting<String> JDBC_URL = new Setting<>("jdbcUrl", "jdbc:sqlite:" + System.getProperty("user.home") + File.separatorChar + ".vigilia" + File.separatorChar + "vigilia.db");
    public static final Setting<String> JDBC_USERNAME = new Setting<>("jdbcUsername", "sa");
    public static final Setting<String> JDBC_PASSWORD = new Setting<>("jdbcPassword", "sa");
    public static final Setting<String> JDBC_DRIVER_CLASS_NAME = new Setting<>("jdbcDriverClassName", "org.sqlite.JDBC");


    private final String preferencesKey;
    private final T defaultValue;

    protected Setting(String preferencesKey, T defaultValue) {
        this.preferencesKey = preferencesKey;
        this.defaultValue = defaultValue;
    }

}