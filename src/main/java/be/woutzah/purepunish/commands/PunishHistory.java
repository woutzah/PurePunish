package be.woutzah.purepunish.commands;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.commands.history.HistoryCreator;
import be.woutzah.purepunish.commands.history.HistoryPage;
import be.woutzah.purepunish.managers.PunishmentManager;
import be.woutzah.purepunish.messages.Printer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PunishHistory extends AbstractCommand {

    private final PurePunish plugin;
    private final Printer printer;
    private final PunishmentManager punishmentManager;

    public PunishHistory(PurePunish plugin) {
        super("punishhistory", true, plugin);
        this.plugin = plugin;
        this.printer = plugin.getPrinter();
        this.punishmentManager = plugin.getPunishmentManager();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player executor = (Player) sender;
            if (!executor.hasPermission("purepunish.punishmenthistory")) {
                executor.sendMessage(printer.printNoPermission());
                return;
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (args.length < 1) {
                sender.sendMessage(printer.printNoName());
                return;
            }
            OfflinePlayer player;
            try {
                player = Bukkit.getOfflinePlayer(args[0]);
            } catch (NullPointerException ex) {
                player = null;
            }
            int pageNumber = 0;
            if (args.length < 2) {
                pageNumber = 1;
            } else if (!isNumeric(args[1])) {
                sender.sendMessage(printer.printNoNumber(args[1]));
                return;
            } else {
                pageNumber = Integer.parseInt(args[1]);
            }
            if (player != null) {
                HistoryCreator hc = new HistoryCreator(plugin, player.getName());
                if (!hc.createHistoryPages(punishmentManager.getAllPunishmentsForPlayer(player.getUniqueId()))) {
                    sender.sendMessage(printer.printNoPunishments(player.getName()));
                    return;
                }
                if (pageNumber > hc.getHistoryPageList().size()) {
                    sender.sendMessage(printer.printNotExistPage());
                    return;
                }
                HistoryPage hp = hc.getHistoryPageWithNumber(pageNumber);
                TextComponent previous = null;
                TextComponent next = null;
                TextComponent separator;
                sender.sendMessage(hp.printPage());
                if (!(hc.getAmountOfPages() == pageNumber)) {
                    next = new TextComponent("§3Next");
                    next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punishhistory " + player.getName() + " " + (pageNumber + 1)));
                }
                if ((pageNumber - 1) != 0) {
                    previous = new TextComponent("§3Previous");
                    previous.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/punishhistory " + player.getName() + " " + (pageNumber - 1)));
                }
                if (previous == null) {
                    if (next != null) {
                        sender.spigot().sendMessage(next);
                        return;
                    }
                }
                if (next == null) {
                    if (previous != null) {
                        sender.spigot().sendMessage(previous);
                        return;
                    }
                }
                separator = new TextComponent(" §f§l| ");
                if (previous != null) {
                    sender.spigot().sendMessage(previous, separator, next);
                }
            } else {
                sender.sendMessage(printer.printNotExistPlayer());
            }
        });
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("punishhistory")
                || command.getName().equalsIgnoreCase("phistory")) {
            if (args.length == 1) {
                if (sender.hasPermission("purepunish.punishmenthistory")) {
                    List<String> suggestionList = new ArrayList<>();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
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

