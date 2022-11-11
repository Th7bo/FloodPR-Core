package me.thiboisweird.prisoncore.managers;

import me.thiboisweird.prisoncore.misc.Misc;
import me.thiboisweird.prisoncore.PrisonCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DataManager {
    private Player player;

    public DataManager(Player player) {
        this.player = player;
    }

    public Object getData(String val) {
        HashMap<String, Object> data = PrisonCore.playerData.get(player) != null ? PrisonCore.playerData.get(player) : new HashMap<>();
        if(data.get(val) != null) {
            return data.get(val);
        } else {
            return 0;
        }
    }

    public boolean isSet(String val) {
        HashMap<String, Object> dataMap = PrisonCore.playerData.get(player) != null ? PrisonCore.playerData.get(player) : new HashMap<>();
        return dataMap.containsKey(val);
    }

    public void setData(String val, Object data) {
        HashMap<String, Object> dataMap = PrisonCore.playerData.get(player) != null ? PrisonCore.playerData.get(player) : new HashMap<>();
        if(data == null) {
            dataMap.remove(val);
        } else {
            dataMap.put(val, data);
        }
        PrisonCore.playerData.put(player, dataMap);
    }

    public void loadData() {
        Misc.sendMessageP(player, "&dLoading data...");
        //String[] query = Database.queryPlayer(player);
        if (null == null) {
            File file = new File(PrisonCore.getInstance().getDataFolder() + "/players.yml");
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            if(configuration.get("players." + player.getUniqueId().toString()) != null){
                HashMap<String, Object> data = new HashMap<>();
                int max = Objects.requireNonNull(configuration.getConfigurationSection("players." + player.getUniqueId().toString())).getKeys(false).size();
                int current = 0;
                for(String key : Objects.requireNonNull(configuration.getConfigurationSection("players." + player.getUniqueId().toString())).getKeys(false)) {
                    data.put(key, configuration.get("players." + player.getUniqueId().toString() + "." + key));
                    current++;
                    if(current % 5 == 0 || max < 5) {
                        Misc.sendMessageP(player, "&dGetting data... &5" + current + "&d/&5" + max);
                    }
                }
                if(data.size() > 0) {
                    Misc.sendMessageP(player, "&dSwitching data to the new database...");
                    Misc.sendMessageP(player, "&dGetting and saving data...");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Misc.sendMessageP(player, "&dFishes have been saved!");
                            new DataManager(player).setData("caught_fish", data.get("caught_fish"));
                            new DataManager(player).setData("tokens_earned", data.get("tokens_earned"));
                            new DataManager(player).setData("keys_caught", data.get("keys_caught"));
                            configuration.set("players." + player.getUniqueId().toString(), null);
                            try {
                                configuration.save(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Misc.sendMessageP(player, "&dData loaded and cleared!");
                        }
                    }.runTaskLater(PrisonCore.getInstance(), 1);
                }
            }
        }

        else {
//            final String[] q = query;
//            new BukkitRunnable() {
//                @Override
//                public void run() {
//                    ArrayList<String> fishes = new ArrayList<>(Arrays.asList(q[1].split(",,,,")));
//                    PrisonCore.fishes.put(player, fishes);
//                    new DataManager(player).setData("caught_fish", q[2]);
//                    new DataManager(player).setData("tokens_earned", q[3]);
//                    new DataManager(player).setData("keys_caught", q[4]);
//                }
//            }.runTaskAsynchronously(PrisonCore.getInstance());

        }

    }

    public void unloadData() {
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Database.savePlayerData(player, false);
//            }
//        }.runTaskAsynchronously(PrisonCore.getInstance());
        PrisonCore.playerData.remove(player);
    }

    public void saveData() {
//        File file = new File(FishingCore.getInstance().getDataFolder() + "/players.yml");
//        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
//        HashMap<String, Object> data = FishingCore.playerData.get(player) != null ? FishingCore.playerData.get(player) : new HashMap<>();
//        assert data != null;
//        for(String key : data.keySet()) {
//            configuration.set("players." + player.getUniqueId().toString() + "." + key, data.get(key));
//        }
//        configuration.set("players." + player.getUniqueId().toString() + ".caughtFish", getFishes());
//        try {
//            configuration.save(file);
//            FishingCore.info("Saved data for " + player.getName());
//        } catch (Exception e) {
//            FishingCore.error("Failed to save data for " + player.getName() + "!");
//        }


        //Database.savePlayerData(player, false);
    }

    public void saveAllData() {
        PrisonCore.info("Saving all data...");
        Bukkit.broadcastMessage(Misc.Color(PrisonCore.getPrefix() + "{c2}Saving all data..."));
        for(Player playe : PrisonCore.getInstance().getServer().getOnlinePlayers()) {
            this.player = playe;
            saveData();
        }
        Bukkit.broadcastMessage(Misc.Color(PrisonCore.getPrefix() + "{c2}All data saved!"));
        PrisonCore.info("Saved all data!");
    }

    public void saveDataRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                saveAllData();
            }
        }.runTaskTimerAsynchronously(PrisonCore.getInstance(), 0, 5 * 60 * 20);
    }
}
