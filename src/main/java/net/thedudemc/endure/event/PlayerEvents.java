package net.thedudemc.endure.event;

import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(event.getPlayer().getUniqueId());

        SurvivorsData.get().setOnline(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        SurvivorsData.get().setOffline(event.getPlayer().getUniqueId());
    }
}
