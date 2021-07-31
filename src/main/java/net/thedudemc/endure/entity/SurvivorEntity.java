package net.thedudemc.endure.entity;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.endure.config.EnemyConfig;
import net.thedudemc.endure.config.ExperienceConfig;
import net.thedudemc.endure.gui.SurvivorHud;
import net.thedudemc.endure.init.EndureConfigs;
import net.thedudemc.endure.util.EndureUtilities;
import net.thedudemc.endure.util.MathUtilities;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SurvivorEntity {

    @Expose private final UUID uuid;
    @Expose private final String name;
    @Expose private int level;
    @Expose private float thirst;
    @Expose private int experience;
    @Expose private double distanceTraveled;

    private Set<UUID> spawnedEntities = new HashSet<>();
    private int xpNeeded;
    private boolean online;

    private SurvivorHud hud;
    private Player player;

    public SurvivorEntity(UUID uuid, int level, float thirst, int experience) {
        this.uuid = uuid;
        this.name = this.getPlayer().getName();
        this.level = level;
        this.thirst = thirst;
        this.experience = experience;
        this.hud = new SurvivorHud(this);
    }

    public UUID getId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        markDirty();
    }

    public void addLevel(int amount) {
        this.level += amount;
        markDirty();
    }

    public float getThirst() {
        return thirst;
    }

    public void setThirst(float thirst) {
        this.thirst = thirst;
        markDirty();
    }

    public void decreaseThirst(float amount) {
        this.thirst = Math.min(thirst + amount, 1.0f);
        if (this.thirst == 1.0f) this.distanceTraveled = 0;
        this.getPlayer().setExp(thirst); // shows thirst level visually on exp bar
        markDirty();
    }

    public void increaseThirst(double amount) {
        this.thirst = (float) Math.max(this.thirst - amount, 0.0f);
        this.getPlayer().setExp(thirst); // shows thirst level visually on exp bar
        markDirty();
    }

    public int getExperience() {
        return experience;
    }

    public void addExperience(int experience) {
        this.experience += experience;
        int initialLevel = this.level;
        updateXpNeeded();
        while (this.experience >= this.xpNeeded) {
            this.level++;
            this.experience -= this.xpNeeded;
        }
        if (this.level > initialLevel) {
            this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1.0f);
        }

        this.hud.updateExperienceBar(this.xpNeeded);

        markDirty();
    }

    private void updateXpNeeded() {
        ExperienceConfig config = (ExperienceConfig) EndureConfigs.get("Experience");
        this.xpNeeded = config.getExperienceNeeded(this.getLevel());
    }

    public void addDistanceTraveled(double distance) {
        this.distanceTraveled += distance;
        markDirty();
    }

    public void setOnline(boolean online) {
        this.online = online;
        updateXpNeeded();
        this.markDirty();
    }

    public boolean isOnline() {
        return online;
    }

    public void tick() {
        if (this.hud == null) this.hud = new SurvivorHud(this);
        this.hud.updateStats();
        this.hud.updateExperienceBar(xpNeeded);

        EndureUtilities.ensureStackSizes(this.getPlayer());

        handleThirst();

        if (this.spawnedEntities == null) this.spawnedEntities = new HashSet<>();
        attemptSpawnZombie();

        checkEntitiesRemoved();
    }

    private void checkEntitiesRemoved() {
        UUID idToRemove = null;
        for (UUID id : this.spawnedEntities) {
            Entity e = Bukkit.getEntity(id);
            if (e == null || e.isDead()) {
                idToRemove = id;
            }
        }
        if (idToRemove != null) this.spawnedEntities.remove(idToRemove);
    }

    private void attemptSpawnZombie() {
        if (this.getPlayer().getTicksLived() % ((EnemyConfig) EndureConfigs.get("Enemies")).getSpawnFrequency() == 0) {
            EnemyConfig config = (EnemyConfig) EndureConfigs.get("Enemies");
            if (this.spawnedEntities.size() < config.getMaximumEntities(this.getLevel())) {
                if (Math.random() < config.getSpawnChance()) {
                    double radius = config.getSpawnRadius();
                    Location spawnLocation = getPossibleSpawnLocation(this.getPlayer().getWorld(), this.getPlayer().getLocation(), radius);
                    if (spawnLocation == null) return;
                    Zombie zombie = (Zombie) this.getPlayer().getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
                    zombie.setGlowing(true);
                    if (zombie.isAdult()) {
                        AttributeInstance speed = zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                        if (speed != null) speed.setBaseValue(speed.getBaseValue() * 1.5);
                    }
                    this.spawnedEntities.add(zombie.getUniqueId());
                }
            }
        }
    }

    private Location getPossibleSpawnLocation(@NotNull World world, Location location, double radius) {
        Location toSpawn = null;
        int positiveX = MathUtilities.getRandomInt(24, (int) radius);
        int negativeX = -MathUtilities.getRandomInt(25, (int) radius);
        int positiveZ = MathUtilities.getRandomInt(24, (int) radius);
        int negativeZ = -MathUtilities.getRandomInt(25, (int) radius);
        Location spawn = new Location(world, location.getX(), location.getY(), location.getZ());
        if (Math.random() < .5d) {
            spawn.add(positiveX, 0, 0);
        } else {
            spawn.add(negativeX, 0, 0);
        }
        if (Math.random() < .5d) {
            spawn.add(0, 0, positiveZ);
        } else {
            spawn.add(0, 0, negativeZ);
        }
        for (int yLevel = (int) Math.max(1, location.getBlockY() - radius); yLevel < Math.min(location.getBlockY() + radius, world.getMaxHeight()); yLevel++) {
            Block block = world.getBlockAt(spawn.getBlockX(), yLevel + 1, spawn.getBlockZ());
            if (block.isLiquid()) continue;
            if ((world.getBlockAt(location).getLightFromSky() > 0 && block.getLightFromSky() > 0) ||
                    (world.getBlockAt(location).getLightFromSky() == 0 && block.getLightFromSky() == 0)) {
                Location above = block.getLocation().clone().add(0, 1, 0);
                if (world.getBlockAt(above).getType().isAir() && world.getBlockAt(above.add(0, 1, 0)).getType().isAir()) {
                    toSpawn = block.getLocation().clone().add(0.5, 1, 0.5);
                    break;
                }
            }
        }
        return toSpawn;
    }


    private void causeThirstDamage() {
        if (this.getPlayer().getTicksLived() % EndureConfigs.get("Thirst").getOption("thirstDamageInterval").getIntValue() == 0) {
            this.getPlayer().damage(EndureConfigs.get("Thirst").getOption("thirstDamageAmount").getDoubleValue());
        }
    }

    private void handleThirst() {
        Player p = this.getPlayer();
        Location location = p.getLocation();

        Biome biome = location.getWorld().getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        Config config = EndureConfigs.get("Thirst");
        HashMap<?, ?> biomeModifiers = new HashMap<>(config.getOption("biomeModifiers").getMapValue());
        Object o = biomeModifiers.get(biome.name().toLowerCase());
        float biomeModifier = 1f;
        if (o != null) biomeModifier = (float) o;

        if (config.getOption("tickWhileStanding").getBooleanValue()) {
            int interval = config.getOption("tickInterval").getIntValue();
            float percentTick = config.getOption("percentTickInterval").getFloatValue();
            if (p.getTicksLived() % interval == 0) {
                increaseThirst((percentTick / 100f) * biomeModifier);
            }
        }

        if (this.distanceTraveled >= config.getOption("blockDistanceInterval").getDoubleValue()) {
            increaseThirst((config.getOption("percentBlockDistanceInterval").getDoubleValue() / 100f) * biomeModifier);
            this.distanceTraveled = 0D;
        }

        if (this.thirst <= 0) {
            causeThirstDamage();
        }
    }


    public Player getPlayer() {
        if (this.player != null) return this.player;
        this.player = Bukkit.getPlayer(this.uuid);
        return this.player;
    }

    private void markDirty() {
        SurvivorsData.get().markDirty();
    }

}
