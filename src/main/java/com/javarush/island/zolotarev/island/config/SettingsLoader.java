package com.javarush.island.zolotarev.island.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

public final class SettingsLoader {

    private static final String SETTINGS_PATH = "/zolotarev/setting.yaml";
    private static final IslandSettings SETTINGS = load();

    private SettingsLoader() {
    }

    public static IslandSettings get() {
        return SETTINGS;
    }

    private static IslandSettings load() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream input = SettingsLoader.class.getResourceAsStream(SETTINGS_PATH)) {
            if (input == null) {
                throw new IllegalStateException("Settings file not found: " + SETTINGS_PATH);
            }
            return mapper.readValue(input, IslandSettings.class);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load settings from " + SETTINGS_PATH, e);
        }
    }
}
