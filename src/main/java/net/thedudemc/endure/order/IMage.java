package net.thedudemc.endure.order;

import net.thedudemc.endure.spells.Spell;

import java.util.List;

public interface IMage {

    void learnSpell(Spell spell);

    int getCurrentMP();

    int getMaxMP();

    void decreaseMP(int amount);

    void increaseMP(int amount);

    void addMaxMP(int amount);

    Spell getCurrentSpell();

    List<Spell> getLearnedSpells();

    default boolean canUseCurrentSpell() {
        if (getCurrentSpell() == null || getLearnedSpells().isEmpty()) return false;
        return getCurrentMP() - this.getCurrentSpell().getCost() >= 0;
    }

    void setCurrentSpell(Spell spell);
}
