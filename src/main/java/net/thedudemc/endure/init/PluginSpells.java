package net.thedudemc.endure.init;

import net.thedudemc.endure.spells.FlameBallSpell;
import net.thedudemc.endure.spells.HealSpell;
import net.thedudemc.endure.spells.Spell;
import net.thedudemc.endure.util.Logger;

import java.util.HashMap;

public class PluginSpells {

    public static HashMap<String, Spell> REGISTRY = new HashMap<>();

    // damage spells
    public static Spell FLAME_BALL;
    public static Spell HEAL_SPELL;

    public static void register() {
        FLAME_BALL = register(new FlameBallSpell("flame_ball", 5));
        HEAL_SPELL = register(new HealSpell("heal", 25));

        Logger.info("Spells registered.");
    }

    private static Spell register(Spell spell) {
        PluginEvents.registerEvent(spell);
        REGISTRY.put(spell.getName(), spell);
        return spell;
    }

    public static Spell get(String name) {
        return REGISTRY.get(name);
    }
}
