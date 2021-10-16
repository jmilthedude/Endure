package net.thedudemc.endure.world.data;

import com.google.gson.annotations.Expose;
import net.thedudemc.endure.entity.EndureEntity;
import net.thedudemc.endure.entity.EndureZombie;
import net.thedudemc.endure.init.PluginData;

import java.util.*;
import java.util.stream.Collectors;

public class EntitiesData extends net.thedudemc.endure.world.data.Data {

    @Expose private final HashMap<UUID, List<EndureZombie>> entities = new HashMap<>();

    @Override
    public String getName() {
        return "Entities";
    }

    @Override
    public void tick() {
        this.entities.values().forEach(endureEntities -> endureEntities.forEach(EndureEntity::tick));
    }

    public EntitiesData addEntity(UUID playerId, EndureEntity entity) {
        this.getEntities(playerId).add((EndureZombie) entity);
        this.markDirty();
        return this;
    }

    public List<EndureZombie> getEntities(UUID playerId) {
        List<EndureZombie> entities = this.entities.computeIfAbsent(playerId, id -> new ArrayList<>());
        markDirty();
        return entities;
    }

    public void removeEntities(UUID playerId, List<EndureZombie> invalid) {
        this.getEntities(playerId).removeAll(invalid);
        markDirty();
    }

    public static EntitiesData get() {
        return PluginData.ENTITIES_DATA;
    }

    public EndureEntity getEntity(UUID id) {
        Optional<EndureZombie> entityOptional = this.entities.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .stream().filter(endureZombie -> endureZombie.getId().equals(id))
                .collect(Collectors.toList())
                .stream().findAny();

        return entityOptional.orElse(null);
    }

}
