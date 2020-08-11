package be.woutzah.purepunish.commands;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.managers.ExemptManager;
import be.woutzah.purepunish.messages.Printer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PunishExempt extends AbstractCommand {

    private final PurePunish plugin;
    private final Printer printer;
    private final ExemptManager exemptManager;

    public PunishExempt(PurePunish plugin) {
        super("punishexempt", true, plugin);
        this.plugin = plugin;
        this.exemptManager = plugin.getExemptManager();
        this.printer = plugin.getPrinter();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player executor = (Player) sender;
            if (!sender.hasPermission("purepunish.punishment")) {
                executor.sendMessage(printer.printNoPermission());
                return;
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (args.length < 1) {
                sender.sendMessage(printer.printNoAction());
                return;
            }
            if (!(args[0].equalsIgnoreCase("add")
                    || args[0].equalsIgnoreCase("remove")
                    || args[0].equalsIgnoreCase("list"))) {
                sender.sendMessage(printer.printNotExistAction());
            }
            if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(printer.printExemptList());
                return;
            }
            if (args.length < 2) {
                sender.sendMessage(printer.printNoName());
                return;
            }
            OfflinePlayer player;
            try {
                player = Bukkit.getOfflinePlayer(args[1]);
            } catch (NullPointerException ex) {
                player = null;
            }
            if (player != null) {
                switch (args[0].toLowerCase()) {
                    case "add":
                        if (exemptManager.getAllExemptedPlayers().contains(player.getUniqueId())) {
                            sender.sendMessage(printer.printListAlreadyAdded());
                            return;
                        }
                        exemptManager.addExemptedPlayer(player.getUniqueId());
                        sender.sendMessage(printer.printListAdded(player.getName()));
                        break;
                    case "remove":
                        if (!exemptManager.getAllExemptedPlayers().contains(player.getUniqueId())) {
                            sender.sendMessage(printer.printListCannotRemove());
                            return;
                        }
                        exemptManager.removeExemptedPlayer(player.getUniqueId());
                        sender.sendMessage(printer.printListRemoved(player.getName()));
                        break;
                }
            } else {
                sender.sendMessage(printer.printNotExistPlayer());
            }
        });
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> suggestionList = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("punishexempt")
                || command.getName().equalsIgnoreCase("pexempt")) {
            if (sender.hasPermission("purepunish.exempt.manage")) {
                List<String> tempList = new ArrayList<>();
                switch (args.length) {
                    case 1:
                        tempList.add("add");
                        tempList.add("remove");
                        tempList.add("list");
                        tempList.stream().filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                                .forEach(suggestionList::add);
                        Collections.sort(suggestionList);
                        return suggestionList;
                    case 2:
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (player.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                                suggestionList.add(player.getName());
                            }
                        }
                        Collections.sort(suggestionList);
                        return suggestionList;
                }
            }
        }
        return null;
    }
}
