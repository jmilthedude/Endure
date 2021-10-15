package net.thedudemc.endure.world.data;

import com.google.gson.annotations.Expose;
import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.EndureData;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class SurvivorsData extends Data {

    @Expose private final HashMap<UUID, SurvivorEntity> survivors = new HashMap<>();

    @Override
    public String getName() {
        return "Survivors";
    }

    @Override
    public void tick() {
        this.survivors.values().forEach(survivor -> {
            if (survivor.isOnline()) {
                survivor.tick();
            }
        });
    }

    public SurvivorsData addSurvivor(UUID uuid, SurvivorEntity survivor) {
        survivors.put(uuid, survivor);
        markDirty();
        return this;
    }

    public SurvivorEntity getSurvivor(Player player) {
        return this.getSurvivor(player.getUniqueId());
    }

    private SurvivorEntity getSurvivor(UUID uuid) {
        SurvivorEntity survivor = survivors.computeIfAbsent(uuid, id -> new SurvivorEntity(id, 1, 1.0f, 0));
        markDirty();
        return survivor;
    }

    public Collection<SurvivorEntity> getSurvivors() {
        return this.survivors.values();
    }

    public static SurvivorsData get() {
        return EndureData.SURVIVORS_DATA;
    }

}
