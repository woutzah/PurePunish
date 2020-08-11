package be.woutzah.purepunish.managers;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.files.FileManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExemptManager {

    private final PurePunish plugin;
    private List<UUID> exemptPlayerList;
    private FileConfiguration config;
    private final FileManager fileManager;

    public ExemptManager(PurePunish plugin) {
        this.plugin = plugin;
        this.fileManager = plugin.getFileManager();
        readExemptedPlayers();
    }

    public void readExemptedPlayers() {
        this.config = plugin.getExemptConfig();
        this.exemptPlayerList = new ArrayList<>();
        config.getStringList("exempted-players")
                .forEach(e -> exemptPlayerList.add(UUID.fromString(e)));
    }

    private void saveExemptedPlayers() {
        config.set("exempted-players", exemptPlayerList
                .stream()
                .map(UUID::toString)
                .collect(Collectors.toList()));
       fileManager.saveFile("exempt.yml",config);
    }

    public List<UUID> getAllExemptedPlayers() {
        return exemptPlayerList;
    }

    public void addExemptedPlayer(UUID uuid) {
        exemptPlayerList.add(uuid);
        saveExemptedPlayers();
    }

    public void removeExemptedPlayer(UUID uuid) {
        exemptPlayerList.remove(uuid);
        saveExemptedPlayers();
    }

    public boolean checkIfExempted(UUID uuid) {
        return exemptPlayerList.contains(uuid);
    }

}
