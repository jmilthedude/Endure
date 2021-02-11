package net.thedudemc.endure.config;

import com.google.gson.annotations.Expose;

public class ThirstConfig extends Config {

    @Expose private boolean SHOULD_TICK_WHILE_STILL;
    @Expose private int TICK_INTERVAL;
    @Expose private float PERCENT_TICK_INTERVAL;
    @Expose private float BLOCK_DISTANCE_INTERVAL;
    @Expose private float PERCENT_BLOCK_DISTANCE_INTERVAL;
    @Expose private float PERCENT_THIRST_PER_BOTTLE;

    @Override
    public String getName() {
        return "Thirst";
    }

    @Override
    protected void reset() {
        SHOULD_TICK_WHILE_STILL = true;
        TICK_INTERVAL = 600;
        PERCENT_TICK_INTERVAL = 3.0f;
        BLOCK_DISTANCE_INTERVAL = 50f;
        PERCENT_BLOCK_DISTANCE_INTERVAL = 0.5f;
        PERCENT_THIRST_PER_BOTTLE = 25f;
    }

    public boolean getShouldTick() { return this.SHOULD_TICK_WHILE_STILL; }
    public int getTickInterval() { return this.TICK_INTERVAL; }
    public float getPercentTick() { return this.PERCENT_TICK_INTERVAL; }
    public float getBlockDistance() { return this.BLOCK_DISTANCE_INTERVAL; }
    public float getPercentDistance() { return this.PERCENT_BLOCK_DISTANCE_INTERVAL; }
    public float getPercentBottle() { return this.PERCENT_THIRST_PER_BOTTLE; }
}
