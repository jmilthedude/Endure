package net.thedudemc.endure.init;

import net.thedudemc.endure.loot.CommonLootTable;
import net.thedudemc.endure.loot.UncommonLootTable;
import net.thedudemc.endure.loot.base.LootTable;
import net.thedudemc.endure.util.Logger;

public class PluginLoot {

    public static LootTable COMMON;
    public static LootTable UNCOMMON;

    public static void register() {
        COMMON = new CommonLootTable().read();
        UNCOMMON = new UncommonLootTable().read();

        Logger.info("Loot registered.");
    }
}
