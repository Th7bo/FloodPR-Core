package me.thiboisweird.prisoncore.misc;

import dev.dbassett.skullcreator.SkullCreator;
import me.thiboisweird.prisoncore.PrisonCore;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Misc {

    public static String translateHexCodes(String textToTranslate) {
        Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-fA-F])");

        Matcher matcher = HEX_PATTERN.matcher(textToTranslate);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString().toLowerCase(Locale.ROOT));
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());

    }

    public static String Color(String s) {
        return translateHexCodes(s);
    }

    public static String[] Color(String[] s){
        ArrayList<String> strings = new ArrayList<>();
        for(String string : s){
            strings.add(Color(string));
        }
        return strings.toArray(new String[0]);
    }

    public static void sendFancyTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut, int space) {
        player.sendTitle(Color(title), Color(subtitle), fadeIn, stay, fadeOut);
    }

    public static void sendFancyTitle(ArrayList<Player> player, String title, String subtitle, int fadeIn, int stay, int fadeOut, int space) {
        for(Player p : player) {
            p.sendTitle(Color(title), Color(subtitle), fadeIn, stay, fadeOut);
        }
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(Misc.Color(title), Misc.Color(subtitle), fadeIn, stay, fadeOut);
    }

    public static ImageMessage createPlayerHead(Player p) throws IOException {
        BufferedImage imageToSend = ImageIO.read(new URL("https://minotar.net/helm/" + p.getName() + "/100.png"));
        return new ImageMessage(
                imageToSend, // the bufferedimage to send
                8, // the image height
                ImageChar.BLOCK.getChar() // the character that the image is made of
        );
    }

    public static void sendActionbar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Misc.Color(message)));
    }

    public static String format(Double s, int decimals) {
        return String.format("%,." + decimals + "f",s);
    }
    public static String format(int s, int decimals) {
        return String.format("%,." + decimals + "f",s);
    }

    public static void sendMessage(Player player, String message) {
        if(player == null){
            Bukkit.getConsoleSender().sendMessage(Misc.Color(message));
        } else {
            player.sendMessage(Misc.Color(message));
        }
    }
    public static void sendError(Player player, String message) {
        if (player != null) {
            player.sendMessage(Color("&c&lERROR&8 • &c" + message));
        } else {
            Bukkit.getConsoleSender().sendMessage(Color(message));
        }
    }
    public static void sendError(CommandSender player, String message) {
        player.sendMessage(Color("&c&lERROR&8 • &c" + message));
    }
    public static void sendMessageP(CommandSender player, String message) {
        message = PrisonCore.getPrefix() + message;
        player.sendMessage(Color(message));
    }

    public static String getCenteredMessage(String message, int PX){
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb.toString() + message;
    }
    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(Color(message));
    }
    public static void sendCError(String message) {
        Bukkit.getConsoleSender().sendMessage(Color("&c&lERROR&8 • &c" + message));
    }

    public static void sendMessageP(Player player, String message) {
        message = PrisonCore.getPrefix() + message;
        if (player != null) {
            player.sendMessage(Color(message));
        } else {
            Bukkit.getConsoleSender().sendMessage(Color(message));
        }
    }
    public static void sendCMessageP(String message) {
        message = PrisonCore.getPrefix() + message;
        Bukkit.getConsoleSender().sendMessage(Color(message));
    }
    public static void sendMessage(Player player, String message, int amount) {
        for(int i = 0; i < amount; i++) {
            if (player != null) {
                player.sendMessage(Color(message));
            } else {
                Bukkit.getConsoleSender().sendMessage(Color(message));
            }
        }
    }

    public static boolean isInBound(Player p, Location loc, Location loc2) {
        double x1 = Math.min(loc.getX(), loc2.getX());
        double x2 = Math.max(loc.getX(), loc2.getX());

        double y1 = Math.min(loc.getY(), loc2.getY());
        double y2 = Math.max(loc.getY(), loc2.getY());

        double z1 = Math.min(loc.getZ(), loc2.getZ());
        double z2 = Math.max(loc.getZ(), loc2.getZ());

        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();

        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

    public static int getEnchLevel(ItemStack item, String key) {
        int level = item.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getByName(key)));
        if(level == 0) {
            return 0;
        }
        return level;
    }

    public static void setEnchLevel(ItemStack item, String key, String value) {
        item.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(key)), Integer.parseInt(value));
    }

    public static boolean chanceOf(int chance) {
        if(chance == 0) return false;
        int random = (int) (Math.random() * 100);
        return random <= chance;
    }
    public static boolean chanceOf(float chance) {
        if(chance == 0) return false;
        int random = (int) (Math.random() * 100);
        return random <= chance;
    }
    public static boolean chanceOf(double chance) {
        if(chance == 0) return false;
        int random = (int) (Math.random() * 100);
        return random <= chance;
    }


    public static double getRandomDouble(double min, double max) {
        final double ll = Math.min(min, max);
        final double uu = Math.max(min, max);
        Random r = new Random();
        return ll + r.nextDouble() * (uu - ll);
    }

    public static int getRandomInt(int min, int max) {
        final int ll = Math.min(min, max);
        final int uu = Math.max(min, max);
        Random r = new Random();
        return ll + r.nextInt() * (uu - ll);
    }

    public static String serialiseLocation(Location location) {
        return Objects.requireNonNull(location.getWorld()).getName() + ";" + Math.floor(location.getX()) + ";" + Math.floor(location.getY()) + ";" + Math.floor(location.getZ());
    }

    public static Location deserialiseLocation(String location) {
        String[] loc = location.replace(" ", "").split(";");
        return new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]));
    }

    public static String serialiseItemStack(ItemStack item, @Nullable String base64) {
        ArrayList<String> lores = new ArrayList<>(Objects.requireNonNull(item.getItemMeta().getLore()));
        StringBuilder returning = new StringBuilder();
        for(String s : lores) {
            returning.append(s).append("``");
        }
        if(base64 == null) {
            base64 = "";
        }
        return item.getType().name() + ";" + item.getItemMeta().getDisplayName() + ";" + returning + ";" + base64;
    }

    public static ItemStack deserialiseItemStack(String item) {
        item = item.replace("]", "").replace("[", "");
        String[] itemData = item.split(";");
        ItemStack itemStack = new ItemStack(Material.valueOf(itemData[0]));
        if(itemStack.getType() == Material.PLAYER_HEAD) {
            itemStack = SkullCreator.itemFromBase64(itemData[3]);
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemData[1]);
        ArrayList<String> lore = new ArrayList<>(Arrays.asList(itemData[2].split("``")));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static String getTimeShort(long time) {
        int months = (int) (time / (30 * 24 * 60 * 60));
        int weeks = (int) ((time % (30 * 24 * 60 * 60)) / (7 * 24 * 60 * 60));
        int days = (int) ((time % (30 * 24 * 60 * 60)) / (24 * 60 * 60));
        int hours = (int) ((time % (24 * 60 * 60)) / (60 * 60));
        int minutes = (int) ((time % (60 * 60)) / 60);
        int seconds = (int) (time % 60);
        String timeString = "";
        if(months > 0) {
            timeString += months + "mo";
        }
        if(weeks > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += weeks + "w";
        }
        if(days > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += days + "d";
        }
        if(hours > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += hours + "h";
        }
        if(minutes > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += minutes + "m";
        }
        if(seconds > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += seconds + "s";
        }
        return timeString;
    }

    public static String getTime(int time){
        int months = time / (30 * 24 * 60 * 60);
        int weeks = (time % (30 * 24 * 60 * 60)) / (7 * 24 * 60 * 60);
        int days = (time % (30 * 24 * 60 * 60)) / (24 * 60 * 60);
        int hours = (time % (24 * 60 * 60)) / (60 * 60);
        int minutes = (time % (60 * 60)) / 60;
        int seconds = time % 60;
        String timeString = "";
        if(months > 0) {
            timeString += months + " month";
            if(months > 1) {
                timeString += "s";
            }
        }
        if(weeks > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += weeks + " week";
            if(weeks > 1) {
                timeString += "s";
            }
        }
        if(days > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += days + " day";
            if(days > 1) {
                timeString += "s";
            }
        }
        if(hours > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";

            }
            timeString += hours + " hour";
            if(hours > 1) {
                timeString += "s";
            }
        }
        if(minutes > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += minutes + " minute";
            if(minutes > 1) {
                timeString += "s";

            }
        }
        if(seconds > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += seconds + " second";
            if(seconds > 1) {
                timeString += "s";
            }
        }
        return timeString;
    }

    public static long getTimeRemaining(long start, long time) {
        long millisnow = System.currentTimeMillis()/1000;
        long millis = millisnow - start;
        return time - millis;
    }

    public static String getTime(long time){
        int months = (int) (time / (30 * 24 * 60 * 60));
        int weeks = (int) ((time % (30 * 24 * 60 * 60)) / (7 * 24 * 60 * 60));
        int days = (int) ((time % (30 * 24 * 60 * 60)) / (24 * 60 * 60));
        int hours = (int) ((time % (24 * 60 * 60)) / (60 * 60));
        int minutes = (int) ((time % (60 * 60)) / 60);
        int seconds = (int) (time % 60);
        String timeString = "";
        if(months > 0) {
            timeString += months + " month";
            if(months > 1) {
                timeString += "s";
            }
        }
        if(weeks > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += weeks + " week";
            if(weeks > 1) {
                timeString += "s";
            }
        }
        if(days > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += days + " day";
            if(days > 1) {
                timeString += "s";
            }
        }
        if(hours > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";

            }
            timeString += hours + " hour";
            if(hours > 1) {
                timeString += "s";
            }
        }
        if(minutes > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += minutes + " minute";
            if(minutes > 1) {
                timeString += "s";

            }
        }
        if(seconds > 0) {
            if(!timeString.isEmpty()) {
                timeString += ", ";
            }
            timeString += seconds + " second";
            if(seconds > 1) {
                timeString += "s";
            }
        }
        return timeString;
    }

}
