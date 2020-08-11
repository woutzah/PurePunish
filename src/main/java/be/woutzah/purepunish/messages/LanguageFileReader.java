package be.woutzah.purepunish.messages;

import be.woutzah.purepunish.PurePunish;
import org.bukkit.configuration.file.FileConfiguration;

public class LanguageFileReader {

    private final PurePunish plugin;
    private String noPermission;
    private String notExistPlayer;
    private String noName;
    private String noPunishType;
    private String notExistPunishType;
    private String noSeverity;
    private String noRightSeverity;
    private String standardReason;
    private String playerPunished;
    private String playerExempt;
    private String errorPunished;
    private String punishmentEntry;
    private String punishmentHeader;
    private String punishmentFooter;
    private String noNumber;
    private String notExistPage;
    private String noPunishments;
    private String typeEntry;
    private String typeHeader;
    private String typeFooter;
    private String exemptEntry;
    private String exemptHeader;
    private String exemptFooter;
    private String noAction;
    private String notExistAction;
    private String listAdded;
    private String listRemoved;
    private String listAlreadyAdded;
    private String listCannotRemove;

    public LanguageFileReader(PurePunish plugin) {
        this.plugin = plugin;
        readLanguageEntries();
    }

    public void readLanguageEntries(){
        FileConfiguration languageConfig = plugin.getLanguageConfig();
        this.noPermission = languageConfig.getString("general.no-permission");
        this.notExistPlayer = languageConfig.getString("general.notexist-player");
        this.noName = languageConfig.getString("general.no-name");
        this.noPunishType = languageConfig.getString("punishment.no-punishtype");
        this.notExistPunishType = languageConfig.getString("punishment.notexist-punishtype");
        this.noSeverity = languageConfig.getString("punishment.no-severity");
        this.noRightSeverity = languageConfig.getString("punishment.no-right-severity");
        this.standardReason = languageConfig.getString("punishment.standard-reason");
        this.playerPunished = languageConfig.getString("punishment.player-punished");
        this.playerExempt = languageConfig.getString("punishment.player-exempt");
        this.errorPunished = languageConfig.getString("punishment.error-punished");
        this.punishmentEntry = languageConfig.getString("punishmenthistory.punishment-entry");
        this.punishmentHeader = languageConfig.getString("punishmenthistory.punishment-header");
        this.punishmentFooter = languageConfig.getString("punishmenthistory.punishment-footer");
        this.noNumber = languageConfig.getString("punishmenthistory.no-number");
        this.notExistPage = languageConfig.getString("punishmenthistory.notexist-page");
        this.noPunishments = languageConfig.getString("punishmenthistory.no-punishments");
        this.typeEntry = languageConfig.getString("punishmenttypes.type-entry");
        this.typeHeader = languageConfig.getString("punishmenttypes.type-header");
        this.typeFooter = languageConfig.getString("punishmenttypes.type-footer");
        this.exemptEntry = languageConfig.getString("punishmentexempt.exempt-entry");
        this.exemptHeader = languageConfig.getString("punishmentexempt.exempt-header");
        this.exemptFooter = languageConfig.getString("punishmentexempt.exempt-footer");
        this.noAction = languageConfig.getString("punishmentexempt.no-action");
        this.notExistAction = languageConfig.getString("punishmentexempt.notexist-action");
        this.listAdded = languageConfig.getString("punishmentexempt.list-added");
        this.listRemoved = languageConfig.getString("punishmentexempt.list-removed");
        this.listAlreadyAdded = languageConfig.getString("punishmentexempt.list-already-added");
        this.listCannotRemove = languageConfig.getString("punishmentexempt.list-cannot-remove");
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getNotExistPlayer() {
        return notExistPlayer;
    }

    public String getNoName() {
        return noName;
    }

    public String getNoPunishType() {
        return noPunishType;
    }

    public String getNotExistPunishType() {
        return notExistPunishType;
    }

    public String getNoSeverity() {
        return noSeverity;
    }

    public String getNoRightSeverity() {
        return noRightSeverity;
    }

    public String getStandardReason() {
        return standardReason;
    }

    public String getPlayerPunished() {
        return playerPunished;
    }

    public String getPlayerExempt() {
        return playerExempt;
    }

    public String getErrorPunished() {
        return errorPunished;
    }

    public String getPunishmentEntry() {
        return punishmentEntry;
    }

    public String getPunishmentHeader() {
        return punishmentHeader;
    }

    public String getPunishmentFooter() {
        return punishmentFooter;
    }

    public String getNoNumber() {
        return noNumber;
    }

    public String getNotExistPage() {
        return notExistPage;
    }

    public String getNoPunishments() {
        return noPunishments;
    }

    public String getTypeEntry() {
        return typeEntry;
    }

    public String getTypeHeader() {
        return typeHeader;
    }

    public String getTypeFooter() {
        return typeFooter;
    }

    public String getExemptEntry() {
        return exemptEntry;
    }

    public String getExemptHeader() {
        return exemptHeader;
    }

    public String getExemptFooter() {
        return exemptFooter;
    }

    public String getNoAction() {
        return noAction;
    }

    public String getNotExistAction() {
        return notExistAction;
    }

    public String getListAdded() {
        return listAdded;
    }

    public String getListRemoved() {
        return listRemoved;
    }

    public String getListAlreadyAdded() {
        return listAlreadyAdded;
    }

    public String getListCannotRemove() {
        return listCannotRemove;
    }
}
