package net.thedudemc.endure.entity;

import com.google.gson.annotations.Expose;
import net.thedudemc.dudeconfig.config.Config;
import net.thedudemc.endure.init.EndureConfigs;
import net.thedudemc.endure.util.MathUtilities;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public class SurvivorEntity {

    @Expose
    private UUID uuid;
    @Expose
    private String name;
    @Expose
    private int level;
    @Expose
    private float thirst;
    @Expose
    private float experience;
    @Expose
    private double distanceTraveled;

    private Scoreboard hud;

    public SurvivorEntity(UUID uuid, int level, float thirst, float experience) {
        this.uuid = uuid;
        this.name = this.getPlayer().getName();
        this.level = level;
        this.thirst = thirst;
        this.experience = experience;
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

    public void addThirst(float amount) {
        this.thirst = Math.min(thirst + amount, 1.0f);
        if (this.thirst == 1.0f) this.distanceTraveled = 0;
        this.getPlayer().setExp(thirst); // shows thirst level visually on exp bar
        markDirty();
    }

    public void decreaseThirst(double amount) {
        this.thirst = (float) Math.max(this.thirst - amount, 0.0f);
        this.getPlayer().setExp(thirst); // shows thirst level visually on exp bar
        markDirty();
    }

    public float getExperience() {
        return experience;
    }

    private int getExperienceForDisplay() {
        return (int) (this.getExperience() * 100f);
    }

    public void setExperience(float experience) {
        this.experience = MathUtilities.clamp(experience, 0f, 1f);
        markDirty();
    }

    public void addExperience(float experience) {
        this.experience = Math.min(this.experience + experience, 1.0f);
        if (this.experience == 1.0f) this.experience = 0.0f;
        markDirty();
    }

    public void removeExperience(float experience) {
        this.experience = Math.max(this.experience - experience, 0.0f);
        markDirty();
    }

    public void addDistanceTraveled(double distance) {
        this.distanceTraveled += distance;
        markDirty();
    }

    public void tick() {
        if (hud == null) setupHud(); // create a hud if one does not exist

        calculateThirst();

        if (this.getPlayer().getTicksLived() % EndureConfigs.get("General").getInt("hudUpdateInterval") == 0) {
            if (hud != null) updateHud();
        }
    }

    private void calculateThirst() {
        Player p = this.getPlayer();
        Location location = p.getLocation();
        Biome biome = location.getWorld().getBiome(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        Config config = EndureConfigs.get("Thirst");
        HashMap<?, ?> biomeModifiers = new HashMap<>(config.getMap("biomeModifiers"));
        System.out.println(biome.name().toLowerCase());
        Object o = biomeModifiers.get(biome.name().toLowerCase());
        float biomeModifier = 1f;
        if (o != null) biomeModifier = (float) o;
        if (config.getBoolean("tickWhileStanding")) {
            int interval = config.getInt("tickInterval");
            float percentTick = config.getFloat("percentTickInterval");
            if (p.getTicksLived() % interval == 0) {
                decreaseThirst((percentTick / 100f) * biomeModifier);
            }
        }
        if (distanceTraveled >= config.getDouble("blockDistanceInterval")) {
            decreaseThirst((config.getDouble("percentBlockDistanceInterval") / 100f) * biomeModifier);
            distanceTraveled = 0D;
        }

    }

    private void setupHud() {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective(this.getName(), "dummy", "Survivor HUD");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int position = 0;

        addHeader("Level", obj, ChatColor.GREEN, --position);
        addValue("Level", board, obj, --position, this.getLevel());
        addSpace(board, obj, --position);
        addHeader("Experience", obj, ChatColor.AQUA, --position);
        addValue("Experience", board, obj, --position, getExperienceForDisplay());

        this.hud = board;
        this.getPlayer().setScoreboard(hud);
    }

    private void addHeader(String name, Objective obj, ChatColor color, int position) {
        Score levelHeader = obj.getScore(color + name);
        levelHeader.setScore(position);
    }

    private void addValue(String name, Scoreboard board, Objective obj, int position, int value) {
        Team counter = board.registerNewTeam(name);
        counter.addEntry(ChatColor.values()[Math.abs(position)].toString());
        counter.setPrefix(" " + ChatColor.YELLOW + value);
        obj.getScore(ChatColor.values()[Math.abs(position)].toString()).setScore(position);
    }

    private void addSpace(Scoreboard board, Objective obj, int position) {
        Score levelHeader = obj.getScore("---------" + ChatColor.values()[Math.abs(position)].toString());
        levelHeader.setScore(position);
        Team counter = board.registerNewTeam(ChatColor.values()[Math.abs(position)].toString());
        counter.addEntry(ChatColor.values()[Math.abs(position)].toString());
    }

    private void updateHud() {
        Scoreboard hud = getPlayer().getScoreboard();
        updateLine("Level", hud, this.getLevel());
        updateLine("Experience", hud, getExperienceForDisplay());
    }

    private void updateLine(String name, Scoreboard hud, int value) {
        hud.getTeam(name).setPrefix(" " + ChatColor.YELLOW + value);
    }


    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    private void markDirty() {
        SurvivorsData.get().markDirty();
    }

}
