package net.thedudemc.endure.event;

import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.EndureAttributes;
import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class AttackEvent implements Listener {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;

        Player p = (Player) event.getDamager();
        ItemStack stack = p.getInventory().getItemInMainHand();
        EndureItem item = EndureItems.getItemFromStack(stack);
        if (item != null) {
            AttributeModifier bonusDamage = item.getAttributeModifier(EndureAttributes.BONUS_DAMAGE);
            AttributeModifier damage = item.getAttributeModifier(EndureAttributes.BASE_DAMAGE);
            double damageAmount = event.getDamage();
            if (damage != null) {
                damageAmount = damage.getAmount();
            }
            if (bonusDamage != null) {
                double value = bonusDamage.getAmount();
                damageAmount += value;
            }
            event.setDamage(damageAmount);
        }
        p.sendMessage(String.valueOf(event.getDamage()));
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player p = event.getEntity().getKiller();
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(p.getUniqueId());
        survivor.addLevel(1);
    }
}
