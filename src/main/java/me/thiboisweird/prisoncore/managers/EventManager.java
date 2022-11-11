package me.thiboisweird.prisoncore.managers;

import me.thiboisweird.prisoncore.PrisonCore;
import me.thiboisweird.prisoncore.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class EventManager {

    private static String current_event;
    private static boolean event_ongoing;
    private static long event_start;
    private static long event_end;

    public EventManager() {
        current_event = "None";
        event_ongoing = false;
    }

    public static void setCurrentEvent(String event) {
        current_event = event;
        event_ongoing = true;
        event_start = System.currentTimeMillis()/1000;
    }

    public static long getEventStart() {
        return event_start;
    }

    public static long getTimeLeft() {
        return Misc.getTimeRemaining(event_start, 180);
    }

    public static String getTimeLeftFormatted() {
        return Misc.getTimeShort(Misc.getTimeRemaining(event_start, 180));
    }

    public static String getTimeLeftNewEventFormatted() {
        return Misc.getTimeShort(Misc.getTimeRemaining(event_end, 27 * 60));
    }

    public static String getCurrentEvent() {
        return current_event;
    }

    public static boolean isEventOngoing() {
        return event_ongoing;
    }

    public static void setEventOngoing(boolean event_ongoing) {
        EventManager.event_ongoing = event_ongoing;
    }
    public static void stopEvent() {
        EventManager.event_ongoing = false;
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Misc.sendFancyTitle(players, "&#7047D1EVENT", "&cThe current event has ended!", 10, 100, 20, 4);
        EventManager.current_event = "None";
        EventManager.event_end = System.currentTimeMillis()/1000;
    }

    public static void setRandomEvent() {
        ArrayList<String> random_events = new ArrayList<>();
        random_events.add("Mining Boost");
        random_events.add("Selling Boost");
        random_events.add("Fishing Boost");

        int random_event = (int) (Math.random() * random_events.size());
        setCurrentEvent(random_events.get(random_event));
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Misc.sendFancyTitle(players, "&#7047D1EVENT", "&aThe " + random_events.get(random_event) +" has started!", 10, 100, 20, 4);
    }

    public static void startRandomEventRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                setRandomEvent();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        stopEvent();
                    }
                }.runTaskLater(PrisonCore.getInstance(), 20 * 60 * 3);
            }
        }.runTaskTimerAsynchronously(PrisonCore.getInstance(), 0, 20 * 60 * 30);
    }

}
