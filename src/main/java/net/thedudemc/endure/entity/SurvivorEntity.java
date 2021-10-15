package net.thedudemc.endure.entity;

import com.google.gson.annotations.Expose;
import net.thedudemc.endure.config.ExperienceConfig;
import net.thedudemc.endure.config.ThirstConfig;
import net.thedudemc.endure.entity.order.Order;
import net.thedudemc.endure.gui.SurvivorHud;
import net.thedudemc.endure.init.EndureConfigs;
import net.thedudemc.endure.init.EndureData;
import net.thedudemc.endure.init.EndureOrders;
import net.thedudemc.endure.util.EndureUtilities;
import net.thedudemc.endure.world.data.EntitiesData;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SurvivorEntity {

    @Expose private final UUID uuid;
    @Expose private final String name;
    @Expose private int level;
    @Expose private float thirst;
    @Expose private int experience;
    @Expose private double distanceTraveled;
    @Expose private String orderName;
    private Order order;
    private Vector currentVelocity = new Vector(0, 0, 0);

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

    public Order getOrder() {
        if (this.order != null) return this.order;

        Supplier<Order> supplier = EndureOrders.get(this.orderName);
        if (supplier != null) this.order = supplier.get();
        return order;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
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

    public Vector getCurrentVelocity() {
        return currentVelocity;
    }

    public void setCurrentVelocity(Vector currentVelocity) {
        this.currentVelocity = currentVelocity;
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

        if (this.getOrder() != null) this.getOrder().tick(this.getPlayer());

        if (slideCooldown > -1) {
            slideCooldown--;
        } else if (slideCooldown == -1) {
            handleSliding();
        }
    }

    // --------------------- Sliding ---------------- //

    private int slideCooldown = -1;
    private int currentSlideTick = -1;
    private boolean isSliding = false;
    private double currentSlideReduction;

    public void setSliding() {
        if (!this.getPlayer().isOnGround()) return;
        if (slideCooldown != -1) return;
        if (!this.isSliding && this.getPlayer().isSprinting()) {
            this.isSliding = true;
            currentSlideTick = 15;
            currentSlideReduction = .33d;
        }
    }

    public boolean isSliding() {
        return this.isSliding;
    }

    public void stopSliding() {
        this.isSliding = false;
        this.currentSlideTick = -1;
        this.currentSlideReduction = .33f;
        this.slideCooldown = 10;
    }

    private void handleSliding() {
        if (!this.isSliding) return;
        if (player.isSneaking() && player.isSprinting() && isSliding) {
            updateSlideReduction();
            player.setVelocity(new Vector(currentSlideReduction, player.getVelocity().getY(), currentSlideReduction).multiply(player.getLocation().getDirection()));
        }
        if (isSliding && currentSlideTick-- == 0) {
            this.stopSliding();
            player.setSprinting(false);

        }
    }

    private void updateSlideReduction() {
        if (currentSlideTick < 5) {
            currentSlideReduction -= currentSlideTick * .005;
        }
    }

    // --------------------- End Sliding ---------------- //

    private void checkEntitiesRemoved() {
        List<EndureZombie> entities = EntitiesData.get().getEntities(this.getId());
        List<EndureZombie> invalid = entities.stream()
                .filter(entity -> entity.getEntity() != null)
                .filter(endureEntity -> !endureEntity.getEntity().isValid())
                .collect(Collectors.toList());
        EndureData.ENTITIES_DATA.removeEntities(this.getId(), invalid);
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
}
