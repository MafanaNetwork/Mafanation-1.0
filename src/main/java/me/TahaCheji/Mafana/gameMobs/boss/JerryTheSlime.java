package me.TahaCheji.Mafana.gameMobs.boss;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.mobData.LootItem;
import me.TahaCheji.Mafana.mobData.MasterBoss;
import me.TahaCheji.Mafana.mobData.MobText;
import me.TahaCheji.Mafana.mobData.MobUtil;
import me.TahaCheji.Mafana.utils.AbilityUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JerryTheSlime extends MasterBoss {


    public JerryTheSlime() {
        super(ChatColor.GREEN + "JerryTheSlime", 100, 2000, EntityType.SLIME, 100, 0, 5, 0, null, null);
        setLootTable(new LootItem(new ItemStack(Material.SLIME_BLOCK), 1, 1, 1));
    }

    @Override
    public void onSpawn(Player player, Entity entity) {
        if(!(entity instanceof Slime)) {
            return;
        }
        BossBar bossBar = MobUtil.createBossBar(Main.getInstance(), (LivingEntity) entity, getName(), BarColor.GREEN, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
        bossBar.addPlayer(player);
        Slime slime = (Slime) entity;
        new MobText(player, (LivingEntity) entity, ChatColor.GREEN + "GROW!");
        slime.setWander(false);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            slime.setSize(3);
        }, 250);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            slime.setSize(10);
        }, 500);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            slime.setSize(25);
        }, 750);
        slime.setWander(true);
    }

    @Override
    public void getHit(Player player, Entity entity) {

    }

    @Override
    public void onAbilityHit(Player player, Entity entity) {
        player.setVelocity(player.getLocation().toVector().subtract(player.getLocation().toVector()).multiply(.1).setY(.4));
        ((LivingEntity) player).removePotionEffect(PotionEffectType.SLOW);
        ((LivingEntity) player).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) (2 * 20), (int) 2));
    }

    @Override
    public void passiveAbility(Entity entity) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for (Entity newEntity : entity.getLocation().getNearbyEntities(5, 5, 5)) {
                if (!(newEntity instanceof Player)) {
                    continue;
                }
                Player player = (Player) newEntity;
                if (player.isDead() || entity.isDead()) {
                    continue;
                }
                Location loc = entity.getLocation();

                new MobText(player, (LivingEntity) entity, ChatColor.GREEN + "Stomp!");
                double radius = 5;
                loc.add(0, -1, 0);
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SLIME_BLOCK_HIT, 1, 0);
                for (double j = 0; j < Math.PI * 2; j += Math.PI / 36) {
                    Location loc1 = loc.clone().add(Math.cos(j) * radius, 1, Math.sin(j) * radius);
                    double y_max = .5 + new Random().nextDouble();
                    for (double y = 0; y < y_max; y += .1) {
                        Location loc2 = loc1.clone();
                        loc2.add(0, y, 0);
                        ParticleEffect.REDSTONE.display(loc2, Color.GREEN);
                    }
                }
                for (Entity entity1 : loc.getNearbyEntities(2, 2, 2)) {
                    if (!(entity1 instanceof Player)) {
                        continue;
                    }
                    Player player1 = ((Player) entity1).getPlayer();
                    if (player1 == null) {
                        continue;
                    }
                    player.damage(5);
                    return;
                }
            }
        }, 250);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for (Entity newEntity : entity.getLocation().getNearbyEntities(5, 5, 5)) {
                if (!(newEntity instanceof Player)) {
                    continue;
                }
                Player player = (Player) newEntity;
                if (player.isDead() || entity.isDead()) {
                    continue;
                }
                Vector dir = new AbilityUtil().getMobTargetDirection(entity, null).multiply(.4);
                Location loc = entity.getLocation().add(0, entity.getHeight() + 2, 0);
                FallingBlock pum = player.getWorld().spawnFallingBlock(loc, Material.SLIME_BLOCK, (byte) 0);
                pum.setVelocity(dir);
                pum.setDropItem(false);
                pum.setHurtEntities(true);
                for(Entity entity1 : pum.getNearbyEntities(3, 3, 3)) {
                    if(!(entity1 instanceof Player)) {
                        continue;
                    }
                    ((Player) entity1).damage(5);
                }
            }
        }, 100);
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

    @Override
    public void onDeath(Player player, Entity entity) {

    }


}
