package net.thedudemc.endure.order;

import net.thedudemc.endure.config.order.BruteConfig;
import net.thedudemc.endure.init.PluginConfigs;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class BruteOrder extends Order {

    public BruteOrder() {
        super("brute");
    }

    @Override
    public void tick(Player player) {

    }

    @Override
    public void apply(Player player) {
        AttributeInstance health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (health == null) return;
        BruteConfig config = PluginConfigs.getOrderConfig().getBruteConfig();
        if (health.getBaseValue() < config.getStartingHealth()) health.setBaseValue(config.getStartingHealth());
    }
}
