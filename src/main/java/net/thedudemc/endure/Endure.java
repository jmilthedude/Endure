package net.thedudemc.endure;

import net.thedudemc.endure.init.EndureData;
import net.thedudemc.endure.init.EndureSetup;
import net.thedudemc.endure.world.data.Data;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

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
        if (INSTANCE == null) INSTANCE = this;

        EndureSetup.initialize();

        //in case of reload, add players back to cache
        Bukkit.getOnlinePlayers().forEach(player -> SurvivorsData.get().getSurvivor(player).onLogin(player));

    }

    @Override
    public void onDisable() {
        EndureData.REGISTRY.values().forEach(Data::save);
    }

    public static NamespacedKey getKey(String name) {
        return new NamespacedKey(Endure.getInstance(), name);
    }

}
