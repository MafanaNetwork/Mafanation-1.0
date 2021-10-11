package me.TahaCheji.Mafana.mobData;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.utils.NBTUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Arrays;
import java.util.Objects;

public class DropLoot implements Listener {


    @EventHandler
    public void onKill(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        Player player = e.getEntity().getKiller();
        e.getDrops().clear();
        e.setDroppedExp(0);
        if(player == null) {
            return;
        }
        for(MasterMob mob : Main.gameMobs) {
            if(entity instanceof Player) {
                continue;
            }
            if(entity.getCustomName() == null) {
                continue;
            }
            NBTCompound nbt = new NBTEntity(entity).getPersistentDataContainer();
            if(!(nbt.hasKey("MobName"))) {
                continue;
            }
            if(!(NBTUtils.getEntityString(entity, "MobName").equalsIgnoreCase(mob.getName()))) {
                continue;
            }
            if(mob.getLootTable() == null) {
                continue;
            }
            mob.tryDropLoot(entity.getLocation(), player);
            Main.activeMobs.remove(mob);
        }

        for(MasterBoss mob : Main.gameBosses) {
            if(entity instanceof Player) {
                continue;
            }
            if(entity.getCustomName() == null) {
                continue;
            }
            NBTCompound nbt = new NBTEntity(entity).getPersistentDataContainer();
            if(!(nbt.hasKey("MobName"))) {
                continue;
            }
            if(!(NBTUtils.getEntityString(entity, "MobName").equalsIgnoreCase(mob.getName()))) {
                continue;
            }
            if(mob.getLootTable() == null) {
                continue;
            }
            for(MasterMob minions : mob.getMinions()) {
                minions.killMob();
            }
            mob.onDeath(player, entity);
            mob.tryDropLoot(entity.getLocation(), player);
            Main.activeBoss.remove(mob);
        }
    }



}
