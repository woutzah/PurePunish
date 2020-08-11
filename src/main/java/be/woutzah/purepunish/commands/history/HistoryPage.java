package be.woutzah.purepunish.commands.history;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.database.Punishment;

import java.util.ArrayList;
import java.util.List;

public class HistoryPage {

    private final PurePunish plugin;
    private final List<Punishment> punishmentEntries;
    private final String playerName;
    private final int pageNumber;

    public HistoryPage(PurePunish plugin, String playerName, int pageNumber) {
        this.plugin = plugin;
        this.playerName = playerName;
        this.pageNumber = pageNumber;
        this.punishmentEntries = new ArrayList<>();
    }

    public void addEntry(Punishment punishment){
        punishmentEntries.add(punishment);
    }

    public List<Punishment> getPunishmentEntries() {
        return punishmentEntries;
    }

    public String printPage(){
        StringBuilder sb = new StringBuilder();
        sb.append(plugin.getPrinter().printPunishmenthistoryHeader(playerName, pageNumber));
        punishmentEntries.forEach(p -> sb.append(plugin.getPrinter().printPunishment(p)));
        sb.append(plugin.getPrinter().printPunishmenthistoryFooter());
        return sb.toString();
    }
}
