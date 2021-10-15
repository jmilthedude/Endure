package net.thedudemc.endure.entity;

import com.google.gson.annotations.Expose;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public abstract class EndureEntity {

    @Expose protected final UUID id;
    @Expose protected final int level;

    private LivingEntity entity;

    public void tick() {
        if (shouldGlow() && !this.entity.isGlowing()) this.entity.setGlowing(true);
    }

    public EndureEntity(LivingEntity entity, int level) {
        this.id = entity.getUniqueId();
        this.entity = entity;
        this.level = level;
        updateHealthBar();
    }

    public UUID getId() {
        return id;
    }

    public LivingEntity getEntity() {
        if (entity != null) return entity;
        this.entity = (LivingEntity) Bukkit.getEntity(this.id);
        return this.entity;
    }

    public void updateHealthBar() {
        this.getEntity().setCustomName(this.getLevelString() + " - " + this.getHealthString());
    }

    private String getLevelString() {
        return ChatColor.YELLOW + "" + this.level + ChatColor.RESET;
    }

    private String getHealthString() {
        AttributeInstance maxHealth = this.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth == null) return "";
        return ChatColor.RED + String.valueOf((int) Math.ceil(this.getEntity().getHealth())) +
                ChatColor.RESET + "/" +
                ChatColor.RED + (int) maxHealth.getValue() + "hp";
    }

    public boolean shouldGlow() {
        return true;
    }

}
