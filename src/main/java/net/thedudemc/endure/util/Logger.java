package net.thedudemc.endure.util;

import net.thedudemc.endure.Endure;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {

    public static void info(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.DARK_RED);
        sb.append("[").append(Endure.getPluginName()).append("] ");
        sb.append(ChatColor.RESET);
        sb.append(msg);
        Bukkit.getLogger().info(sb.toString());
    }

    public static void error(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.DARK_RED);
        sb.append("[").append(Endure.getPluginName()).append("] ");
        sb.append(ChatColor.RED);
        sb.append(msg);
        Bukkit.getLogger().info(sb.toString());
    }
}
