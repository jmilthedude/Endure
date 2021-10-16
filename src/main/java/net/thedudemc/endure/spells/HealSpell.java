package net.thedudemc.endure.spells;

import net.thedudemc.endure.entity.SurvivorEntity;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class HealSpell extends Spell {

    public HealSpell(String name, int cost) {
        super(name, cost);
    }

    @Override
    public void cast(SurvivorEntity survivor) {
        Player p = survivor.getPlayer();
        AttributeInstance health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (health == null) return;

        p.setHealth(Math.min(p.getHealth() + 2.0f, health.getValue()));
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, .7f, 1.5f);
    }
}
