package be.woutzah.purepunish.punishtypes;

public class AutoCommand {

    private final String command;
    private final int weight;

    public AutoCommand(String command, int weight) {
        this.command = command;
        this.weight = weight;
    }

    public String getCommand() {
        return command;
    }

    public int getWeight() {
        return weight;
    }
}
