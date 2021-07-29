package net.thedudemc.endure.loot.base;

import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.BiConsumer;

public interface RandomListAccess<T> {

    @Nullable
    public T getRandom(Random random);

    public void forEach(BiConsumer<T, Number> weightEntryConsumer);

}
