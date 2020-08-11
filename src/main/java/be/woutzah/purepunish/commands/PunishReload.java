package be.woutzah.purepunish.commands;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.managers.ExemptManager;
import be.woutzah.purepunish.managers.PunishmentManager;
import be.woutzah.purepunish.messages.LanguageFileReader;
import be.woutzah.purepunish.messages.Printer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PunishReload extends AbstractCommand {

    private final PurePunish plugin;
    private final LanguageFileReader languageFileReader;
    private final PunishmentManager punishmentManager;
    private final ExemptManager exemptManager;
    private final Printer printer;

    public PunishReload(PurePunish plugin) {
        super("punishreload", true, plugin);
        this.plugin = plugin;
        this.languageFileReader = plugin.getLanguageFileReader();
        this.punishmentManager = plugin.getPunishmentManager();
        this.exemptManager = plugin.getExemptManager();
        this.printer = plugin.getPrinter();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player executor = (Player) sender;
            if (!sender.hasPermission("purepunish.reload")) {
                executor.sendMessage(printer.printNoPermission());
                return;
            }
        }
        plugin.setupFiles();
        languageFileReader.readLanguageEntries();
        punishmentManager.loadPunishTypes();
        exemptManager.readExemptedPlayers();
        sender.sendMessage( ChatColor.translateAlternateColorCodes('&', "&c&lPP&f&l>> &fPurePunish &cV" + plugin.getDescription().getVersion() + " &fhas been reloaded!"));
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
