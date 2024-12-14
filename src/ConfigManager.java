import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class ConfigManager {
    private static ConfigManager instance;
    private GameConfig config;

    private ConfigManager() {
        loadConfig();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    // Load configuration from JSON file
    private void loadConfig() {
        File configFile = new File("./config.json");

        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                Gson gson = new Gson();
                config = gson.fromJson(reader, GameConfig.class);
            } catch (Exception e) {
                System.out.println("Error while loading configuration file. Falling back to default configuration.");
                loadDefaultConfig();
            }
        } else {
            System.out.println("Configuration file not found. Falling back to default configuration.");
            loadDefaultConfig();
        }

    }
    private void loadDefaultConfig() {
        try (InputStream in = getClass().getResourceAsStream("/config/default_config.json")) {
            if (in == null) {
                System.out.println("Can't find default_config.json using getClass(). Loading default configuration from config folder.");
                try (FileReader reader = new FileReader("./config/default_config.json")) {
                    Gson gson = new Gson();
                    config = gson.fromJson(reader, GameConfig.class);
                } catch (IOException e) {
                    throw new FileNotFoundException("Default configuration file not found.");
                }

            }
            else {
                Reader reader = new InputStreamReader(in);
                Gson gson = new Gson();
                config = gson.fromJson(reader, GameConfig.class);
            }
            // if loadDefaultConfig() is called, it means that the configuration file is missing
            // so we should save the default configuration to a new file for the user
            saveConfig();
        } catch (IOException e) {
            throw new RuntimeException("Error while loading default configuration file.");
        }
    } 
    public void saveConfig() {
        try (FileWriter writer = new FileWriter("./config.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(config, writer);
        } catch (Exception e) {
            System.out.println("Error while saving configuration file.");
        }
    }

    public GameConfig getConfig() {
        return config;
    }
}

