package be.woutzah.purepunish;

import be.woutzah.purepunish.commands.AbstractCommand;
import be.woutzah.purepunish.commands.listeners.CommandListener;
import be.woutzah.purepunish.database.PunishmentDatabase;
import be.woutzah.purepunish.managers.ExemptManager;
import be.woutzah.purepunish.files.FileManager;
import be.woutzah.purepunish.managers.PunishmentManager;
import be.woutzah.purepunish.messages.LanguageFileReader;
import be.woutzah.purepunish.messages.Printer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PurePunish extends JavaPlugin {

    private FileManager fileManager;
    private FileConfiguration languageConfig;
    private FileConfiguration reasonsConfig;
    private FileConfiguration exemptConfig;
    private LanguageFileReader languageFileReader;
    private PunishmentManager punishmentManager;
    private PunishmentDatabase punishmentDatabase;
    private Printer printer;
    private ExemptManager exemptManager;

    @Override
    public void onEnable() {
        this.fileManager = new FileManager(this);
        setupFiles();
        init();
        printer.printConsoleMessage();
        this.getServer().getPluginManager().registerEvents(new CommandListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public void init() {
        this.languageFileReader = new LanguageFileReader(this);
        this.punishmentDatabase = new PunishmentDatabase(this);
        this.punishmentManager = new PunishmentManager(this);
        this.exemptManager = new ExemptManager(this);
        this.printer = new Printer(this);
        AbstractCommand.registerCommands(this);
    }

    public void setupFiles(){
        saveDefaultConfig();
        languageConfig = fileManager.loadFile("language/language.yml", true);
        reasonsConfig = fileManager.loadFile("reasons/reasons.yml", true);
        exemptConfig = fileManager.loadFile("exempt.yml",true);
    }



    public FileConfiguration getLanguageConfig() {
        return languageConfig;
    }

    public FileConfiguration getReasonsConfig() {
        return reasonsConfig;
    }

    public FileConfiguration getExemptConfig() {
        return exemptConfig;
    }

    public LanguageFileReader getLanguageFileReader() {
        return languageFileReader;
    }

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }

    public PunishmentDatabase getPunishmentDatabase() {
        return punishmentDatabase;
    }

    public Printer getPrinter() {
        return printer;
    }

    public ExemptManager getExemptManager() {
        return exemptManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
