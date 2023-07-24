package net.byalexius.rewards.helper;

import org.bukkit.ChatColor;

public class ColorHelper {

    public static ChatColor stringToChatColor(String s) {
        switch (s.trim().toUpperCase()) {
            case "AQUA" -> {
                return ChatColor.AQUA;
            }
            case "BLACK" -> {
                return ChatColor.BLACK;
            }
            case "BLUE" -> {
                return ChatColor.BLUE;
            }
            case "DARK_AQUA" -> {
                return ChatColor.DARK_AQUA;
            }
            case "DARK_BLUE" -> {
                return ChatColor.DARK_BLUE;
            }
            case "DARK_GRAY" -> {
                return ChatColor.DARK_GRAY;
            }
            case "DARK_GREEN" -> {
                return ChatColor.DARK_GREEN;
            }
            case "DARK_PURPLE" -> {
                return ChatColor.DARK_PURPLE;
            }
            case "DARK_RED" -> {
                return ChatColor.DARK_RED;
            }
            case "GOLD" -> {
                return ChatColor.GOLD;
            }
            case "GRAY" -> {
                return ChatColor.GRAY;
            }
            case "LIGHT_PURPLE" -> {
                return ChatColor.LIGHT_PURPLE;
            }
            case "MAGIC" -> {
                return ChatColor.MAGIC;
            }
            case "RED" -> {
                return ChatColor.RED;
            }
            case "WHITE" -> {
                return ChatColor.WHITE;
            }
            case "YELLOW" -> {
                return ChatColor.YELLOW;
            }
            default -> {
                return ChatColor.GREEN;
            }
        }
    }

}
