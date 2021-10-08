package me.TahaCheji.Mafana.listeners;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.itemData.itemAttribute.UndeadAttributeUtl;
import me.TahaCheji.Mafana.manager.DamageManager;
import me.TahaCheji.Mafana.playerData.playerInfo.playerMostDamage;
import me.TahaCheji.Mafana.stats.*;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.io.IOException;
import java.text.DecimalFormat;

public class PlayerDamage implements Listener {


    DecimalFormat format = new DecimalFormat("#.##");

    Main plugin = Main.getPlugin(Main.class);


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) throws IOException {
        if (!(e.getEntity() instanceof LivingEntity)) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (e.getEntity() instanceof ArmorStand) {
            e.setCancelled(true);
            return;
        }
        Player player = (Player) e.getDamager();
        double baseDamage = PlayerStats.getStrength(player) * .2;
        double defense = ((LivingEntity) e.getEntity()).getAttribute(Attribute.GENERIC_ARMOR).getValue();
        double realDamage = baseDamage * (100 / (100 + defense));
        UndeadAttributeUtl.onHit(e);
        playerMostDamage.onHit(e);
        new DamageManager(player, (LivingEntity) e.getEntity(), (int) realDamage).hitDamage();
        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BREAK, 10, 10);
    }



    /*
    @EventHandler
    public void entityDamageEvent(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            return;
        }
      double damageTaken = e.getDamage();
        double heath = ((LivingEntity) e.getEntity()).getHealth();
        heath -= damageTaken;
        e.getEntity().setCustomNameVisible(true);
        e.getEntity().setCustomName(ChatColor.translateAlternateColorCodes('&',
                e.getEntity().getName().split(" ")[0] + ChatColor.RED + " ♥" +
                        ChatColor.RED + format.format((int) heath) + ChatColor.RED + "♥"));
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
            com.gmail.filoghost.holograms.api.Hologram h = HolographicDisplaysAPI.createHologram
                    (Main.getInstance(), e.getEntity().getLocation().add(getRandomOffset(), 1, getRandomOffset()), ChatColor.RED + "✧" + ChatColor.GOLD + format.format(damageTaken) + ChatColor.RED + "✧");
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                h.delete();
            }, 20); // Time in ticks (20 ticks = 1 second)
        }
        if(e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) {
            com.gmail.filoghost.holograms.api.Hologram h = HolographicDisplaysAPI.createHologram
                    (Main.getInstance(), e.getEntity().getLocation().add(getRandomOffset(), 1, getRandomOffset()), ChatColor.AQUA + "✧" + ChatColor.GOLD + format.format(damageTaken) + ChatColor.RED + "✧");
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                h.delete();
            }, 20); // Time in ticks (20 ticks = 1 second)
        }
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
            com.gmail.filoghost.holograms.api.Hologram h = HolographicDisplaysAPI.createHologram
                    (Main.getInstance(), e.getEntity().getLocation().add(getRandomOffset(), 1, getRandomOffset()), ChatColor.RED + "✧" + ChatColor.DARK_GRAY + format.format(damageTaken) + ChatColor.RED + "✧");
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                h.delete();
            }, 20); // Time in ticks (20 ticks = 1 second)
        }
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            com.gmail.filoghost.holograms.api.Hologram h = HolographicDisplaysAPI.createHologram
                    (Main.getInstance(), e.getEntity().getLocation().add(getRandomOffset(), 1, getRandomOffset()), ChatColor.RED + "✧" + ChatColor.BLACK + format.format(damageTaken) + ChatColor.RED + "✧");
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                h.delete();
            }, 20); // Time in ticks (20 ticks = 1 second)
        }

    }




    @EventHandler
    public void onArrowBlood(EntityDamageByEntityEvent e){
        if (!(e.getDamager() instanceof Arrow)){
            return;
        }
        if(!(e.getEntity() instanceof LivingEntity)) {
            return;
        }
        Arrow a = (Arrow) e.getDamager();
        LivingEntity shooter = (LivingEntity) a.getShooter();
        Player p = (Player) shooter;

        if (!(shooter instanceof Player)) {
            return;
        }
        if (!(p.getInventory().getItemInHand().getType().equals(Material.BOW))) {
            return;
        }
        if(!(NBTUtils.getString(p.getItemInHand(), "ItemType").contains("Bow"))) {
            return;
        }
        Entity en = e.getEntity();
        Player player = (Player) e.getDamager();
        double baseDamage = PlayerStats.getStrength(player) * .2;
        double defense = ((LivingEntity) e.getEntity()).getAttribute(Attribute.GENERIC_ARMOR).getValue();
        double realDamage = baseDamage *(100/(100+defense));
        e.setDamage(realDamage);
        double x = e.getEntity().getLocation().getX();
        double y = e.getEntity().getLocation().getY() + player.getEyeHeight();
        double z = e.getEntity().getLocation().getZ();
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(100, 0, 0), 1);
        player.spawnParticle(Particle.REDSTONE, x,y,z, 25, dustOptions);
        player.playSound(player.getLocation(), Sound.BLOCK_STONE_BREAK, 10, 10);
        com.gmail.filoghost.holograms.api.Hologram h = HolographicDisplaysAPI.createHologram
                (Main.getInstance(), en.getLocation().add(getRandomOffset(), 1, getRandomOffset()),  ChatColor.WHITE + "✧" + ChatColor.WHITE  + format.format(realDamage) + ChatColor.WHITE + "✧" );
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            h.delete();
        }, 20); // Time in ticks (20 ticks = 1 second)
    }

     */

    private double getRandomOffset() {
        double random = Math.random();
        if (Math.random() > 0.5) random *= -1;
        return random;
    }
}







