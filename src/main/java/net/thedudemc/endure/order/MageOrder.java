package net.thedudemc.endure.order;

import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.PluginSpells;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.entity.Player;

public class MageOrder extends Order {

    public MageOrder() {
        super("mage");
    }

    @Override
    public void tick(Player player) {
        if (player.getRemainingAir() < 300) {
            player.setRemainingAir(300);
        }
    }

    @Override
    public void apply(Player player) {
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(player);
        survivor.learnSpell(PluginSpells.FLAME_BALL);
        survivor.learnSpell(PluginSpells.HEAL_SPELL);
        survivor.setCurrentSpell(PluginSpells.HEAL_SPELL);
    }
}
