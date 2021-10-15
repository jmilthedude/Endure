package net.thedudemc.endure.entity.order;

import net.thedudemc.endure.init.EndureConfigs;
import org.bukkit.entity.Player;

public class BruteOrder extends Order {

    public BruteOrder() {
        super("brute", EndureConfigs.get("brute"));

    }

    @Override
    public void tick(Player player) {

    }

    @Override
    public void apply(Player player) {
    }
}
