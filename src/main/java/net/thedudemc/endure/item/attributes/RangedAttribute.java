package net.thedudemc.endure.item.attributes;

public class RangedAttribute extends Attribute {
    private final double minimumValue;
    private final double maximumValue;

    public RangedAttribute(String attributeName, String displayName, double defaultValue, double minimumValue, double maximumValue) {
        super(attributeName, displayName, defaultValue);
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        } else if (defaultValue < minimumValue) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        } else if (defaultValue > maximumValue) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }

    public double clampValue(double value) {
        return this.clamp(value, this.minimumValue, this.maximumValue);
    }

    private double clamp(double num, double min, double max) {
        if (num < min) {
            return min;
        } else {
            return Math.min(num, max);
        }
    }
}
