package net.thedudemc.endure.loot.base;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class WeightedList<T> extends AbstractList<WeightedList.Entry<T>> implements RandomListAccess<T> {

    @Expose private final List<Entry<T>> entries = new ArrayList<>();

    public WeightedList() {}

    public WeightedList(Map<T, Integer> map) {
        this();
        map.forEach(this::add);
    }

    public WeightedList<T> add(T value, int weight) {
        this.add(new Entry<>(value, weight));
        return this;
    }

    @Override
    public int size() {
        return this.entries.size();
    }

    @Override
    public Entry<T> get(int index) {
        return this.entries.get(index);
    }

    @Override
    public boolean add(Entry<T> entry) {
        return this.entries.add(entry);
    }

    @Override
    public Entry<T> remove(int index) {
        return this.entries.remove(index);
    }

    public int getTotalWeight() {
        return this.entries.stream().mapToInt(entry -> entry.weight).sum();
    }

    @Override
    @Nullable
    public T getRandom(Random random) {
        int totalWeight = this.getTotalWeight();
        if (totalWeight <= 0) {
            return null;
        }
        return this.getWeightedAt(random.nextInt(totalWeight));
    }

    private T getWeightedAt(int index) {
        for (Entry<T> e : this.entries) {
            index -= e.weight;
            if (index < 0) {
                return e.value;
            }
        }
        return null;
    }

    public WeightedList<T> copy() {
        WeightedList<T> copy = new WeightedList<>();
        this.entries.forEach(entry -> copy.add(entry.value, entry.weight));
        return copy;
    }

    public WeightedList<T> copyFiltered(Predicate<T> filter) {
        WeightedList<T> copy = new WeightedList<>();
        this.entries.forEach(entry -> {
            if (filter.test(entry.value)) {
                copy.add(entry);
            }
        });
        return copy;
    }

    public boolean containsValue(T value) {
        return this.stream().map(entry -> entry.value).anyMatch(t -> t.equals(value));
    }

    @Override
    public void forEach(BiConsumer<T, Number> weightEntryConsumer) {
        this.forEach(entry -> weightEntryConsumer.accept(entry.value, entry.weight));
    }

    public static class Entry<T> {

        @Expose public T value;
        @Expose public int weight;

        public Entry(T value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }

}
