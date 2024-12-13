import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        try (FileReader reader = new FileReader("./config/config.json")) {
            Gson gson = new Gson();
            config = gson.fromJson(reader, GameConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback to default configuration
            config = new GameConfig();
        }
    }
    
    public void saveConfig() {
        try (FileWriter writer = new FileWriter("./config/config.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameConfig getConfig() {
        return config;
    }
}

