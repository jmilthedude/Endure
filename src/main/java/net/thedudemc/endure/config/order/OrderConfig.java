package net.thedudemc.endure.config.order;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;

public class OrderConfig extends Config {

    @Expose private StalkerConfig stalkerConfig;
    @Expose private BruteConfig bruteConfig;

    @Override
    protected String getName() {
        return "Order";
    }

    @Override
    protected void reset() {
        this.stalkerConfig = new StalkerConfig();
        this.bruteConfig = new BruteConfig(.75d);
    }

    public StalkerConfig getStalkerConfig() {
        return stalkerConfig;
    }

    public BruteConfig getBruteConfig() {
        return bruteConfig;
    }
}
