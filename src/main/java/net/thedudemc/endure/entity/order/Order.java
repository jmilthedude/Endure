package net.thedudemc.endure.entity.order;

import net.thedudemc.dudeconfig.config.Config;
import org.bukkit.entity.Player;

public abstract class Order {

    private final String name;
    private final Config config;

    public <C extends Config> Order(String name, C config) {
        this.name = name;
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public <C extends Config> C getConfig() {
        return (C) config;
    }

    public abstract void tick(Player player);

    public abstract void apply(Player player);

}
