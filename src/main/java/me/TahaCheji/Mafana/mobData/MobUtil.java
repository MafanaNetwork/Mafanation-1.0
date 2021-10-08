package me.TahaCheji.Mafana.mobData;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.utils.NBTUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MobUtil implements Listener {


    public static MasterMob getMob(String name) {
        MasterMob gameMob = null;
        for (MasterMob createMob : Main.gameMobs) {
            MasterMob mob = createMob.getMob(name);
            if (mob == null) {
                continue;
            }
            gameMob = mob;
        }
        return gameMob;
    }

    public static MasterBoss getBoss(String name) {
        MasterBoss gameMob = null;
        for (MasterBoss createMob : Main.gameBosses) {
            MasterBoss mob = createMob.getMob(name);
            if (mob == null) {
                continue;
            }
            gameMob = mob;
        }
        return gameMob;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        LivingEntity entity = (LivingEntity) e.getDamager();
        Player player = (Player) e.getEntity();
        for (MasterMob mob : Main.activeMobs) {
            if (entity instanceof Player) {
                continue;
            }
            if (entity.getCustomName() == null) {
                continue;
            }
            NBTCompound nbt = new NBTEntity(entity).getPersistentDataContainer();
            if (!(nbt.hasKey("MobName"))) {
                continue;
            }
            if (!(NBTUtils.getEntityString(entity, "MobName").equalsIgnoreCase(mob.getName()))) {
                continue;
            }
            mob.onAbilityHit(player, entity);
        }
    }

    @EventHandler
    public void getHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        LivingEntity entity = (LivingEntity) e.getEntity();
        Player player = (Player) e.getDamager();
        for (MasterBoss mob : Main.activeBoss) {
            if (entity instanceof Player) {
                continue;
            }
            if (entity.getCustomName() == null) {
                continue;
            }
            NBTCompound nbt = new NBTEntity(entity).getPersistentDataContainer();
            if (!(nbt.hasKey("MobName"))) {
                continue;
            }
            if (!(NBTUtils.getEntityString(entity, "MobName").equalsIgnoreCase(mob.getName()))) {
                continue;
            }
            if(!mob.stageOne) {
                mob.stageOne(player, entity, (int) entity.getHealth());
            }
            if(!mob.stageTwo && mob.stageOne) {
                mob.stageTwo(player, entity, (int) entity.getHealth());
            }
            if(!mob.stageThree && mob.stageTwo && mob.stageOne) {
                mob.stageThree(player, entity, (int) entity.getHealth());
            }
        }
    }


}
