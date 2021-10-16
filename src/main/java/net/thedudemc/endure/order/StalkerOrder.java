package net.thedudemc.endure.order;

import net.thedudemc.endure.config.order.StalkerConfig;
import net.thedudemc.endure.init.PluginConfigs;
import org.bukkit.entity.Player;

public class StalkerOrder extends Order {

    public StalkerOrder() {
        super("stalker");
    }

    @Override
    public void tick(Player player) {
        StalkerConfig config = PluginConfigs.getOrderConfig().getStalkerConfig();
        double modifier = config.getCrouchSpeedModifier();
        if (player.isSneaking() && !player.isSprinting()) {
            player.setWalkSpeed((float) (0.25f * modifier));
        } else if (!player.isSneaking()) {
            player.setWalkSpeed(0.25f);
        }
    }

    @Override
    public void apply(Player player) {
        StalkerConfig config = PluginConfigs.getOrderConfig().getStalkerConfig();
        player.setWalkSpeed((float) config.getBaseWalkSpeed());
    }
}
