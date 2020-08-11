package be.woutzah.purepunish.commands.history;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.database.Punishment;

import java.util.ArrayList;
import java.util.List;

public class HistoryCreator {

    private final List<HistoryPage> historyPageList;
    private final PurePunish plugin;
    private final String playerName;
    private final int pageLimit;
    private int pageCounter = 1;

    public HistoryCreator(PurePunish plugin, String playerName) {
        this.plugin = plugin;
        this.playerName = playerName;
        this.historyPageList = new ArrayList<>();
        this.pageLimit = plugin.getConfig().getInt("punishmenthistory-page-amount");
    }

    public boolean createHistoryPages(List<Punishment> punishmentList){
        if (punishmentList.isEmpty()){
            return false;
        }
        HistoryPage hp = new HistoryPage(plugin, playerName,pageCounter);
        for(int i = 1; i <= punishmentList.size() ;i++){
            if(hp.getPunishmentEntries().size() < pageLimit){
                hp.addEntry(punishmentList.get(i-1));
                if (i == punishmentList.size()){
                    historyPageList.add(hp);
                }
            }else{
                historyPageList.add(hp);
                pageCounter++;
                hp = new HistoryPage(plugin, playerName,pageCounter);
                hp.addEntry(punishmentList.get(i-1));
                if (i == punishmentList.size()){
                    historyPageList.add(hp);
                }
            }
        }
        pageCounter = 1;
        return true;
    }

    public List<HistoryPage> getHistoryPageList() {
        return historyPageList;
    }

    public HistoryPage getHistoryPageWithNumber(int number){
        return historyPageList.get(number - 1);
    }

    public int getAmountOfPages(){
        return historyPageList.size();
    }
}
