package net.thedudemc.endure.config;

import com.google.gson.annotations.Expose;

public class GeneralConfig extends Config{

    @Expose private int HUD_UPDATE_INTERVAL_TICKS;

    @Override
    public String getName() {
        return "General";
    }

    @Override
    protected void reset() {
        HUD_UPDATE_INTERVAL_TICKS = 10;
    }

    public int getHudUpdateInterval() { return this.HUD_UPDATE_INTERVAL_TICKS; }
}
