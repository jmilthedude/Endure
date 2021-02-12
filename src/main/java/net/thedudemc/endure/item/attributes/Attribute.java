package net.thedudemc.endure.item.attributes;

public class Attribute {
    private final double defaultValue;
    private final String attributeName;
    private final String displayName;

    protected Attribute(String attributeName, String displayName, double defaultValue) {
        this.defaultValue = defaultValue;
        this.attributeName = attributeName;
        this.displayName = displayName;

    }

    public double getDefaultValue() {
        return this.defaultValue;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

