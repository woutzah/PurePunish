package be.woutzah.purepunish.commands.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class CommandListener implements Listener {

    public CommandListener() {

    }

    @EventHandler
    public void commandSend(PlayerCommandSendEvent event) {
        switch (Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]){
            case "v1_13_R1":
            case "v1_14_R1":
            case "v1_15_R1":
            case "v1_16_R1":
                if (!event.getPlayer().hasPermission("purepunish.punishment")) {
                    event.getCommands().remove("punish");
                    event.getCommands().remove("purepunish");
                }
                if (!event.getPlayer().hasPermission("purepunish.punishmenthistory")) {
                    event.getCommands().remove("punishhistory");
                    event.getCommands().remove("phistory");
                }
                if (!event.getPlayer().hasPermission("purepunish.punishmentreasons")) {
                    event.getCommands().remove("punishreasons");
                    event.getCommands().remove("preasons");
                }
                if (!event.getPlayer().hasPermission("purepunish.exempt.manage")) {
                    event.getCommands().remove("punishexempt");
                    event.getCommands().remove("pexempt");
                }
                if (!event.getPlayer().hasPermission("purepunish.reload")) {
                    event.getCommands().remove("punishreload");
                    event.getCommands().remove("preload");
                }
                break;
            default:
                break;
        }
    }
}
