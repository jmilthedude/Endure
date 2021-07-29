package net.thedudemc.endure.loot;

import net.thedudemc.endure.loot.base.LootItem;
import net.thedudemc.endure.loot.base.LootTable;
import net.thedudemc.endure.loot.base.WeightedList;

public class CommonLootTable extends LootTable {
    @Override
    public String getName() {
        return "Common";
    }

    @Override
    protected void reset() {
        loot.add(new WeightedList.Entry<>(new LootItem("dinky_sword", 1), 10));
        loot.add(new WeightedList.Entry<>(new LootItem("empty_bottle", 1), 5));
    }


}
