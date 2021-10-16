package net.thedudemc.endure.config.order;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;

public class OrderConfig extends Config {

    @Expose private StalkerConfig stalker;
    @Expose private BruteConfig brute;
    @Expose private MageConfig mage;

    @Override
    protected String getName() {
        return "Order";
    }

    @Override
    protected void reset() {
        this.stalker = new StalkerConfig();
        this.brute = new BruteConfig();
        this.mage = new MageConfig();
    }

    public StalkerConfig getStalkerConfig() {
        return stalker;
    }

    public BruteConfig getBruteConfig() {
        return brute;
    }

    public MageConfig getMageConfig() {
        return mage;
    }
}
