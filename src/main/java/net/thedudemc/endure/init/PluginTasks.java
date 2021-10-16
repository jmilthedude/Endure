package net.thedudemc.endure.init;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.config.EnemyConfig;
import net.thedudemc.endure.util.Logger;
import net.thedudemc.endure.world.EndureSpawnerTask;
import net.thedudemc.endure.world.WorldTickTask;
import org.bukkit.Bukkit;

public class PluginTasks {

    public static void register() {
        registerTask(new EndureSpawnerTask(), 0, ((EnemyConfig) PluginConfigs.get("Enemies")).getSpawnRate());
        registerTask(new WorldTickTask(), 0L, 1L);

        Logger.info("Tasks registered.");
    }

    private static int registerTask(Runnable task, long delay, long interval) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Endure.getInstance(), task, delay, interval);
    }
}
