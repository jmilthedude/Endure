package net.thedudemc.endure.config.order;

import com.google.gson.annotations.Expose;

public class StalkerConfig {

    @Expose private double crouchSpeedModifier = 1.25d;
    @Expose private double baseWalkSpeed = 0.25d;

    public double getCrouchSpeedModifier() {
        return crouchSpeedModifier;
    }

    public double getBaseWalkSpeed() {
        return baseWalkSpeed;
    }
}
