package net.thedudemc.endure.world;

import net.thedudemc.endure.world.data.SurvivorsData;

public class WorldTickTask implements Runnable {
    @Override
    public void run() {
        SurvivorsData.get().tick();
    }
}
