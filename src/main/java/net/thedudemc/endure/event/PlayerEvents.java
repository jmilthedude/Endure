package net.thedudemc.endure.event;

import net.thedudemc.endure.config.ThirstConfig;
import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.PluginConfigs;
import net.thedudemc.endure.init.PluginItems;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(player);
        survivor.login(player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        SurvivorsData.get().getSurvivor(event.getPlayer()).logout();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo() == null) return;
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(event.getPlayer());
        double distance = event.getFrom().distance(event.getTo());
        survivor.addDistanceTraveled(distance);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(event.getPlayer());

        if (event.isSneaking()) {
            if (event.getPlayer().isSprinting()) survivor.setSliding();
        } else {
            if (survivor.isSliding()) {
                survivor.stopSliding();
            }
        }
    }

    @EventHandler
    public void onDrinkWater(PlayerItemConsumeEvent event) {
        Player p = event.getPlayer();
        ItemStack stack = event.getItem();
        ItemMeta meta = stack.getItemMeta();
        if (!(meta instanceof PotionMeta potion)) return;

        if (potion.getBasePotionData().getType() != PotionType.WATER) return;
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(p);

        survivor.decreaseThirst((float) (((ThirstConfig) PluginConfigs.get("Thirst")).getPercentThirstPerWaterBottle() / 100f));
        event.setCancelled(true);
        if (p.getInventory().getItemInMainHand().isSimilar(stack))
            p.getInventory().setItemInMainHand(PluginItems.EMPTY_BOTTLE.getItemStack());
        else if (p.getInventory().getItemInOffHand().isSimilar(stack))
            p.getInventory().setItemInOffHand(PluginItems.EMPTY_BOTTLE.getItemStack());

    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        event.setCancelled(true);
    }

    // for testing armor damage
    @EventHandler
    public void onAddArmor(PlayerInteractAtEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getRightClicked().getType() != EntityType.ZOMBIE) return;

        Zombie zombie = (Zombie) event.getRightClicked();
        Player player = event.getPlayer();

        ItemStack stack = event.getPlayer().getInventory().getItemInMainHand();
        if (stack.getType() != Material.DIAMOND_CHESTPLATE) return;
        EntityEquipment equipment = zombie.getEquipment();
        if (equipment != null) {
            equipment.setChestplate(stack);
        }
        stack.setAmount(0);
        player.updateInventory();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(event.getPlayer());
        survivor.setThirst(1.0f);
    }

}
