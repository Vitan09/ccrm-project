package edu.ccrm.config;

public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    // default max credits
    private final int maxCreditsPerSemester = 24;

    private final String dataFolder = "data";

    private AppConfig() {}

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public String getDataFolder() {
        return dataFolder;
    }

    public int getMaxCreditsPerSemester() {
        return maxCreditsPerSemester;
    }
}
