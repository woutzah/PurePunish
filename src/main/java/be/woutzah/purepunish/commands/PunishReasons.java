package be.woutzah.purepunish.commands;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.messages.Printer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PunishReasons extends AbstractCommand {

    private final Printer printer;

    public PunishReasons(PurePunish plugin) {
        super("punishreasons", true, plugin);
        this.printer = plugin.getPrinter();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player executor = (Player) sender;
            if (!executor.hasPermission("purepunish.punishmentreasons")) {
                executor.sendMessage(printer.printNoPermission());
                return;
            }
        }
        sender.sendMessage(printer.printReasons());
    }


    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
