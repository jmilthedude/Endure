package net.thedudemc.endure.world.data;

import com.google.gson.annotations.Expose;
import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.EndureData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class SurvivorsData extends Data {

    @Expose private final HashMap<UUID, SurvivorEntity> survivors = new HashMap<>();
    private final HashSet<UUID> onlineSurvivors = new HashSet<>();

    @Override
    public String getName() {
        return "Survivors";
    }

    @Override
    protected void reset() {
    }

    public SurvivorsData tick() {
        this.survivors.values().forEach(survivor -> {
            if (isOnline(survivor.getId())) survivor.tick();
        });
        return this;
    }

    public SurvivorsData addSurvivor(UUID uuid, SurvivorEntity survivor) {
        survivors.put(uuid, survivor);
        markDirty();
        return this;
    }

    public SurvivorEntity getSurvivor(UUID uuid) {
        SurvivorEntity survivor = survivors.computeIfAbsent(uuid, id -> new SurvivorEntity(id, 0, 1.0f, 0.0f));
        markDirty();
        return survivor;
    }

    public static SurvivorsData get() {
        return EndureData.SURVIVORS_DATA;
    }

    public boolean setOnline(UUID uuid) {
        return onlineSurvivors.add(uuid);
    }

    public boolean setOffline(UUID uuid) {
        return onlineSurvivors.remove(uuid);
    }

    public boolean isOnline(UUID uuid) {
        return onlineSurvivors.contains(uuid);
    }

}
