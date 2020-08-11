package be.woutzah.purepunish.database.dao;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.database.Punishment;
import org.bukkit.Bukkit;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PunishmentDAO {

    private final String url;
    private final String user;
    private final String password;
    private final int dateLimit;
    private final String serverName;

    public PunishmentDAO(String url, String user, String password, PurePunish plugin) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.dateLimit = plugin.getConfig().getInt("punishment-timer-days");
        this.serverName = plugin.getConfig().getString("server-name");
    }

    public List<Punishment> getAllPunishmentsForPlayer(String uuid) {
        List<Punishment> punishmentList = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM punishments WHERE Uuid=?")) {
            stmt.setString(1, uuid);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Punishment punishment = new Punishment();
                    punishment.setUuid(rs.getString("Uuid"));
                    punishment.setType(rs.getString("Type"));
                    punishment.setReason(rs.getString("Reason"));
                    punishment.setWeight(rs.getInt("Weight"));
                    punishment.setDate(rs.getString("Date"));
                    punishment.setServer(rs.getString("Server"));
                    punishmentList.add(punishment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return punishmentList;
    }

    public int getTotalWeightPreviousPunishments(String uuid, String type) {
        LocalDate date = LocalDate.now().minusDays(dateLimit);
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateLimit = formatter.format(Date.valueOf(date));
        int totalWeight = 0;
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT Weight FROM punishments WHERE Uuid=? AND Type=? AND Date > ?")) {
            stmt.setString(1, uuid);
            stmt.setString(2, type);
            stmt.setString(3, dateLimit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    totalWeight += rs.getInt("Weight");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalWeight;
    }

    public boolean addPunishmentForPlayer(Punishment punishment) {
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement("insert into punishments (Uuid, Type, Reason, Weight, Date, Server)" +
                     " values (?, ?, ?, ?, ?,?)")) {
            stmt.setString(1, punishment.getUuid());
            stmt.setString(2, punishment.getType());
            stmt.setString(3, punishment.getReason());
            stmt.setInt(4, punishment.getWeight());
            stmt.setString(5, punishment.getDate());
            stmt.setString(6, serverName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void createTable() {
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS punishments (Uuid varchar(36)," +
                     "Type varchar(36) ,Reason varchar(36), Weight int, Date varchar(36), Server varchar(36))")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("[PurePunish] Database could not be created!");
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
