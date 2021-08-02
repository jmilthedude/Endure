package net.thedudemc.endure.init;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.config.EnemyConfig;
import net.thedudemc.endure.world.EndureSpawnerTask;
import net.thedudemc.endure.world.WorldTickTask;
import org.bukkit.Bukkit;

public class EndureTasks {

    public static void register() {
        registerTask(new EndureSpawnerTask(), 0, ((EnemyConfig) EndureConfigs.get("Enemies")).getSpawnRate());
        registerTask(new WorldTickTask(), 0L, 1L);
    }

    private static int registerTask(Runnable task, long delay, long interval) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(Endure.getInstance(), task, delay, interval);
    }
}
