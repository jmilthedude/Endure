package net.thedudemc.endure.entity.order;

import net.thedudemc.endure.config.order.OrderConfig;
import net.thedudemc.endure.config.order.StalkerConfig;
import net.thedudemc.endure.init.EndureConfigs;
import org.bukkit.entity.Player;

public class StalkerOrder extends Order {

    public StalkerOrder() {
        super("stalker", ((OrderConfig) EndureConfigs.get("Order")).getStalkerConfig());
    }

    @Override
    public void tick(Player player) {
        StalkerConfig config = this.getConfig();
        double modifier = config.getCrouchSpeedModifier();
        if (player.isSneaking() && !player.isSprinting()) {
            player.setWalkSpeed((float) (0.2f * modifier));
        } else {
            player.setWalkSpeed(0.2f);
        }
    }

    @Override
    public void apply(Player player) {
    }
}
