package me.thiboisweird.prisoncore.misc;

import me.thiboisweird.prisoncore.PrisonCore;
import me.thiboisweird.prisoncore.managers.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Placeholders extends PlaceholderExpansion {

    private final PrisonCore plugin; // This instance is assigned in canRegister()

    public Placeholders(PrisonCore prisonCore) {
        this.plugin = prisonCore;
    }

    @Override
    public String getAuthor() {
        return "ThiboIsEpic";
    }

    @Override
    public String getIdentifier() {
        return "prisoncorej";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("event")){
            return EventManager.getCurrentEvent();
        } else if(params.contains("center_")) {
            String[] split = params.split("_");
            return Misc.getCenteredMessage(split[2], Integer.parseInt(split[1]));
        } else if(params.equalsIgnoreCase("nextevent")) {
            if(EventManager.isEventOngoing()) {
                String time_left = ": " + EventManager.getTimeLeftFormatted();
                return Misc.Color("&f" + EventManager.getCurrentEvent() + "" + time_left);
            } else {
                return Misc.Color("&f" + EventManager.getTimeLeftNewEventFormatted());
            }
        } else if(params.equalsIgnoreCase("eventstatus")) {
            if(EventManager.isEventOngoing()) {
                return "Current Event";
            } else {
                return "Next Event";
            }
        } else if(params.equalsIgnoreCase("version")) {
            return PrisonCore.getVersion();
        } else if (params.equalsIgnoreCase("online")) {
            int i = 0;
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.hasPermission("visible")) {
                    i += 1;
                }
            }
            return i + "";

        }


        return null; // Placeholder is unknown by the expansion
    }
}