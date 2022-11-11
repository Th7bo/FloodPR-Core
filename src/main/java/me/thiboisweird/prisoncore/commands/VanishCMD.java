package me.thiboisweird.prisoncore.commands;

import me.thiboisweird.prisoncore.managers.VanishManager;
import me.thiboisweird.prisoncore.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("prisoncore.vanish")) {
            Misc.sendFancyTitle(player, "&#7047D1VANISH", "&cYou do not have permission to use this command!", 10, 100, 20, 4);
            return true;
        }
        if(args.length == 0) {
            Misc.sendFancyTitle(player, "&#7047D1VANISH", "&aYou toggled your vanish!", 10, 100, 20, 4);
            VanishManager.toggleVanish(player);
            return true;
        }
        if(args[0].equalsIgnoreCase("on")) {
            Misc.sendFancyTitle(player, "&#7047D1VANISH", "&aYou are now vanished!", 10, 100, 20, 4);
            VanishManager.setVanish(player, true);
            return true;
        }
        else if(args[0].equalsIgnoreCase("off")) {
            Misc.sendFancyTitle(player, "&#7047D1VANISH", "&aYou are no longer vanished!", 10, 100, 20, 4);
            VanishManager.setVanish(player, false);
            return true;
        }
        else {
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                Misc.sendFancyTitle(player, "&#7047D1VANISH", "&cUsage: /vanish <on/off/player>", 10, 100, 20, 4);
                return true;
            } else {
                if(VanishManager.isVanish(target)) {
                    Misc.sendFancyTitle(player, "&#7047D1VANISH", "&a" + target.getName() + " is now visible!", 10, 100, 20, 4);
                    VanishManager.setVanish(target, false);
                } else {
                    Misc.sendFancyTitle(player, "&#7047D1VANISH", "&a" + target.getName() + " is now vanished!", 10, 100, 20, 4);
                    VanishManager.setVanish(target, true);
                }
            }
            return true;
        }
    }
}
