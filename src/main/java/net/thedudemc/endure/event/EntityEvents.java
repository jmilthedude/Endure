package net.thedudemc.endure.event;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityEvents implements Listener {

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (!(event.getEntity() instanceof Zombie && event.getTarget() instanceof Player)) return;

        Zombie zombie = (Zombie) event.getEntity();
        Player player = (Player) event.getTarget();
        if (player.isSneaking()) {
            if (zombie.getLocation().distanceSquared(player.getLocation()) > (10 * 10)) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFire(EntityCombustEvent event) {
        if (event.getEntity() instanceof Zombie) event.setCancelled(true);
    }
}
