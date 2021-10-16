package net.thedudemc.endure.config.order;

import com.google.gson.annotations.Expose;

public class MageConfig {
    @Expose private int additionalStartingMP = 10;

    public int getAdditionalStartingMP() {
        return additionalStartingMP;
    }
}
