package net.thedudemc.endure.spells;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.PluginSpells;
import org.bukkit.event.Listener;

import java.lang.reflect.Type;

public abstract class Spell implements Listener {

    @Expose private final String name;
    @Expose private final int cost;

    public Spell(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public abstract void cast(SurvivorEntity survivor);

    public static class SpellDeserializer implements JsonDeserializer<Spell> {

        @Override
        public Spell deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            return PluginSpells.get(name);
        }
    }
}
