package net.thedudemc.endure.item.attributes;

public class Attribute {
    private final double defaultValue;
    private final String attributeName;

    protected Attribute(String attributeName, double defaultValue) {
        this.defaultValue = defaultValue;
        this.attributeName = attributeName;
    }

    public double getDefaultValue() {
        return this.defaultValue;
    }

    public String getAttributeName() {
        return this.attributeName;
    }
}
