package be.woutzah.purepunish.punishtypes;

import java.util.List;

public class PunishType {

    private final String typeName;
    private final String scope;
    private final int mild;
    private final int medium;
    private final int severe;
    private final List<String> aliasList;
    private final List<AutoCommand> autoCommandList;

    public PunishType(String typeName, String scope, int mild, int medium,
                      int severe, List<String> aliasList,
                      List<AutoCommand> autoCommandList) {
        this.typeName = typeName;
        this.scope = scope;
        this.mild = mild;
        this.medium = medium;
        this.severe = severe;
        this.aliasList = aliasList;
        this.autoCommandList = autoCommandList;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getScope() {
        return scope;
    }

    public int getMild() {
        return mild;
    }

    public int getMedium() {
        return medium;
    }

    public int getSevere() {
        return severe;
    }

    public List<String> getAliasList() {
        return aliasList;
    }

    public List<AutoCommand> getAutoCommandList() {
        return autoCommandList;
    }
}
