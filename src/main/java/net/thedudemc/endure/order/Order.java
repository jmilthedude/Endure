package net.thedudemc.endure.order;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import net.thedudemc.endure.init.PluginOrders;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;

public abstract class Order {

    @Expose private String name;

    public Order(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void tick(Player player);

    public abstract void apply(Player player);


    public static class OrderDeserializer implements JsonDeserializer<Order> {

        @Override
        public Order deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            return PluginOrders.get(name).get();
        }
    }

}
