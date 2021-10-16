package net.thedudemc.endure.world;

import net.thedudemc.endure.config.EnemyConfig;
import net.thedudemc.endure.entity.EndureZombie;
import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.PluginConfigs;
import net.thedudemc.endure.world.data.EntitiesData;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EndureSpawnerTask implements Runnable {

    private void attemptSpawnZombie(SurvivorEntity survivor) {
        EnemyConfig config = PluginConfigs.get("Enemies");

        EntitiesData entityData = EntitiesData.get();
        int spawnedEntityCount = entityData.getEntities(survivor.getId()).size();
        if (spawnedEntityCount < config.getMaximumEntities(survivor.getLevel())) {
            if (Math.random() < config.getSpawnChance()) {
                int min = config.getMinSpawnRadius();
                int max = config.getMaxSpawnRadius();
                int maxHeightDifference = config.getMaxHeightDifference();
                Location spawnLocation = getPossibleSpawnLocation(survivor.getPlayer().getWorld(), survivor.getPlayer().getLocation(), min, max, maxHeightDifference);
                if (spawnLocation == null) return;
                Zombie zombie = (Zombie) survivor.getPlayer().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
                entityData.addEntity(survivor.getId(), new EndureZombie(zombie, 1));
            }
        }
    }


    private Location getPossibleSpawnLocation(@NotNull World world, Location playerLoc, int min, int max, int maxHeightDifference) {
        Random random = new Random();
        double angle = 2.0D * Math.PI * random.nextDouble();
        double distance = Math.sqrt(random.nextDouble() * (max * max - min * min) + min * min);
        int x = (int) Math.ceil(distance * Math.cos(angle));
        int z = (int) Math.ceil(distance * Math.sin(angle));
        x += playerLoc.getBlockX();
        z += playerLoc.getBlockZ();
        Location potential;
        int maxHeight = world.getMaxHeight();
        int minY = Math.max(1, playerLoc.getBlockY() - maxHeightDifference);
        int maxY = Math.min(playerLoc.getBlockY() + maxHeightDifference, maxHeight);
        for (int yLevel = minY; yLevel < maxY; yLevel++) {
            potential = new Location(playerLoc.getWorld(), x, yLevel, z);
            if (!isValidSpawnLocation(playerLoc, potential, min, max)) continue;

            return potential.add(0.5, -1, 0.5);

        }
        return null;
    }

    private boolean isValidSpawnLocation(Location playerLocation, Location potential, int min, int max) {
        if (potential.getBlock().getType().isAir()) return false;
        else if (potential.getBlock().isLiquid()) return false;
        else if (potential.getBlock().isPassable()) return false;
        else if (potential.getBlock().getBlockData() instanceof Leaves) return false;
        else if (distance2d(potential, playerLocation) < (min * min)) return false;
        else if (distance2d(potential, playerLocation) > (max * max)) return false;
        for (int i = 1; i <= 2; i++) {
            potential.add(0, 1, 0);
            if (!potential.getBlock().getType().isAir()) {
                return false;
            }
        }
        return true;
    }

    private double distance2d(Location a, Location b) {
        int x1 = a.getBlockX();
        int z1 = a.getBlockZ();
        int x2 = b.getBlockX();
        int z2 = b.getBlockZ();
        return (x2 - x1) * (x2 - x1) + (z2 - z1) * (z2 - z1);
    }

    @Override
    public void run() {
        if (Bukkit.getWorlds().get(0).getDifficulty() == Difficulty.PEACEFUL) return;
        SurvivorsData.get().getSurvivors().stream().filter(SurvivorEntity::isOnline).forEach(this::attemptSpawnZombie);
    }
}
