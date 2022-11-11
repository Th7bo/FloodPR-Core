package me.thiboisweird.prisoncore.commands;

import me.thiboisweird.prisoncore.PrisonCore;
import me.thiboisweird.prisoncore.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TabCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("prisoncore.tab")) {
            Misc.sendError(player, "You do not have permission to use this command!");
        } else {
            if(args.length == 0) {
                if(!PrisonCore.getTabType()) {
                    PrisonCore.setTabType(true);
                    Misc.sendMessage(player, "&7Custom tablist &cdisabled&7!");
                } else {
                    PrisonCore.setTabType(false);
                    Misc.sendMessage(player, "&7Custom tablist &aenabled&7!");
                }
            } else {
                if(args[0].equalsIgnoreCase("enable")) {
                    PrisonCore.setTabType(false);
                    Misc.sendMessage(player, "&7Custom tablist &aenabled&7!");
                } else if(args[0].equalsIgnoreCase("disable")) {
                    PrisonCore.setTabType(true);
                    Misc.sendMessage(player, "&7Custom tablist &cdisabled&7!");
                } else {
                    Misc.sendError(player, "Invalid argument!");
                }
            }
        }
        return true;
    }
}
