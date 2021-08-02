package net.thedudemc.endure.entity;

import com.google.gson.annotations.Expose;
import net.thedudemc.endure.config.ExperienceConfig;
import net.thedudemc.endure.config.ThirstConfig;
import net.thedudemc.endure.gui.SurvivorHud;
import net.thedudemc.endure.init.EndureConfigs;
import net.thedudemc.endure.util.EndureUtilities;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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
    @Expose private Set<UUID> spawnedEntities = new HashSet<>();

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

    public void onLogin(Player player) {
        this.online = true;
        this.player = player;
        updateXpNeeded();
        this.markDirty();
    }

    public void onLogout() {
        this.online = false;
        this.hud = null;
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

        if (this.getPlayer().getGameMode() == GameMode.SURVIVAL) handleThirst();

        checkEntitiesRemoved();
    }

    private void checkEntitiesRemoved() {
        UUID idToRemove = null;
        for (UUID id : this.spawnedEntities) {
            Entity e = Bukkit.getEntity(id);
            if (e == null || e.isDead() || !e.isValid()) {
                idToRemove = id;
            }
        }
        if (idToRemove != null) this.spawnedEntities.remove(idToRemove);
    }

    private boolean hasSkyAccess(Location location) {
        return location.getBlock().getLightFromSky() > 0;
    }


    private void causeThirstDamage() {
        if (this.getPlayer().getTicksLived() % ((ThirstConfig) EndureConfigs.get("Thirst")).getThirstDamageInterval() == 0) {
            this.getPlayer().damage(((ThirstConfig) EndureConfigs.get("Thirst")).getThirstDamageAmount());
        }
    }

    private void handleThirst() {
        Player p = this.getPlayer();
        Location location = p.getLocation();
        Biome biome = p.getWorld().getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        ThirstConfig config = (ThirstConfig) EndureConfigs.get("Thirst");
        double biomeModifier = config.getBiomeModifier(biome.name().toLowerCase());
        if (config.shouldTickWhileStanding()) {
            int interval = config.getTickInterval();
            double percentTick = config.getPercentTickInterval();
            if (p.getTicksLived() % interval == 0) {
                increaseThirst((percentTick / 100d) * biomeModifier);
            }
        }

        if (this.distanceTraveled >= config.getBlockDistanceInterval()) {
            increaseThirst((config.getPercentBlockDistanceInterval() / 100d) * biomeModifier);
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

    public Set<UUID> getSpawnedEntities() {
        return this.spawnedEntities;
    }
}
