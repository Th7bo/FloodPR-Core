package me.thiboisweird.prisoncore.commands;

import me.thiboisweird.prisoncore.PrisonCore;
import me.thiboisweird.prisoncore.misc.Misc;
import me.thiboisweird.prisoncore.misc.listeners.ClientBrand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClientCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("staff")) {
            Misc.sendError(sender, "You do not have permission to use this command!");
            return true;
        }
        if(args.length < 1) {
            Misc.sendError(sender, "Invalid argument!");
            return true;
        } else if (args.length == 1) {
            Player p = Bukkit.getPlayer(args[0]);
            if(p == null) {
                Misc.sendError(sender, "Player not found!");
                return true;
            }
            String client = PrisonCore.clients.get(p.getUniqueId());
            String brand = PrisonCore.brands.get(p.getUniqueId());
            String message = Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aInfo&7) &a" + p.getName() + "&7 is playing on &a" + brand + "&7 (&a" + client + "&7)");
            Misc.sendMessage(sender, message);
            return true;
        } else {
            String arg = args[1];
            Player p = Bukkit.getPlayer(args[0]);
            if(p == null) {
                Misc.sendError(sender, "Player not found!");
                return true;
            }
            if (arg.equalsIgnoreCase("client")) {
                String client = PrisonCore.clients.get(p.getUniqueId());
                String message = Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aClient&7) &a" + p.getName() + "&7 is playing on &a" + client);
                Misc.sendMessage(sender, message);
                return true;
            } else if (arg.equalsIgnoreCase("brand")) {
                String brand = PrisonCore.brands.get(p.getUniqueId());
                String message = Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aBrand&7) &a" + p.getName() + "&7 is playing on &a" + brand);
                Misc.sendMessage(sender, message);
                return true;
            } else {
                Misc.sendError(sender, "Invalid argument!");
                return true;
            }
        }
    }
}
