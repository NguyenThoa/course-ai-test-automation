package com.anhtester.crm.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads {@code config.properties} once and exposes typed accessors for it.
 * Centralizing configuration here keeps environment/credentials/browser
 * settings out of the page objects and test classes.
 */
public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = load();

    private ConfigReader() {
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Unable to find " + CONFIG_FILE + " on the classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + CONFIG_FILE, e);
        }
        return properties;
    }

    public static String baseUrl() {
        return get("base.url");
    }

    public static String validEmail() {
        return get("valid.email");
    }

    public static String validPassword() {
        return get("valid.password");
    }

    public static String browser() {
        return get("browser");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static long explicitWaitSeconds() {
        return Long.parseLong(get("explicit.wait.seconds"));
    }

    /**
     * Reads a raw property, allowing a JVM system property (-Dkey=value) to
     * override the value in config.properties, e.g. for CI runs.
     */
    private static String get(String key) {
        String override = System.getProperty(key);
        if (override != null && !override.isBlank()) {
            return override;
        }
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Missing required property: " + key);
        }
        return value;
    }
}
