package me.thiboisweird.prisoncore.misc.listeners;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import me.thiboisweird.prisoncore.PrisonCore;
import me.thiboisweird.prisoncore.misc.Misc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class ClientBrand extends PacketListenerAbstract {
    String brand = "vanilla";
    String client = "1.18";
    public ClientBrand() {
        super(PacketListenerPriority.LOW);
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.PLUGIN_MESSAGE) {
            WrapperPlayClientPluginMessage packet = new WrapperPlayClientPluginMessage(event);
            User user = event.getUser();
            String channelName = packet.getChannelName();
            if (channelName.equalsIgnoreCase("minecraft:brand") || // 1.13+
                    packet.getChannelName().equals("MC|Brand")) { // 1.12
                byte[] data = packet.getData();

                if (data.length > 64 || data.length == 0) {
                    brand = "sent " + brand.length() + " bytes as brand";
                } else {
                    byte[] minusLength = new byte[data.length - 1];
                    System.arraycopy(data, 1, minusLength, 0, minusLength.length);

                    brand = new String(minusLength).replace(" (Velocity)", ""); //removes velocity's brand suffix
                    String client = user.getClientVersion().getReleaseName();
                    this.client = client;
                    UUID uuid = user.getUUID();
                    PrisonCore.clients.put(uuid, client);
                    PrisonCore.brands.put(uuid, brand);
                    String message = Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aBrand&7) &a" + user.getName() + " &7has joined with &a" + brand + "&7 (&a" + client + "&7)");
                    if(!brand.contains("vanilla")) {
                        user.sendMessage(Misc.Color("&#ADF3FD&lꜰʟᴏᴏᴅ&#2AB7AB&lᴘʀ &8•&7 (&aɪɴꜰᴏ&7) &fᴅᴜᴇ ᴛᴏ ɴᴏᴛ ʙᴇɪɴɢ ᴏɴ ᴠᴀɴɪʟʟᴀ, ʏᴏᴜ ᴍɪɢʜᴛ ʜᴀᴠᴇ ᴀ ᴍᴏᴅ ᴛʜᴀᴛ ꜱʜᴏᴡꜱ ɴᴀᴍᴇᴛᴀɢꜱ ᴀɴᴅ ᴛʜᴜꜱ ᴛʜᴇ ɴᴀᴍᴇᴛᴀɢꜱ ᴍɪɢʜᴛ ʟᴏᴏᴋ ᴡᴇɪʀᴅ"));
                    }
                    // sendMessage is async safe while broadcast isn't due to adventure
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.hasPermission("staff")) {
                            player.sendMessage(message);
                        }
                    }
                    Bukkit.getConsoleSender().sendMessage(message);

                }
            }
        }
    }

    public String getBrand() {
        return brand;
    }

    public String getClient() {
        return client;
    }
}