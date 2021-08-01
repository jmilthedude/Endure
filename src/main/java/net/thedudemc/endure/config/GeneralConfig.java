package net.thedudemc.endure.config;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;

public class GeneralConfig extends Config {

    @Expose private int hudUpdateInterval;

    @Override
    public String getName() {
        return "General";
    }

    @Override
    protected void reset() {
        this.hudUpdateInterval = 1;
    }

    public int getHudUpdateInterval() {
        return this.hudUpdateInterval;
    }

    public void setHudUpdateInterval(int hudUpdateInterval) {
        this.hudUpdateInterval = hudUpdateInterval;
    }
}
