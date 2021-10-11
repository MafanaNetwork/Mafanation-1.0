package me.TahaCheji.Mafana.mobData;

import me.TahaCheji.Mafana.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MobText {

    private final Player player;
    private final LivingEntity livingEntity;
    private final String text;
    private ArmorStand armorStand;
    int taskId;

    public MobText(Player player, LivingEntity livingEntity, String text) {
        this.player = player;
        this.livingEntity = livingEntity;
        this.text = text;
    }

    public void spawnText(int seconds) {
        if (armorStand == null) {
            player.getWorld().spawn(livingEntity.getLocation().add(0, livingEntity.getHeight() + 2, 0), ArmorStand.class, armorStand -> {
                armorStand.setMarker(true);
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.setSmall(true);
                armorStand.setCustomNameVisible(true);
                armorStand.setCustomName(text);
                Main.armorStands.add(armorStand);
                this.armorStand = armorStand;
                player.sendMessage("[" + livingEntity.getCustomName() + "]: " + text);
                taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
                    if (armorStand.isDead() || livingEntity.isDead()) {
                        Bukkit.getScheduler().cancelTask(taskId);
                    }
                    armorStand.teleport(livingEntity.getLocation().add(0, livingEntity.getHeight() + 2, 0));
                }, 0L, 5L); //0 Tick initial delay, 20 Tick (1 Second) between repeats
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    Main.armorStands.add(armorStand);
                    armorStand.remove();
                    Bukkit.getScheduler().cancelTask(taskId);
                }, 20L * seconds);
            });
        }
    }


    public Player getPlayer() {
        return player;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public String getText() {
        return text;
    }
}
