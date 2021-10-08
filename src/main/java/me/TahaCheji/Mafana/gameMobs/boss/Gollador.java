package me.TahaCheji.Mafana.gameMobs.boss;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.gameItems.Material.GolladorStand;
import me.TahaCheji.Mafana.mobData.LootItem;
import me.TahaCheji.Mafana.mobData.MasterBoss;
import me.TahaCheji.Mafana.mobData.MasterMob;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.ParticleColor;

import java.awt.*;
import java.awt.Color;
import java.util.Random;

public class Gollador extends MasterBoss {

    public Gollador() {
        super(ChatColor.RED + "Gollador", 100, EntityType.WITHER_SKELETON, 150, 50, 25, 5, 0, null, null);
        setLootTable(new LootItem(new GolladorStand().getItem(), 100));
    }

    @Override
    public void onAbilityHit(Player player, Entity entity) {
        ((LivingEntity) player).removePotionEffect(PotionEffectType.WITHER);
        ((LivingEntity) player).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) (2 * 20), (int) 2));
    }

    @Override
    public void passiveAbility(Entity entity) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for(Entity newEntity : entity.getLocation().getNearbyEntities(5, 5, 5)) {
                if(!(newEntity instanceof Player)) {
                    continue;
                }
                Player player = (Player) newEntity;
                if(player.isDead()) {
                    continue;
                }
                Location loc = player.getLocation();

                double radius = 2.7;

                loc.add(0, -1, 0);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 1, 0);
                for (double j = 0; j < Math.PI * 2; j += Math.PI / 36) {
                    Location loc1 = loc.clone().add(Math.cos(j) * radius, 1, Math.sin(j) * radius);
                    double y_max = .5 + new Random().nextDouble();
                    for (double y = 0; y < y_max; y += .1) {
                        Location loc2 = loc1.clone();
                        loc2.add(0, y, 0);
                        ParticleEffect.REDSTONE.display(loc2, Color.BLACK);
                    }
                }
                player.damage(5);
                player.removePotionEffect(PotionEffectType.WITHER);
                player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) (2 * 20), (int) 2));
            }
        }, 250);
    }

    @Override
    public void stageOne(Player player, Entity entity, int health) {

    }

    @Override
    public void stageTwo(Player player, Entity entity, int health) {

    }

    @Override
    public void stageThree(Player player, Entity entity, int health) {

    }


}
