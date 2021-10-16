package net.thedudemc.endure.event;

import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.spells.Spell;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MagicEvents implements Listener {

    @EventHandler
    public void onUseMagic(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) return;
        if (event.getItem() == null || event.getItem().getType() != Material.BLAZE_ROD) return;

        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(event.getPlayer());
        if (!survivor.canUseCurrentSpell()) {
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_CANDLE_EXTINGUISH, .7f, 1f);
            return;
        }

        Spell currentSpell = survivor.getCurrentSpell();
        survivor.decreaseMP(currentSpell.getCost());
        currentSpell.cast(survivor);
    }
}
