package net.thedudemc.endure.config.order;

import com.google.gson.annotations.Expose;

public class BruteConfig extends OrderConfig {
    @Expose private double damageReduction;

    public BruteConfig(double damageReduction) {
        this.damageReduction = damageReduction;
    }

    public double getDamageReduction() {
        return damageReduction;
    }
}
