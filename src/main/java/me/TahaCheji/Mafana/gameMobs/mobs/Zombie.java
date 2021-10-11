package me.TahaCheji.Mafana.gameMobs.mobs;

import me.TahaCheji.Mafana.mobData.LootItem;
import me.TahaCheji.Mafana.mobData.MasterMob;
import me.TahaCheji.Mafana.mobData.MasterSpawn;
import me.TahaCheji.Mafana.mobData.MobText;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Zombie extends MasterMob {

    public Zombie() {
        super(ChatColor.GREEN + "Zombie", 100, EntityType.ZOMBIE, 25, 10,
                5, 10, 0, null, null, new LootItem(new ItemStack(Material.ROTTEN_FLESH), 1, 3, 100));
        setMasterSpawn(new MasterSpawn(new Location(Bukkit.getWorld("world"), 0, 64, 0), this));
    }

    @Override
    public void onAbilityHit(Player player, Entity entity) {
    }

    @Override
    public void passiveAbility(Entity entity) {

    }
}
