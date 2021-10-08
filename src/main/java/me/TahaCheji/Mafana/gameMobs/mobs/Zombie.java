package me.TahaCheji.Mafana.gameMobs.mobs;

import me.TahaCheji.Mafana.mobData.LootItem;
import me.TahaCheji.Mafana.mobData.MasterMob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Zombie extends MasterMob {

    public Zombie() {
        super(ChatColor.GREEN + "Zombie", 100, EntityType.ZOMBIE, 25, 10,
                5, 10, 0, null, null, new LootItem(new ItemStack(Material.ROTTEN_FLESH), 1, 3, 100));
    }

    @Override
    public void onAbilityHit(Player player, Entity entity) {
        player.sendMessage("test1");
    }

    @Override
    public void passiveAbility(Entity entity) {
        for(Entity entities : entity.getLocation().getNearbyEntities(2, 2, 2)) {
            if(entities instanceof Player) {
                Player player = (Player) entities;
                player.sendMessage("test2");
            }
        }
    }
}
