package net.thedudemc.endure.config.order;

import com.google.gson.annotations.Expose;

public class StalkerConfig extends OrderConfig {

    @Expose
    private double crouchSpeedModifier = 1.25d;

    public StalkerConfig() {

    }

    public double getCrouchSpeedModifier() {
        return crouchSpeedModifier;
    }
}
