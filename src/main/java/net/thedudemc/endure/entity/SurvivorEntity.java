package net.thedudemc.endure.entity;

import com.google.gson.annotations.Expose;
import net.thedudemc.endure.config.ExperienceConfig;
import net.thedudemc.endure.config.ThirstConfig;
import net.thedudemc.endure.gui.SurvivorHud;
import net.thedudemc.endure.init.PluginConfigs;
import net.thedudemc.endure.init.PluginData;
import net.thedudemc.endure.order.IMage;
import net.thedudemc.endure.order.Order;
import net.thedudemc.endure.spells.Spell;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SurvivorEntity implements IMage {

    @Expose private final UUID uuid;
    @Expose private final String name;
    @Expose private int level;
    @Expose private float thirst;
    @Expose private int experience;
    @Expose private double distanceTraveled;
    @Expose private int currentMP;
    @Expose private int maxMP;
    @Expose private Order order;
    @Expose private Spell currentSpell;
    @Expose private List<Spell> learnedSpells = new ArrayList<>();

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
        this.maxMP = 100;
        this.currentMP = this.maxMP;
    }

    public void tick() {
        if (this.hud == null) this.hud = new SurvivorHud(this);
        this.hud.updateStatsSidePane();
        this.hud.updateStatBars();

        EndureUtilities.ensureStackSizes(this.getPlayer());

        if (this.getPlayer().getGameMode() == GameMode.SURVIVAL && !this.getPlayer().isInWater()) handleThirst();

        checkEntitiesRemoved();

        if (this.getOrder() != null) this.getOrder().tick(this.getPlayer());

        if (slideCooldown > -1) {
            slideCooldown--;
        } else if (slideCooldown == -1) {
            handleSliding();
        }

        if (this.getPlayer().getTicksLived() % 20 == 0) {
            increaseMP(1);
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.markDirty();
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

        this.hud.updateExperienceBar();

        markDirty();
    }

    private void updateXpNeeded() {
        ExperienceConfig config = PluginConfigs.get("Experience");
        this.xpNeeded = config.getExperienceNeeded(this.getLevel());
    }

    public int getXpNeeded() {
        return xpNeeded;
    }

    public void addDistanceTraveled(double distance) {
        this.distanceTraveled += distance;
        markDirty();
    }

    public void login(Player player) {
        this.online = true;
        this.player = player;
        updateXpNeeded();
        this.markDirty();
    }

    public void logout() {
        this.online = false;
        this.hud = null;
        this.markDirty();
    }

    public boolean isOnline() {
        return online;
    }

    @Override
    public void learnSpell(Spell spell) {
        this.learnedSpells.add(spell);
        this.markDirty();
    }

    @Override
    public int getCurrentMP() {
        return currentMP;
    }

    @Override
    public int getMaxMP() {
        return maxMP;
    }

    @Override
    public void decreaseMP(int amount) {
        this.currentMP = Math.max(0, currentMP - amount);
        this.hud.updateMagicBar();
        this.markDirty();
    }

    @Override
    public void increaseMP(int amount) {
        this.currentMP = Math.min(currentMP + amount, maxMP);
        this.hud.updateMagicBar();
        this.markDirty();
    }

    @Override
    public void addMaxMP(int amount) {
        this.maxMP += amount;
        this.markDirty();
    }

    @Override
    public Spell getCurrentSpell() {
        if (currentSpell == null && !learnedSpells.isEmpty()) {
            currentSpell = learnedSpells.get(0);
            this.markDirty();
        }
        return currentSpell;
    }

    @Override
    public List<Spell> getLearnedSpells() {
        return this.learnedSpells;
    }

    @Override
    public void setCurrentSpell(Spell spell) {
        if (getLearnedSpells().contains(spell)) {
            this.currentSpell = spell;
            this.markDirty();
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
        PluginData.ENTITIES_DATA.removeEntities(this.getId(), invalid);
    }


    private void handleThirst() {
        Player p = this.getPlayer();
        Location location = p.getLocation();
        Biome biome = p.getWorld().getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        ThirstConfig config = PluginConfigs.get("Thirst");
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

    private void causeThirstDamage() {
        if (this.getPlayer().getTicksLived() % ((ThirstConfig) PluginConfigs.get("Thirst")).getThirstDamageInterval() == 0) {
            this.getPlayer().damage(((ThirstConfig) PluginConfigs.get("Thirst")).getThirstDamageAmount());
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
