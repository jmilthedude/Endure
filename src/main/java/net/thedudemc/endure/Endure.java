package net.thedudemc.endure;

import net.thedudemc.endure.init.*;
import net.thedudemc.endure.util.Logger;
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

        initialize();

        //in case of reload, add players back to cache
        Bukkit.getOnlinePlayers().forEach(player -> SurvivorsData.get().getSurvivor(player).login(player));

    }

    private static void initialize() {
        Logger.info("Initializing...");

        PluginConfigs.register();
        PluginItems.register();
        PluginLoot.register();
        PluginCommands.register();
        PluginEvents.register();
        PluginTasks.register();
        PluginData.register();
    }

    @Override
    public void onDisable() {
        PluginData.REGISTRY.values().forEach(net.thedudemc.endure.world.data.Data::save);
    }

    public static NamespacedKey getKey(String name) {
        return new NamespacedKey(Endure.getInstance(), name);
    }

}
