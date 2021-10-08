package me.TahaCheji.Mafana.manager;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.itemData.MasterAbility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.text.DecimalFormat;

public class DamageManager {

    private final Player damager;
    private final LivingEntity target;
    private MasterAbility ability;
    private int hitDamage;
    DecimalFormat format = new DecimalFormat("#.##");

    public DamageManager(Player damager, LivingEntity target, MasterAbility ability) {
        this.damager = damager;
        this.target = target;
        this.ability = ability;
    }

    public DamageManager(Player damager, LivingEntity target, int hitDamage) {
        this.damager = damager;
        this.target = target;
        this.hitDamage = hitDamage;
    }

    public void abilityDamage () {
        if(!canDamage(damager, null, target)) {
            return;
        }
        int damage = ability.getAbilityDamage();
        target.damage(damage);
        Location loc = target.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
        damager.getWorld().spawn(loc, ArmorStand.class, armorStand -> {
            armorStand.setMarker(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(ChatColor.DARK_PURPLE + "✧" + damage);
            Main.armorStands.add(armorStand);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                Main.armorStands.add(armorStand);
                armorStand.remove();
            },20);
        });
    }

    public void hitDamage() {
        if(!canDamage(damager, null, target)) {
            return;
        }
        int damage = hitDamage;
        target.damage(damage);
        Location loc = target.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
        damager.getWorld().spawn(loc, ArmorStand.class, armorStand -> {
            armorStand.setMarker(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(ChatColor.RED + "✧" + damage);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                Main.armorStands.add(armorStand);
                armorStand.remove();
            },20);
        });
        double heath = ((LivingEntity) target).getHealth();
        if(heath <= damage) {
            target.setKiller(damager);
            return;
        }
        heath -= damage;
        target.setCustomName(ChatColor.translateAlternateColorCodes('&',
                target.getName().split(" ")[0] + ChatColor.RED + " ♥" +
                        ChatColor.RED + format.format((int) heath) + ChatColor.RED + "♥"));
    }


    public Player getDamager() {
        return damager;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public MasterAbility getAbility() {
        return ability;
    }

    public int getHitDamage() {
        return hitDamage;
    }

    private double getRandomOffset() {
        double random = Math.random();
        if (Math.random() > 0.5) random *= -1;
        return random;
    }

    public static boolean canDamage(Player player, Location loc, Entity target) {
        if (target.equals(player) || !(target instanceof LivingEntity) || target instanceof ArmorStand || target.isDead())
            return false;

        if (target.hasMetadata("NPC"))
            return false;

        if (target instanceof Player)
            return false;

        return loc == null;
    }

}
