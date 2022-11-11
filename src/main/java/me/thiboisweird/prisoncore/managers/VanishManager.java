package me.thiboisweird.prisoncore.managers;

import me.thiboisweird.prisoncore.PrisonCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;

public class VanishManager {
    public static ArrayList<Player> players = new ArrayList<>();
    public static void setVanish(Player player, boolean vanish) {
        if(vanish) {
            player.hidePlayer(PrisonCore.getInstance(), player);
            player.setMetadata("vanished", new FixedMetadataValue(PrisonCore.getInstance(), "true"));
            players.add(player);
        } else {
            player.showPlayer(PrisonCore.getInstance(), player);
            player.removeMetadata("vanished", PrisonCore.getInstance());
            players.remove(player);
        }
        updateVanished();
    }

    public static void toggleVanish(Player player) {
        if(players.contains(player)) {
            player.showPlayer(PrisonCore.getInstance(), player);
            player.removeMetadata("vanished", PrisonCore.getInstance());
            players.remove(player);
        } else {
            player.hidePlayer(PrisonCore.getInstance(), player);
            player.setMetadata("vanished", new FixedMetadataValue(PrisonCore.getInstance(), "true"));
            players.add(player);
        }
        updateVanished();
    }

    public static void updateVanished() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            for(Player vanished : Bukkit.getOnlinePlayers()) {
                if(isVanish(vanished)) {
                    player.hidePlayer(PrisonCore.getInstance(), vanished);
                } else {
                    player.showPlayer(PrisonCore.getInstance(), vanished);
                }
            }
        }
    }

    public static boolean isVanish(Player player) {
        return players.contains(player);
    }
}
