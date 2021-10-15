package net.thedudemc.endure.entity;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;

public class EndureZombie extends EndureEntity {

    public EndureZombie(LivingEntity entity, int playerLevel) {
        super(entity, playerLevel);

        if (entity instanceof Ageable && ((Ageable) entity).isAdult()) {
            AttributeInstance speed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            if (speed != null) speed.setBaseValue(speed.getBaseValue() * 1.5);
        }

    }
}
