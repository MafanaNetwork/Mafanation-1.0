package me.TahaCheji.Mafana.gameMobs.mobs;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.mobData.LootItem;
import me.TahaCheji.Mafana.mobData.MasterMob;
import me.TahaCheji.Mafana.utils.AbilityUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;

public class GolladorMinion extends MasterMob {


    public GolladorMinion() {
        super(ChatColor.BLACK + "GolladorMinion", 100, EntityType.ZOMBIE, 55, 15, 5, 10, 0, null, null);
    }

    @Override
    public void onAbilityHit(Player player, Entity entity) {
        ((LivingEntity) player).removePotionEffect(PotionEffectType.WITHER);
        ((LivingEntity) player).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int) (2 * 20), (int) 2));
    }

    @Override
    public void passiveAbility(Entity entity) {
        new BukkitRunnable() {
            Vector vec = new AbilityUtil().getMobTargetDirection(entity, null);
            Location loc = entity.getLocation().add(0, 2, 0);
            double ti = 0;

            public void run() {
                ti++;
                if (loc.getBlock().getType().isSolid())
                    cancel();

                loc.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_HAT, 2, 1);
                for (int j = 0; j < 2; j++) {
                    loc.add(vec);

                    for (double i = -Math.PI; i < Math.PI; i += Math.PI / 2) {
                        Vector v = new Vector(Math.cos(i + ti / 4), Math.sin(i + ti / 4), 0);
                        ParticleEffect.REDSTONE.display(loc, AbilityUtil.rotateFunc(v, loc), .09f, 0, null, Bukkit.getOnlinePlayers());
                    }
                    for (Entity entity1 : loc.getNearbyEntities(2, 2, 2)) {
                        if (!(entity1 instanceof Player)) {
                            continue;
                        }
                        Player player = ((Player) entity1).getPlayer();
                        ParticleEffect.EXPLOSION_LARGE.display(loc, 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
                        ParticleEffect.FIREWORKS_SPARK.display(loc, 0, 0, 0, .2f, 32, null, Bukkit.getOnlinePlayers());
                        loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
                        player.damage(15);
                        player.setVelocity(player.getLocation().toVector().subtract(loc.toVector()).multiply(.1).setY(.4));
                        cancel();
                        return;
                    }
                    if (ti > 40)
                        cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }
}

