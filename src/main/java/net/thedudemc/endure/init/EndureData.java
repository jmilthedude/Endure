package net.thedudemc.endure.init;

import net.thedudemc.endure.util.Logger;
import net.thedudemc.endure.world.data.Data;
import net.thedudemc.endure.world.data.EntitiesData;
import net.thedudemc.endure.world.data.SurvivorsData;

import java.util.HashMap;

public class EndureData {

    public static HashMap<String, Data> REGISTRY = new HashMap<>();

    public static SurvivorsData SURVIVORS_DATA;
    public static EntitiesData ENTITIES_DATA;

    public static void register() {
        SURVIVORS_DATA = (SurvivorsData) registerData(new SurvivorsData().read());
        ENTITIES_DATA = (EntitiesData) registerData(new EntitiesData().read());
    }

    private static Data registerData(Data data) {
        Logger.info("Registering " + data.getName());
        REGISTRY.put(data.getName(), data);
        return data;
    }
}
