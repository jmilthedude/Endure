package net.thedudemc.endure.gui;

import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.EndureConfigs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scoreboard.*;

public class SurvivorHud {

    private Scoreboard stats;
    private BossBar experienceBar;
    private final SurvivorEntity survivor;

    public SurvivorHud(SurvivorEntity survivor) {
        this.survivor = survivor;
        setupStats();
        setupExperienceBar();
    }


    private void setupStats() {
        if (this.stats != null) return;

        this.stats = Bukkit.getScoreboardManager().getNewScoreboard();
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

    private void setupExperienceBar() {
        if (this.experienceBar != null) return;

        this.experienceBar = Bukkit.createBossBar("Experience", BarColor.GREEN, BarStyle.SOLID);

        this.experienceBar.addPlayer(this.survivor.getPlayer());

    }


    public void updateStats() {
        if (this.stats != null) {
            if (!survivor.getPlayer().getScoreboard().equals(this.stats))
                survivor.getPlayer().setScoreboard(this.stats);

            if (survivor.getPlayer().getTicksLived() % EndureConfigs.get("General").getOption("hudUpdateInterval").getIntValue() == 0) {
                Scoreboard hud = survivor.getPlayer().getScoreboard();
                updateLine("Level", survivor.getLevel());
                updateLine("Experience", survivor.getExperience());
            }
        }
    }

    public void updateExperienceBar(int xpNeeded) {
        if (this.experienceBar != null) {
            int currentXp = survivor.getExperience();
            this.experienceBar.setProgress((double) currentXp / (double) xpNeeded);
        }
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
        if (this.stats.getTeam(name) == null) return;
        this.stats.getTeam(name).setPrefix(" " + ChatColor.YELLOW + value);
    }
}
