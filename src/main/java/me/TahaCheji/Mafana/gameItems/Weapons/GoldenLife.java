package me.TahaCheji.Mafana.gameItems.Weapons;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.crafting.MasterTable;
import me.TahaCheji.Mafana.gameItems.Material.GoldenBlock;
import me.TahaCheji.Mafana.itemData.*;
import me.TahaCheji.Mafana.manager.DamageManager;
import me.TahaCheji.Mafana.stats.PlayerStats;
import me.TahaCheji.Mafana.utils.AbilityUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;
import java.util.Random;

public class GoldenLife extends MasterItems {


    public GoldenLife() {
        super(Material.GOLDEN_PICKAXE, "GoldenLife", ItemType.SWORD, RarityType.GOLD, null, 15,
                10, 0, 0, true, true,
                new MasterAbility("GoldenTrust", AbilityType.RIGHT_CLICK,15, 35), new ItemValueUtl(1500, true),
                "Legend has it Midas himself was scared of this weapon.");
        setRecipe(new MasterTable(new GoldenBlock().getItem(), 2, null, 1, null, 1, null, 1, null, 1,
                null, 1, null, 1, null ,1, null, 1, getItem()));
    }

    @Override
    public void onItemStackCreate(ItemStack var1) {

    }

    @Override
    public boolean leftClickAirAction(Player var1, ItemStack var2) {
        return false;
    }

    @Override
    public boolean leftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean rightClickAirAction(Player player, ItemStack var2) {
        /*
        PlayerStats playerStats = PlayerStats.playerStats.get(player.getUniqueId());
        if (!(playerStats.getCurrentIntelligence() > getMasterAbility().getManaCost())) {
            player.sendMessage(ChatColor.RED + "You do not have the mana to use this ability");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_HURT, 10, 1);
        } else {
            new AbilityUtil().sendAbility(player, getMasterAbility());
            playerStats.setCurrentIntelligence(playerStats.getCurrentIntelligence() - getMasterAbility().getManaCost());
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 1);
            new BukkitRunnable() {
                Vector vec = new AbilityUtil().getTargetDirection(player, null).multiply(.8);
                Location loc = player.getEyeLocation();
                int ti = 0;

                public void run() {
                    ti++;
                    if (ti > 20)
                        cancel();

                    loc.getWorld().playSound(loc, Sound.BLOCK_FIRE_AMBIENT, 2, 1);
                    for (int j = 0; j < 2; j++) {
                        loc.add(vec);
                        if (loc.getBlock().getType().isSolid())
                            cancel();

                        ParticleEffect.FLAME.display(loc, .12f, .12f, .12f, 0, 5, null, Bukkit.getOnlinePlayers());
                        if (new Random().nextDouble() < .3)
                            ParticleEffect.LAVA.display(loc, 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
                        for (Entity target : loc.getNearbyEntities(2, 2, 2)) {
                            if (target.equals(player) || !(target instanceof LivingEntity)) {
                                continue;
                            }
                            ParticleEffect.LAVA.display(loc, 0, 0, 0, 0, 8, null, Bukkit.getOnlinePlayers());
                            ParticleEffect.FLAME.display(loc, 0, 0, 0, .1f, 32, null, Bukkit.getOnlinePlayers());
                            ParticleEffect.EXPLOSION_LARGE.display(loc, 0, 0, 0, 0, 1, null, Bukkit.getOnlinePlayers());
                            loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
                            new DamageManager(player, (LivingEntity) target, getMasterAbility()).abilityDamage();
                            target.setFireTicks((int) 3 * 20);
                            cancel();
                            return;
                        }
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0, 1);
        }

         */
        return false;
    }

    @Override
    public boolean rightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean shiftLeftClickAirAction(Player var1, ItemStack var2) {
        return false;
    }

    @Override
    public boolean shiftLeftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean shiftRightClickAirAction(Player var1, ItemStack var2) {
        rightClickAirAction(var1, var2);
        return false;
    }

    @Override
    public boolean shiftRightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean middleClickAction(Player var1, ItemStack var2) {
        return false;
    }

    @Override
    public boolean hitEntityAction(Player var1, EntityDamageByEntityEvent var2, Entity var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean breakBlockAction(Player var1, BlockBreakEvent var2, Block var3, ItemStack var4) {
        return false;
    }

    @Override
    public boolean clickedInInventoryAction(Player var1, InventoryClickEvent var2, ItemStack var3, ItemStack var4) {
        return false;
    }
}
