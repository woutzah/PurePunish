package be.woutzah.purepunish.database;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.database.dao.PunishmentDAO;

import java.util.List;
import java.util.UUID;

public class PunishmentDatabase {

    private final PurePunish plugin;
    private final PunishmentDAO punishmentDAO;

    public PunishmentDatabase(PurePunish plugin) {
        this.plugin = plugin;
        this.punishmentDAO = new PunishmentDAO(generateUrl(), getDBUser(), getDBPassword(), plugin);
        punishmentDAO.createTable();
    }

    public List<Punishment> getAllPunishmentsForPlayer(UUID uuid) {
        return punishmentDAO.getAllPunishmentsForPlayer(uuid.toString());
    }

    public boolean addPunishmentForPlayer(Punishment punishment) {
        return punishmentDAO.addPunishmentForPlayer(punishment);
    }

    public int getTotalWeightPreviousPunishments(String uuid, String type) {
        return punishmentDAO.getTotalWeightPreviousPunishments(uuid, type);
    }

    private String generateUrl() {
        String host = plugin.getConfig().getString("mysql.host");
        String port = plugin.getConfig().getString("mysql.port");
        String database = plugin.getConfig().getString("mysql.database");
        return "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
    }

    private String getDBUser() {
        return plugin.getConfig().getString("mysql.username");
    }

    private String getDBPassword() {
        return plugin.getConfig().getString("mysql.password");
    }


}
