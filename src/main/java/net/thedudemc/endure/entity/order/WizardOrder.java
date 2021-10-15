package net.thedudemc.endure.entity.order;

import net.thedudemc.endure.init.EndureConfigs;
import org.bukkit.entity.Player;

public class WizardOrder extends Order {

    public WizardOrder() {
        super("wizard", EndureConfigs.get("wizard"));
    }

    @Override
    public void tick(Player player) {

    }

    @Override
    public void apply(Player player) {

    }
}
