package net.thedudemc.endure.world.data;

import com.google.gson.annotations.Expose;
import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.EndureData;

import java.util.HashMap;
import java.util.UUID;

public class SurvivorsData extends Data {

    @Expose private final HashMap<UUID, SurvivorEntity> survivors = new HashMap<>();

    @Override
    public String getName() {
        return "Survivors";
    }

    @Override
    protected void reset() {
    }

    public SurvivorsData tick() {
        this.survivors.values().forEach(survivor -> {
            if (survivor.isOnline()) {
                survivor.tick();
            }
        });
        return this;
    }

    public SurvivorsData addSurvivor(UUID uuid, SurvivorEntity survivor) {
        survivors.put(uuid, survivor);
        markDirty();
        return this;
    }

    public SurvivorEntity getSurvivor(UUID uuid) {
        SurvivorEntity survivor = survivors.computeIfAbsent(uuid, id -> new SurvivorEntity(id, 0, 1.0f, 0));
        markDirty();
        return survivor;
    }

    public static SurvivorsData get() {
        return EndureData.SURVIVORS_DATA;
    }

}
