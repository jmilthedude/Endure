package net.thedudemc.endure;

import net.thedudemc.endure.init.EndureData;
import net.thedudemc.endure.init.EndureSetup;
import net.thedudemc.endure.util.Logger;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;

public class Endure extends JavaPlugin {

    private static Endure INSTANCE;

    public static Endure getInstance() {
        return INSTANCE;
    }

    public static String getPluginName() {
        return Endure.class.getSimpleName();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        EndureSetup.initialize(this);

    }

    @Override
    public void onDisable() {
        EndureData.REGISTRY.values().forEach(data -> {
            try {
                data.write();
            } catch (IOException e) {
                Logger.error(e.getMessage());
                Logger.error(Arrays.toString(e.getStackTrace()));
            }
        });
    }

    public static NamespacedKey getKey(String name) {
        return new NamespacedKey(Endure.getInstance(), name);
    }

}
