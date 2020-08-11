package be.woutzah.purepunish.commands;

import be.woutzah.purepunish.PurePunish;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    private final String commandName;
    private final boolean canConsoleUse;

    public AbstractCommand(final String commandName, final boolean canConsoleUse, PurePunish plugin) {
        this.commandName = commandName;
        this.canConsoleUse = canConsoleUse;
        plugin.getCommand(commandName).setExecutor(this);
        switch (Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]){
            case "v1_13_R1":
            case "v1_14_R1":
            case "v1_15_R1":
            case "v1_16_R1":
                plugin.getCommand(commandName).setTabCompleter(this);
                break;
            default:
                break;
        }
    }

    public static void registerCommands(PurePunish plugin) {
        new Punish(plugin);
        new PunishReasons(plugin);
        new PunishHistory(plugin);
        new PunishExempt(plugin);
        new PunishReload(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String str, @NotNull String[] args) {
        if (!cmd.getName().equalsIgnoreCase(commandName))
            return true;
        /*if (!sender.hasPermission(permission)) {
            sender.sendMessage("You don't have permission for this.");
            return true;
        }*/
        if (!canConsoleUse && !(sender instanceof Player)) {
            sender.sendMessage("Only players may use this command, sorry!");
            return true;
        }
        try {
            execute(sender, args);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public abstract void execute(CommandSender sender, String[] args) throws ExecutionException, InterruptedException;
}
