package net.thedudemc.endure.item.attributes;

import org.bukkit.ChatColor;

public class Attribute {
    private final double defaultValue;
    private final String attributeName;
    private final String displayName;
    private final ChatColor nameColor;

    protected Attribute(String attributeName, String displayName, double defaultValue) {
        this(attributeName, displayName, ChatColor.WHITE, defaultValue);
    }

    protected Attribute(String attributeName, String displayName, ChatColor nameColor, double defaultValue) {
        this.defaultValue = defaultValue;
        this.attributeName = attributeName;
        this.displayName = displayName;
        this.nameColor = nameColor;
    }

    public double getDefaultValue() {
        return this.defaultValue;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public String getDisplayName() {
        return this.nameColor + displayName + ChatColor.RESET;
    }
}

