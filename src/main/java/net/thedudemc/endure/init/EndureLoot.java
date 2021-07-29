package net.thedudemc.endure.init;

import net.thedudemc.endure.loot.CommonLootTable;
import net.thedudemc.endure.loot.base.LootTable;
import net.thedudemc.endure.loot.UncommonLootTable;

public class EndureLoot {

    public static LootTable COMMON;
    public static LootTable UNCOMMON;

    public static void register() {
        COMMON = new CommonLootTable().read();
        UNCOMMON = new UncommonLootTable().read();
    }
}
