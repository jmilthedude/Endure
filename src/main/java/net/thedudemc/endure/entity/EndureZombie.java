package net.thedudemc.endure.entity;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;

public class EndureZombie extends EndureEntity {

    public EndureZombie(LivingEntity entity, int playerLevel) {
        super(entity, playerLevel);

        entity.setGlowing(true);
        if (((Ageable) entity).isAdult()) {
            AttributeInstance speed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            if (speed != null) speed.setBaseValue(speed.getBaseValue() * 1.5);
        }

    }

    @Override
    public void tick() {

    }
}
