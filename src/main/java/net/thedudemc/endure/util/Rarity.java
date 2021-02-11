package net.thedudemc.endure.util;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON(ChatColor.GRAY),
    UNCOMMON(ChatColor.BLUE),
    RARE(ChatColor.LIGHT_PURPLE),
    EPIC(ChatColor.GOLD);

    private ChatColor color;

    Rarity(ChatColor color) {
        this.color = color;
    }

    public ChatColor getColor() { return this.color; }
}
