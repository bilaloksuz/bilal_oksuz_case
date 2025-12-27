package configutartion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Config {
    private Map<String, String> pet;

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
                this.isInitialized=true;
            } catch (IOException e) {
                System.out.println("Config initialized fail: " + e.getMessage());
                throw new RuntimeException("Config mapping failed: " + e);
            }
        }
    }

    public static void setConfig(Config config) {
        Config.config = config;
    }

    public String getPetBaseURL() {
        return pet.get("BaseUrl");
    }

    public String getPetBasePath() {
        return pet.get("BasePath");
    }

    public String getPetFindByStatusPath() {
        return pet.get("FindByStatus");
    }

    public String getPetUploadImagePath() {
        return pet.get("UploadImage");
    }

    public String getValidationMessagePositive() {
        return pet.get("ValidationMessagePositive");
    }

    public String getValidationMessageNegative() {
        return pet.get("ValidationMessageNegative");
    }

    public String getResponseCodePositive() {
        return pet.get("ResponseCodePositive");
    }

    public String getResponseCodeNegative() {
        return pet.get("ResponseCodeNegative");
    }

    public Map<String, String> getPet() {
        return pet;
    }

    public void setPet(Map<String, String> pet) {
        this.pet = pet;
    }

}
