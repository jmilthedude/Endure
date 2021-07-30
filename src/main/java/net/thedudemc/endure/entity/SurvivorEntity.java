package net.thedudemc.endure.entity;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.endure.config.ExperienceConfig;
import net.thedudemc.endure.gui.SurvivorHud;
import net.thedudemc.endure.init.EndureAttributes;
import net.thedudemc.endure.init.EndureConfigs;
import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import net.thedudemc.endure.util.EndureUtilities;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class SurvivorEntity {

    @Expose private final UUID uuid;
    @Expose private final String name;
    @Expose private int level;
    @Expose private float thirst;
    @Expose private int experience;
    @Expose private double distanceTraveled;

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

        ensureStackSizes();

        calculateThirst();
        if (this.thirst <= 0) {
            causeThirstDamage();
        }

    }

    private void ensureStackSizes() {
        for (ItemStack stack : this.getPlayer().getInventory()) {
            if (stack == null || stack.getType() == Material.AIR) continue;
            EndureItem item = EndureItems.getItemFromStack(stack);
            if (item == null) continue;
            int currentAmount = stack.getAmount();
            AttributeModifier modifier = item.getAttributeModifier(EndureAttributes.MAX_STACK_SIZE);
            if (modifier == null) continue;

            int maxStackSize = (int) item.getAttributeModifier(EndureAttributes.MAX_STACK_SIZE).getAmount();
            if (currentAmount > maxStackSize) {
                int excess = currentAmount - maxStackSize;
                stack.setAmount(maxStackSize);
                ItemStack extra = stack.clone();
                extra.setAmount(excess);
                if (EndureUtilities.addItem(getPlayer(), extra, false)) {
                    this.getPlayer().updateInventory();
                } else {
                    this.getPlayer().getWorld().dropItem(this.getPlayer().getLocation(), extra);
                }
            }
        }
    }


    private void causeThirstDamage() {
        if (this.getPlayer().getTicksLived() % EndureConfigs.get("Thirst").getOption("thirstDamageInterval").getIntValue() == 0) {
            this.getPlayer().damage(EndureConfigs.get("Thirst").getOption("thirstDamageAmount").getDoubleValue());
        }
    }

    private void calculateThirst() {
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
