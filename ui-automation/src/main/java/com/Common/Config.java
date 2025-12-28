package com.Common;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Config {
    private Map<String, String> data;
    private Map<String, String> gridConfiguration;

    private static volatile Config config;

    private volatile boolean isInitialized = false;

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public static Config getConfig() {
        if (config == null) {
            Class var0 = Config.class;
            synchronized (Config.class) {
                if (config == null) {
                    config = new Config();
                }
            }
        }
        return config;
    }

    public synchronized void initialize() {
        if (this.isInitialized) {
            System.out.println("config already initialized, skipping...");
        } else {
            try {
                this.config = mapper.readValue(new File(System.getProperty("user.dir") + "/Config-production.yaml"), Config.class);
                this.isInitialized = true;
            } catch (IOException e) {
                System.out.println("Config initialized fail: " + e.getMessage());
                throw new RuntimeException("Config mapping failed: " + e);
            }
        }
    }


    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getGridConfiguration() {
        return gridConfiguration;
    }

    public void setGridConfiguration(Map<String, String> gridConfiguration) {
        this.gridConfiguration = gridConfiguration;
    }

    public String getBaseUrl() {
        return data.get("BaseUrl");
    }

    public String getBrowser() {
        return data.get("Browser");
    }

    public String getCareerButtonText() {
        return data.get("CareerButtonText");
    }

    public String getFindJobButtonText() {
        return data.get("FindJobButtonText");
    }

    public String getQaJobButtonText() {
        return data.get("QaJobButtonText");
    }

    public String getQaJobTitle() {
        return data.get("QaJobTitle");
    }

    public String getDefaultLocation() {
        return data.get("DefaultLocation");
    }

    public String getGridUrl() {
        return gridConfiguration.get("gridURL");
    }

    public boolean isDebug() {
        return Boolean.parseBoolean(gridConfiguration.get("isDebug"));
    }

    public Config() {

    }
}