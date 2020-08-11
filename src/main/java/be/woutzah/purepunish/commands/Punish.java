package be.woutzah.purepunish.commands;

import be.woutzah.purepunish.PurePunish;
import be.woutzah.purepunish.database.Punishment;
import be.woutzah.purepunish.managers.ExemptManager;
import be.woutzah.purepunish.managers.PunishmentManager;
import be.woutzah.purepunish.messages.Printer;
import be.woutzah.purepunish.punishtypes.AutoCommand;
import be.woutzah.purepunish.punishtypes.PunishType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;

public class Punish extends AbstractCommand {

    private final PurePunish plugin;
    private final PunishmentManager punishmentManager;
    private final ExemptManager exemptManager;
    private final Printer printer;

    public Punish(PurePunish plugin) {
        super("punish", true, plugin);
        this.plugin = plugin;
        this.punishmentManager = plugin.getPunishmentManager();
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
            Date now = new Date();
            String pattern = "yyyy-MM-dd HH:mm";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
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
            if (args.length < 2) {
                sender.sendMessage(printer.printNoPunishType());
                return;
            }
            String typeArg = args[1].toUpperCase();
            if (args.length < 3) {
                sender.sendMessage(printer.printNoSeverity());
                return;
            }
            if (!(args[2].equalsIgnoreCase("mild") || args[2].equalsIgnoreCase("medium") || args[2].equalsIgnoreCase("severe"))) {
                sender.sendMessage(printer.printNoRightSeverity());
                return;
            }
            String severityArg = args[2];
            String reason;
            if (args.length < 4) {
                reason = printer.printStandardReason()
                        .replace("<type>", args[1].substring(0, 1)
                                .toUpperCase() + args[1].substring(1)
                                .toLowerCase());
            } else {
                reason = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
            }
            if (player != null) {
                if (!exemptManager.checkIfExempted(player.getUniqueId())) {
                    if (punishmentManager.getPunishTypeList().stream().map(PunishType::getTypeName).anyMatch(t -> t.equalsIgnoreCase(typeArg)) ||
                            punishmentManager.getPunishTypeList().stream().map(PunishType::getAliasList).anyMatch(al -> al.contains(typeArg))) {
                        int weight = punishmentManager.calculateWeight(severityArg, typeArg);
                        Punishment punishment = new Punishment(player.getUniqueId().toString(),
                                typeArg, reason, weight,
                                formatter.format(now));
                        PunishType punishType = punishmentManager.getPunishTypeObject(typeArg);
                        int totalWeight = punishmentManager.getTotalWeightPreviousPunishments(player.getUniqueId().toString(),
                                punishType.getTypeName()) + weight;
                        AutoCommand command = punishmentManager.getAutoCommandWithWeight(punishType, totalWeight);
                        if (punishmentManager.addPunishmentForPlayer(punishment)) {
                            sender.sendMessage(printer.printPlayerPunished(player.getName()));
                        } else {
                            sender.sendMessage(printer.printErrorPunished());
                        }
                        OfflinePlayer finalPlayer = player;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                String commandString = command.getCommand();
                                if (sender instanceof Player) {
                                    Player executor = (Player) sender;
                                    commandString = commandString.replace("<uuid>", String.valueOf(executor.getUniqueId()));
                                } else {
                                    commandString = commandString.replace("--sender-uuid=<uuid>", "");
                                }
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandString
                                        .replace("<player>", finalPlayer.getName())
                                        .replace("<reason>", reason)
                                        .replace("<issuer>", sender.getName())
                                        .replace("<scope>", punishType.getScope()));
                            }
                        }.runTask(plugin);
                    } else {
                        sender.sendMessage(printer.printNotExistPunishType());
                    }
                } else {
                    sender.sendMessage(printer.printPlayerExempt());
                }
            } else {
                sender.sendMessage(printer.printNotExistPlayer());
            }
        });
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("punish")
                || command.getName().equalsIgnoreCase("purepunish")) {
            if (sender.hasPermission("purepunish.punishment")) {
                List<String> suggestionList = new ArrayList<>();
                List<String> tempList = new ArrayList<>();
                switch (args.length) {
                    case 1:
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            if (!exemptManager.getAllExemptedPlayers().contains(player.getUniqueId())) {
                                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                                    suggestionList.add(player.getName());
                                }
                            }
                        }
                        Collections.sort(suggestionList);
                        return suggestionList;
                    case 2:
                        punishmentManager.getPunishTypeList().stream()
                                .filter(pt -> pt.getTypeName().toLowerCase().startsWith(args[1].toLowerCase()))
                                .forEach(pt -> suggestionList.add(pt.getTypeName().toLowerCase()));
                        Collections.sort(suggestionList);
                        return suggestionList;
                    case 3:
                        tempList.add("mild");
                        tempList.add("medium");
                        tempList.add("severe");
                        tempList.stream().filter(s -> s.toLowerCase().startsWith(args[2].toLowerCase()))
                                .forEach(suggestionList::add);
                        Collections.sort(suggestionList);
                        return suggestionList;
                    case 4:
                        tempList.add("reason");
                        tempList.stream().filter(s -> s.toLowerCase().startsWith(args[3].toLowerCase()))
                                .forEach(suggestionList::add);
                        Collections.sort(suggestionList);
                        return suggestionList;
                }
            }
        }
        return null;
    }
}
