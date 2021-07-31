package net.thedudemc.endure.event;

import net.thedudemc.endure.entity.SurvivorEntity;
import net.thedudemc.endure.init.EndureAttributes;
import net.thedudemc.endure.init.EndureItems;
import net.thedudemc.endure.item.EndureItem;
import net.thedudemc.endure.item.attributes.AttributeModifier;
import net.thedudemc.endure.world.data.SurvivorsData;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AttackEvent implements Listener {

    private static final Set<UUID> attacked = new HashSet<>();

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;

        Player player = (Player) event.getDamager();
        LivingEntity target = (LivingEntity) event.getEntity();

        ItemStack stack = player.getInventory().getItemInMainHand();
        EndureItem item = EndureItems.getItemFromStack(stack);

        double damage = 0;

        if (item != null) {

            AttributeModifier damageModifier = item.getAttributeModifier(EndureAttributes.BASE_DAMAGE);
            if (damageModifier != null) {
                damage += damageModifier.getAmount();
            }

            AttributeModifier bonusDamage = item.getAttributeModifier(EndureAttributes.BONUS_DAMAGE);
            if (bonusDamage != null) {
                damage += bonusDamage.getAmount();
            }

            event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0); // we do our own armor calculations

            damage += getStrengthIncrease(player);
            damage *= getCritIncrease(player, target);
            damage *= getArmorReduction(target, damage);
            damage *= getEnchantmentReduction(target);

            event.setDamage(damage * player.getAttackCooldown());
        }
        System.out.println(damage * player.getAttackCooldown());
    }

    private double getArmorReduction(LivingEntity target, double damage) {
        double armor = 0;
        double toughness = 0;
        AttributeInstance armorAttribute = target.getAttribute(Attribute.GENERIC_ARMOR);
        if (armorAttribute != null) {
            armor = armorAttribute.getValue();
        }
        AttributeInstance toughnessAttribute = target.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
        if (toughnessAttribute != null) {
            toughness = toughnessAttribute.getValue();
        }
        return (1d - (Math.min(20d, Math.max(armor / 5d, armor - damage / (2d + (toughness / 4d)))) / 25d));
    }

    private double getEnchantmentReduction(LivingEntity target) {
        EntityEquipment equipment = target.getEquipment();
        double protection = 0;
        if (equipment != null) {
            ItemStack helm = equipment.getHelmet();
            ItemStack chest = equipment.getChestplate();
            ItemStack legs = equipment.getLeggings();
            ItemStack boot = equipment.getBoots();
            if (helm != null) protection += helm.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            if (chest != null) protection += chest.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            if (legs != null) protection += legs.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            if (boot != null) protection += boot.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        }
        return (1d - (Math.min(20.0d, protection) / 25d));
    }

    private double getStrengthIncrease(Player player) {
        PotionEffect effect = player.getPotionEffect(PotionEffectType.INCREASE_DAMAGE);
        if (effect != null) {
            return 3 * (effect.getAmplifier() + 1);
        }
        return 0;
    }

    private double getCritIncrease(Player player, LivingEntity target) {
        return isCrit(player, target) ? 1.5d : 1;
    }

    private boolean isCrit(Player player, LivingEntity target) {
        boolean crit = player.getFallDistance() > 0.0F &&
                !player.isOnGround() &&
                !player.isClimbing() &&
                !player.isInWater() &&
                !player.hasPotionEffect(PotionEffectType.BLINDNESS) &&
                !player.isInsideVehicle();
        if (crit) {
            player.spawnParticle(
                    Particle.CRIT,
                    new Location(target.getWorld(),
                            target.getLocation().getX(),
                            target.getLocation().getY() + 1.5d,
                            target.getLocation().getZ()),
                    10);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, .75f, 1.0f);
        }
        return crit;
    }


    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player p = event.getEntity().getKiller();
        if (p == null) return;
        SurvivorEntity survivor = SurvivorsData.get().getSurvivor(p.getUniqueId());
        survivor.addExperience(100);
    }
}
