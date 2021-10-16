package net.thedudemc.endure.spells;

import net.thedudemc.endure.entity.SurvivorEntity;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class FlameBallSpell extends Spell {


    public FlameBallSpell(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void cast(SurvivorEntity survivor) {
        Player p = survivor.getPlayer();
        p.getWorld().playSound(p.getLocation(), Sound.ITEM_FIRECHARGE_USE, .7f, 1.0f);
        Vector direction = p.getLocation().getDirection();
        Fireball fireball = p.getWorld().spawn(p.getEyeLocation(), Fireball.class);
        fireball.setDirection(direction);
        fireball.setCustomName(this.getName());
        fireball.setCustomNameVisible(false);
        fireball.setVelocity(direction.multiply(3));
        fireball.setShooter(survivor.getPlayer());
    }

    @EventHandler
    public void onFlameBallHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Fireball fireball) {
            if (fireball.getCustomName() != null && fireball.getCustomName().equalsIgnoreCase("flame_ball")) {
                event.setCancelled(true);
                if (event.getHitEntity() != null && event.getHitEntity() instanceof LivingEntity target) {
                    target.damage(10.5d, (Entity) event.getEntity().getShooter());
                    fireball.remove();
                }
            }
        }
    }

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent event) {
        if (event.getEntity() instanceof Fireball fireball) {
            if (fireball.getCustomName() != null && fireball.getCustomName().equalsIgnoreCase("flame_ball")) {
                event.setCancelled(true);
                fireball.getWorld().playSound(fireball.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
            }
        }
    }
}
