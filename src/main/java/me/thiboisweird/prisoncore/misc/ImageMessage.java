package me.thiboisweird.prisoncore.misc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageMessage {
    private final static char TRANSPARENT_CHAR = ' ';

    private String[] lines;

    public ImageMessage(BufferedImage image, int height, char imgChar) {
        String[][] chatColors = toChatColorArray(image, height);
        lines = toImgMessage(chatColors, imgChar);
    }

    public ImageMessage appendText(String... text) {
        for (int y = 0; y < lines.length; y++) {
            if (text.length > y) {
                lines[y] += " " + text[y];
            }
        }
        return this;
    }

    private String[][] toChatColorArray(BufferedImage image, int height) {
        double ratio = (double) image.getHeight() / image.getWidth();
        int width = (int) (height / ratio);
        if (width > 10) width = 10;
        BufferedImage resized = resizeImage(image, (int) (height / ratio), height);

        String[][] chatImg = new String[resized.getWidth()][resized.getHeight()];
        for (int x = 0; x < resized.getWidth(); x++) {
            for (int y = 0; y < resized.getHeight(); y++) {
                int rgb = resized.getRGB(x, y);
                String hex = RGBToHex(new Color(rgb).getRed(), new Color(rgb).getGreen(), new Color(rgb).getBlue());
                chatImg[x][y] = Misc.translateHexCodes(hex);

            }
        }
        return chatImg;
    }


    private String RGBToHex(int red, int green, int blue) {
        return "&" + String.format("#%02x%02x%02x", red, green, blue);
    }

    private String[] toImgMessage(String[][] colors, char imgchar) {
        String[] lines = new String[colors[0].length];
        for (int y = 0; y < colors[0].length; y++) {
            String line = "";
            for (int x = 0; x < colors.length; x++) {
                String color = colors[x][y];
                line += (color != null) ? colors[x][y].toString() + imgchar : TRANSPARENT_CHAR;
            }
            lines[y] = line + ChatColor.RESET;
        }
        return lines;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        AffineTransform af = new AffineTransform();
        af.scale(
                width / (double) originalImage.getWidth(),
                height / (double) originalImage.getHeight());

        AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(originalImage, null);
    }

    public void sendToPlayer(Player player) {
        for (String line : lines) {
            Misc.sendMessage(player, line);
        }
    }
}