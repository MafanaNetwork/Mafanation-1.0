package me.TahaCheji.Mafana.listeners;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.mobData.MasterBoss;
import me.TahaCheji.Mafana.mobData.MasterMob;
import me.TahaCheji.Mafana.stats.PlayerStats;
import me.TahaCheji.Mafana.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeath implements Listener {


    @EventHandler
    public void onDeath(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Entity entity = e.getDamager();
        Player player = (Player) e.getEntity();
        double damage = e.getDamage();
        if (damage >= player.getHealth()) {
            player.setHealth(100);
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You Died!");
            player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 10, 10);
            player.teleport(Main.getInstance().getLobbyPoint());
            Bukkit.broadcastMessage(player.getDisplayName() + " just died from " + NBTUtils.getEntityString(entity, "MobName"));
            MasterBoss masterBoss = Main.playerBossFight.get(player);
            Main.getInstance().regeneratePlayerStats();
            if (masterBoss != null) {
                masterBoss.killMob();
                player.sendMessage(ChatColor.RED + "You Failed");
                Main.playerBossFight.remove(player, masterBoss);
                for (MasterMob minions : masterBoss.getMinions()) {
                    minions.killMob();
                }
            }
        }
    }

    @EventHandler
    public void onRealDeath(PlayerDeathEvent e) {
        e.setCancelled(true);
    }



}
