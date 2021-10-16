package net.thedudemc.endure.gui;

import net.thedudemc.endure.Endure;
import net.thedudemc.endure.config.GeneralConfig;
import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.PluginConfigs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scoreboard.*;

import java.util.HashMap;

public class SurvivorHud {

    private Scoreboard stats;
    private BossBar experienceBar;
    private HashMap<String, BossBar> statBars = new HashMap<>();
    private final SurvivorEntity survivor;

    public SurvivorHud(SurvivorEntity survivor) {
        this.survivor = survivor;
        setupStats();
        createStatBar("Experience", "xp", BarColor.GREEN);
        createStatBar("Magic", "magic", BarColor.BLUE);
    }

    private void setupStats() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;
        this.stats = manager.getNewScoreboard();
        this.survivor.getPlayer().setScoreboard(this.stats);
        Objective obj = this.stats.getObjective(survivor.getName());
        if (obj == null) obj = this.stats.registerNewObjective(survivor.getName(), "dummy", "Survivor HUD");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int position = 0;

        addHeader("Level", obj, ChatColor.GREEN, --position);
        addValue("Level", obj, --position, survivor.getLevel());
        addSpace(obj, --position);
        addHeader("Experience", obj, ChatColor.AQUA, --position);
        addValue("Experience", obj, --position, survivor.getExperience());
    }

    public void createStatBar(String title, String key, BarColor color) {
        NamespacedKey id = Endure.getKey(this.survivor.getName() + key);
        BossBar bar = Bukkit.getBossBar(id);
        if (bar == null) {
            bar = Bukkit.createBossBar(id, title, BarColor.GREEN, BarStyle.SOLID);
        }
        bar.setColor(color);
        bar.addPlayer(this.survivor.getPlayer());
        this.statBars.put(title, bar);
    }


    public void updateStatsSidePane() {
        if (this.stats != null) {
            if (this.survivor.getPlayer().getTicksLived() % ((GeneralConfig) PluginConfigs.get("General")).getHudUpdateInterval() == 0) {
                updateLine("Level", survivor.getLevel());
                updateLine("Experience", survivor.getExperience());
            }
        }
    }

    public void updateStatBars() {
        this.updateExperienceBar();
        this.updateMagicBar();
    }

    public void updateExperienceBar() {
        BossBar experienceBar = statBars.get("Experience");
        if (!experienceBar.getPlayers().contains(this.survivor.getPlayer())) {
            experienceBar.addPlayer(this.survivor.getPlayer());
        }
        int xpNeeded = survivor.getXpNeeded();
        int currentXp = survivor.getExperience();
        experienceBar.setProgress((double) currentXp / (double) xpNeeded);
    }

    public void updateMagicBar() {
        BossBar magicBar = statBars.get("Magic");
        if (!magicBar.getPlayers().contains(this.survivor.getPlayer())) {
            magicBar.addPlayer(this.survivor.getPlayer());
        }

        int currentMagic = survivor.getCurrentMP();
        int totalMagic = survivor.getMaxMP();
        magicBar.setProgress((double) currentMagic / (double) totalMagic);
    }

    private void addHeader(String name, Objective obj, ChatColor color, int position) {
        Score levelHeader = obj.getScore(color + name);
        levelHeader.setScore(position);
    }

    private void addValue(String name, Objective obj, int position, int value) {
        Team counter = this.stats.registerNewTeam(name);
        counter.addEntry(ChatColor.values()[Math.abs(position)].toString());
        counter.setPrefix(" " + ChatColor.YELLOW + value);
        obj.getScore(ChatColor.values()[Math.abs(position)].toString()).setScore(position);
    }

    private void addSpace(Objective obj, int position) {
        Score levelHeader = obj.getScore("---------" + ChatColor.values()[Math.abs(position)].toString());
        levelHeader.setScore(position);
        Team counter = this.stats.registerNewTeam(ChatColor.values()[Math.abs(position)].toString());
        counter.addEntry(ChatColor.values()[Math.abs(position)].toString());
    }

    private void updateLine(String name, int value) {
        Team team = this.stats.getTeam(name);
        if (team == null) return;
        team.setPrefix(" " + ChatColor.YELLOW + value);
    }

    public void reset() {
        Bukkit.removeBossBar(Endure.getKey(this.survivor.getName() + "xp"));
    }

}
