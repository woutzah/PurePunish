package be.woutzah.purepunish.messages;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.database.Punishment;
import be.woutzah.purepunish.managers.ExemptManager;
import be.woutzah.purepunish.managers.PunishmentManager;
import be.woutzah.purepunish.punishtypes.PunishType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Objects;

public class Printer {

    private final PurePunish plugin;
    private final PunishmentManager punishmentManager;
    private final LanguageFileReader languageFileReader;
    private final ExemptManager exemptManager;

    public Printer(PurePunish plugin) {
        this.plugin = plugin;
        this.punishmentManager = plugin.getPunishmentManager();
        this.exemptManager = plugin.getExemptManager();
        this.languageFileReader = plugin.getLanguageFileReader();
    }

    public void printConsoleMessage() {
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&c&l>&f&m-----------&c&l[ &b&oPurePunish &c&l]&f&m-----------&c&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&c&l>                                    &c&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&c&l>           &bPurePunish V" + plugin.getDescription().getVersion()
                        + "          &c&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&c&l>             &7&oBy woutzah             &c&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&c&l>                                    &c&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&c&l>            &aEnabling ...            &c&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&c&l>                                    &c&l<"));
        Bukkit.getConsoleSender()
                .sendMessage(parseColorCodes("&c&l>&f&m------------------------------------&c&l<"));
    }

    public String printNoPermission() {
        return parseColorCodes(languageFileReader.getNoPermission());
    }

    public String printNotExistPlayer() {
        return parseColorCodes(languageFileReader.getNotExistPlayer());
    }

    public String printNoName() {
        return parseColorCodes(languageFileReader.getNoName());
    }

    public String printNoPunishType() {
        return parseColorCodes(languageFileReader.getNoPunishType());
    }

    public String printNotExistPunishType() {
        return parseColorCodes(languageFileReader.getNotExistPunishType());
    }

    public String printNoSeverity() {
        return parseColorCodes(languageFileReader.getNoSeverity());
    }

    public String printNoRightSeverity() {
        return parseColorCodes(languageFileReader.getNoRightSeverity());
    }

    public String printStandardReason() {
        return parseColorCodes(languageFileReader.getStandardReason());
    }

    public String printPlayerPunished(String name) {
        return parseColorCodes(languageFileReader.getPlayerPunished()
                .replace("<player>", name));
    }

    public String printPlayerExempt() {
        return parseColorCodes(languageFileReader.getPlayerExempt());
    }

    public String printErrorPunished() {
        return parseColorCodes(languageFileReader.getErrorPunished());
    }

    public String printNoNumber(String number) {
        return parseColorCodes(languageFileReader.getNoNumber()
                .replace("<number>", number));
    }

    public String printNotExistPage() {
        return parseColorCodes(languageFileReader.getNotExistPage());
    }

    public String printNoPunishments(String name) {
        return parseColorCodes(languageFileReader.getNoPunishments()
                .replace("<player>", name));
    }

    public String printNoAction() {
        return parseColorCodes(languageFileReader.getNoAction());
    }

    public String printNotExistAction() {
        return parseColorCodes(languageFileReader.getNotExistAction());
    }

    public String printListAdded(String name) {
        return parseColorCodes(languageFileReader.getListAdded()
                .replace("<player>", name));
    }

    public String printListRemoved(String name) {
        return parseColorCodes(languageFileReader.getListRemoved()
                .replace("<player>", name));
    }

    public String printListAlreadyAdded() {
        return parseColorCodes(languageFileReader.getListAlreadyAdded());
    }

    public String printListCannotRemove() {
        return parseColorCodes(languageFileReader.getListCannotRemove());
    }


    public String printExemptList() {
        StringBuilder sb = new StringBuilder();
        sb.append(languageFileReader.getExemptHeader()
                .replace("\\n", "\n"));
        if (exemptManager.getAllExemptedPlayers().isEmpty()) {
            sb.append("&cNo players\n");
        } else {
            exemptManager.getAllExemptedPlayers().forEach(u -> sb.append(languageFileReader.getExemptEntry()
                    .replace("\\n", "\n")
                    .replace("<player>", Bukkit.getOfflinePlayer(u).getName())));
        }
        sb.append(languageFileReader.getExemptFooter());
        return parseColorCodes(sb.toString());
    }

    public String printReasons() {
        StringBuilder sb = new StringBuilder();
        sb.append(languageFileReader.getTypeHeader()
                .replace("\\n", "\n"));
        punishmentManager.getPunishTypeList()
                .stream()
                .map(PunishType::getTypeName).forEach(n -> sb.append(languageFileReader.getTypeEntry()
                .replace("\\n", "\n")
                .replace("<type>", n.toLowerCase())));
        sb.append(languageFileReader.getTypeFooter());
        return parseColorCodes(sb.toString());
    }

    public String printPunishment(Punishment punishment) {
        return parseColorCodes(languageFileReader.getPunishmentEntry()
                .replace("\\n", "\n")
                .replace("<type>", punishment.getType().toLowerCase())
                .replace("<reason>", punishment.getReason())
                .replace("<date>", punishment.getDate())
                .replace("<server>", punishment.getServer()));
    }

    public String printPunishmenthistoryHeader(String name, int pageNumber) {
        return parseColorCodes(languageFileReader.getPunishmentHeader()
                .replace("\\n", "\n")
                .replace("<player>", name)
                .replace("<pnum>", String.valueOf(pageNumber)));
    }

    public String printPunishmenthistoryFooter() {
        return parseColorCodes(languageFileReader.getPunishmentFooter());
    }

    private String parseColorCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
