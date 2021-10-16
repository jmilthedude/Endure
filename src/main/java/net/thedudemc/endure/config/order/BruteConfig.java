package net.thedudemc.endure.config.order;

import com.google.gson.annotations.Expose;

public class BruteConfig {
    @Expose private double damageReduction;
    @Expose private double startingHealth;

    public BruteConfig() {
        this.damageReduction = 0.15d;
        this.startingHealth = 24.0d;
    }

    public double getDamageReduction() {
        return damageReduction;
    }

    public double getStartingHealth() {
        return startingHealth;
    }
}
