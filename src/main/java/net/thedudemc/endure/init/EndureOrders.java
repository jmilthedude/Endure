package net.thedudemc.endure.init;

import net.thedudemc.endure.entity.order.BruteOrder;
import net.thedudemc.endure.entity.order.Order;
import net.thedudemc.endure.entity.order.StalkerOrder;
import net.thedudemc.endure.entity.order.WizardOrder;

import java.util.HashMap;
import java.util.function.Supplier;

public class EndureOrders {
    public static HashMap<String, Supplier<Order>> REGISTRY = new HashMap<>();

    public static void register() {
        REGISTRY.put("brute", BruteOrder::new);
        REGISTRY.put("stalker", StalkerOrder::new);
        REGISTRY.put("wizard", WizardOrder::new);
    }

    public static Supplier<Order> get(String name) {
        return REGISTRY.get(name);
    }
}
