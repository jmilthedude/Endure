package net.thedudemc.endure.init;

import net.thedudemc.endure.order.BruteOrder;
import net.thedudemc.endure.order.MageOrder;
import net.thedudemc.endure.order.Order;
import net.thedudemc.endure.order.StalkerOrder;
import net.thedudemc.endure.util.Logger;

import java.util.HashMap;
import java.util.function.Supplier;

public class PluginOrders {
    public static HashMap<String, Supplier<Order>> REGISTRY = new HashMap<>();

    public static Supplier<Order> BRUTE;
    public static Supplier<Order> MAGE;
    public static Supplier<Order> STALKER;

    public static void register() {
        BRUTE = registerOrder("brute", BruteOrder::new);
        MAGE = registerOrder("mage", MageOrder::new);
        STALKER = registerOrder("stalker", StalkerOrder::new);

        Logger.info("Orders registered.");
    }

    private static Supplier<Order> registerOrder(String name, Supplier<Order> order) {
        REGISTRY.put(name, order);
        return order;
    }

    public static Supplier<Order> get(String name) {
        return REGISTRY.get(name);
    }
}
