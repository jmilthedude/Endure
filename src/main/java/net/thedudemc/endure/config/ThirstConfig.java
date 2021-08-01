package net.thedudemc.endure.config;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;

import java.util.HashMap;

public class ThirstConfig extends Config {

    @Expose private boolean tickWhileStanding;
    @Expose private int tickInterval;
    @Expose private double percentTickInterval;
    @Expose private double blockDistanceInterval;
    @Expose private double percentBlockDistanceInterval;
    @Expose private double percentThirstPerWaterBottle;
    @Expose private int thirstDamageInterval;
    @Expose private double thirstDamageAmount;
    @Expose private HashMap<String, Double> biomeModifiers;

    @Override
    public String getName() {
        return "Thirst";
    }

    @Override
    protected void reset() {
        tickWhileStanding = true;
        tickInterval = 600;
        percentTickInterval = 3d;
        blockDistanceInterval = 50d;
        percentBlockDistanceInterval = 0.5d;
        percentThirstPerWaterBottle = 25d;
        thirstDamageInterval = 80;
        thirstDamageAmount = 1.0d;

        biomeModifiers = new HashMap<>();
        biomeModifiers.put("desert", 2.0d);
        biomeModifiers.put("savanna", 1.5d);
    }

    public boolean shouldTickWhileStanding() {
        return tickWhileStanding;
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public double getPercentTickInterval() {
        return percentTickInterval;
    }

    public double getBlockDistanceInterval() {
        return blockDistanceInterval;
    }

    public double getPercentBlockDistanceInterval() {
        return percentBlockDistanceInterval;
    }

    public double getPercentThirstPerWaterBottle() {
        return percentThirstPerWaterBottle;
    }

    public int getThirstDamageInterval() {
        return thirstDamageInterval;
    }

    public double getThirstDamageAmount() {
        return thirstDamageAmount;
    }

    public double getBiomeModifier(String name) {
        return this.biomeModifiers.getOrDefault(name, 1.0d);
    }
}
