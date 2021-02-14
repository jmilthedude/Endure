package net.thedudemc.endure.event;

import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.EndureConfigs;
import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

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

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo() == null) return;
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(event.getPlayer().getUniqueId());
        double distance = event.getFrom().distance(event.getTo());
        survivor.addDistanceTraveled(distance);
    }

    @EventHandler
    public void onDrinkWater(PlayerItemConsumeEvent event) {
        Player p = event.getPlayer();
        ItemStack stack = event.getItem();
        ItemMeta meta = stack.getItemMeta();
        if (!(meta instanceof PotionMeta)) return;

        PotionMeta potion = (PotionMeta) meta;
        if (potion.getBasePotionData().getType() != PotionType.WATER) return;
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(p.getUniqueId());

        survivor.addThirst(EndureConfigs.THIRST.getPercentBottle() / 100f);
        event.setCancelled(true);
        if (p.getInventory().getItemInMainHand().isSimilar(stack))
            p.getInventory().setItemInMainHand(EndureItems.EMPTY_BOTTLE.getItemStack());
        else if (p.getInventory().getItemInOffHand().isSimilar(stack))
            p.getInventory().setItemInOffHand(EndureItems.EMPTY_BOTTLE.getItemStack());

    }
}
