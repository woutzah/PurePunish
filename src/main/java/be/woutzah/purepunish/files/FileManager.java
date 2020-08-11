package be.woutzah.purepunish.files;

import be.woutzah.purepunish.PurePunish;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private final PurePunish plugin;

    public FileManager(PurePunish plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration loadFile(String path, boolean fromJar) {
        FileConfiguration config = null;
        try {
            File file = new File(plugin.getDataFolder(), path);
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                if (fromJar) {
                    plugin.saveResource(path, false);
                } else {
                    file.createNewFile();
                }
            }
            config = new YamlConfiguration();
            config.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
        config.saveToString();
        return config;
    }

    public void saveFile(String path, FileConfiguration input) {
        File file = new File(plugin.getDataFolder(), path);
        try {
            input.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
