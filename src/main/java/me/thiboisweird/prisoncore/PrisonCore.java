package me.thiboisweird.prisoncore;

import com.github.retrooper.packetevents.PacketEvents;
import com.keenant.tabbed.Tabbed;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import me.thiboisweird.prisoncore.commands.*;
import me.thiboisweird.prisoncore.managers.ConfigManager;
import me.thiboisweird.prisoncore.managers.EventManager;
import me.thiboisweird.prisoncore.misc.listeners.ClientBrand;
import me.thiboisweird.prisoncore.misc.Misc;
import me.thiboisweird.prisoncore.misc.Placeholders;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class PrisonCore extends JavaPlugin {


    private static PrisonCore instance;
    private final boolean dev = this.getDescription().getVersion().endsWith("-dev");
    private final String version = this.getDescription().getVersion();
    public static HashMap<UUID, String> brands = new HashMap<>();
    public static HashMap<UUID, String> clients = new HashMap<>();

    private static Tabbed tabbed = null;
    private static LuckPerms api = null;
    private static boolean useNormalTab = false;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        //Are all listeners read only?
        PacketEvents.getAPI().getSettings().readOnlyListeners(true)
                .checkForUpdates(true)
                .bStats(true);
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        instance = this;
        if (this.isDev()) {
            PrisonCore.warning("Development build detected, if this is not intended, please report this on the github.");
        }
        Bukkit.getConsoleSender().sendMessage(Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aVersion&7) &aDetected Version &c" + version));
        Bukkit.getConsoleSender().sendMessage(Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aVersion&7) &aLoading settings for Version &c" + version));

        Bukkit.getConsoleSender().sendMessage(Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aProtocol&7) &aLoading protocol..."));
        PacketEvents.getAPI().getEventManager().registerListener(new ClientBrand());
        PacketEvents.getAPI().init();

        Bukkit.getConsoleSender().sendMessage(Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aConfig&7) &aLoading config(s)..."));
        saveDefaultConfig();
        getCommand("vanish").setExecutor(new VanishCMD());
        getCommand("client").setExecutor(new ClientCMD());

        EventManager.startRandomEventRunnable();


        if(Bukkit.getServer().getPluginManager().isPluginEnabled("ViaVersion")){
            Bukkit.getConsoleSender().sendMessage(Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aViaVersion&7) &aDetected ViaVersion " + Bukkit.getPluginManager().getPlugin("ViaVersion").getDescription().getVersion()));
        }

        if(Bukkit.getServer().getPluginManager().isPluginEnabled("ProtocolLib")){
            Bukkit.getConsoleSender().sendMessage(Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aProtocolLib&7) &aDetected ProtocolLib " + Bukkit.getPluginManager().getPlugin("ProtocolLib").getDescription().getVersion()));
            useNormalTab = false;
            tabbed = new Tabbed(this);
        } else {
            useNormalTab = true;
        }

        if(Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")){
            Bukkit.getConsoleSender().sendMessage(Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aPlaceholderAPI&7) &aDetected PlaceholderAPI " + Bukkit.getPluginManager().getPlugin("PlaceholderAPI").getDescription().getVersion()));
            new Placeholders(this).register();
        }
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }
//        MinesManager.setupMineData();

        Bukkit.getConsoleSender().sendMessage(Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 &aStarted!"));
    }

    public static String getVersion(){
        return instance.getDescription().getVersion();
    }
    public static String getPrefix() {
        return Objects.requireNonNull(ConfigManager.getValue("prefix")).toString() != null ? PrisonCore.getInstance().getConfig().getString("prefix") + " &8• " : "&d&lPRISON &8- ";
    }
    public static String getPrefixS() {
        return Objects.requireNonNull(ConfigManager.getValue("prefix")).toString() != null ? PrisonCore.getInstance().getConfig().getString("prefix") : "&d&lPRISON";
    }
    public static String getError() {
        return Objects.requireNonNull(ConfigManager.getValue("color_error")).toString() != null ? PrisonCore.getInstance().getConfig().getString("color_error") : "&c";
    }
    public static String getColor_1() {
        return Objects.requireNonNull(ConfigManager.getValue("color_1")).toString() != null ? PrisonCore.getInstance().getConfig().getString("color_1") : "&7";
    }
    public static String getColor_2() {
        return Objects.requireNonNull(ConfigManager.getValue("color_2")).toString() != null ? PrisonCore.getInstance().getConfig().getString("color_2") : "&d";
    }
    public static String getColor_3() {
        return Objects.requireNonNull(ConfigManager.getValue("color_3")).toString() != null ? PrisonCore.getInstance().getConfig().getString("color_3") : "&5";
    }

    @Override
    public void onDisable() {
        if(Bukkit.getOnlinePlayers().size() > 0) {
            //new DataManager(null).saveAllData();
        }
        PacketEvents.getAPI().terminate();
        instance = null;
    }

    public static void info(String log) {
        instance.getLogger().info(log);
    }

    public static void warning(String warning) {
        instance.getLogger().warning(warning);
    }

    public static void error(String error) {
        instance.getLogger().severe(error);
    }

    public static PrisonCore getInstance() {
        return instance;
    }
    public static Tabbed getTabbed() {
        return tabbed;
    }
    public static boolean isTabbed() {
        return tabbed != null;
    }

    public static boolean getTabType() { return useNormalTab; }

    public static void setTabType(boolean useNormalTab) {
        PrisonCore.useNormalTab = useNormalTab;
    }

    public static LuckPerms getLuckPerms() {
        return api;
    }

    public static boolean isLuckPerms() {
        return api != null;
    }

    public boolean isDev() {
        return dev;
    }
}
