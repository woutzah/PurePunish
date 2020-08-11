package be.woutzah.purepunish.database;

import java.io.Serializable;

public class Punishment implements Serializable {

    private String uuid;
    private String type;
    private String reason;
    private int weight;
    private String date;
    private String server;

    public Punishment(){

    }

    public Punishment(String uuid, String type, String reason, int weight, String date) {
        this.uuid = uuid;
        this.type = type;
        this.reason = reason;
        this.weight = weight;
        this.date = date;
        this.server = "";
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
