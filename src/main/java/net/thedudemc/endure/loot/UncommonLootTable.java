package net.thedudemc.endure.loot;

import net.thedudemc.endure.loot.base.LootItem;
import net.thedudemc.endure.loot.base.LootTable;
import net.thedudemc.endure.loot.base.WeightedList;

public class UncommonLootTable extends LootTable {
    @Override
    public String getName() {
        return "Uncommon";
    }

    @Override
    protected void reset() {
        loot.add(new WeightedList.Entry<>(new LootItem("empty_bottle", 1), 10));
        loot.add(new WeightedList.Entry<>(new LootItem("diamond", 1), 5));
    }
}
