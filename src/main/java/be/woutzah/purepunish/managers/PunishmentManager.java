package be.woutzah.purepunish.managers;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.database.Punishment;
import be.woutzah.purepunish.database.PunishmentDatabase;
import be.woutzah.purepunish.punishtypes.AutoCommand;
import be.woutzah.purepunish.punishtypes.PunishType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class PunishmentManager {

    private final PurePunish plugin;
    private List<PunishType> punishTypeList;
    private final PunishmentDatabase punishmentDatabase;

    public PunishmentManager(PurePunish plugin) {
        this.plugin = plugin;
        loadPunishTypes();
        this.punishmentDatabase = plugin.getPunishmentDatabase();
    }

    public List<Punishment> getAllPunishmentsForPlayer(UUID uuid) {
        return punishmentDatabase.getAllPunishmentsForPlayer(uuid);
    }
    public int getTotalWeightPreviousPunishments(String uuid, String type){
        return punishmentDatabase.getTotalWeightPreviousPunishments(uuid, type);
    }

    public boolean addPunishmentForPlayer(Punishment punishment) {
        return punishmentDatabase.addPunishmentForPlayer(punishment);
    }

    public void loadPunishTypes() {
        this.punishTypeList = new ArrayList<>();
        FileConfiguration config = plugin.getReasonsConfig();
        ConfigurationSection punishSection = config.getConfigurationSection("reasons");
        for (String punishEntry :
                Objects.requireNonNull(punishSection.getKeys(false))) {
            try {
                PunishType newPunishType = new PunishType(punishSection.getString(punishEntry + ".type").toUpperCase(),
                        punishSection.getString(punishEntry + ".scope"),
                        punishSection.getInt(punishEntry + ".mild"),
                        punishSection.getInt(punishEntry + ".medium"),
                        punishSection.getInt(punishEntry + ".severe"),
                        punishSection.getStringList(punishEntry + ".aliases").stream()
                                .map(String::toUpperCase).collect(Collectors.toList()),
                        getAutoCommands(Objects.requireNonNull(punishSection
                                .getConfigurationSection(punishEntry + ".auto-commands"))));
                punishTypeList.add(newPunishType);
            }catch (NullPointerException ex){
                Bukkit.getConsoleSender().sendMessage("[PurePunish Error] " + punishEntry + "is not configured correctly! It is missing something.");
            }
        }
    }

    public int calculateWeight(String severity, String type) {
        PunishType pt = getPunishTypeObject(type);
        if(pt == null){
            return 0;
        }
        switch (severity.toUpperCase()) {
            case "MILD":
                return pt.getMild();
            case "MEDIUM":
                return pt.getMedium();
            case "SEVERE":
                return pt.getSevere();
            default:
                return 0;
        }
    }

    public PunishType getPunishTypeObject(String type) {
        PunishType punishtype = punishTypeList.stream().filter(pt -> pt.getTypeName().equalsIgnoreCase(type)).findFirst().orElse(null);
        if (!(punishtype == null)) {
            return punishtype;
        }
        punishtype = punishTypeList.stream()
                .filter(pt -> pt.getAliasList().contains(type)).findFirst().orElse(null);
        if (!(punishtype == null)) {
            return punishtype;
        }
        return null;
    }

    public AutoCommand getAutoCommandWithWeight(PunishType punishType, int weight){
        int currentWeight = weight;
        while (true) {
            for (AutoCommand autoCommand : punishType.getAutoCommandList()) {
                if (autoCommand.getWeight() == currentWeight) {
                    return autoCommand;
                }
            }
            currentWeight -= 1;
        }
    }

    private List<AutoCommand> getAutoCommands(ConfigurationSection section) {
        List<AutoCommand> autoCommands = new ArrayList<>();
        for (String autoCommandEntry : section.getKeys(false)) {
            autoCommands.add(new AutoCommand(section.getString(autoCommandEntry + ".command"),
                    section.getInt(autoCommandEntry + ".weight-milestone")));
        }
        return autoCommands;
    }

    public List<PunishType> getPunishTypeList() {
        return punishTypeList;
    }
}
