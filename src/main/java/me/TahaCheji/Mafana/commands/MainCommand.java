package me.TahaCheji.Mafana.commands;

import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.crafting.CraftingGui;
import me.TahaCheji.Mafana.itemData.ItemGui;
import me.TahaCheji.Mafana.mobData.GameMobsGui;
import me.TahaCheji.Mafana.mobData.MasterBoss;
import me.TahaCheji.Mafana.mobData.MasterMob;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("Mf")) {
            Player player = (Player) sender;
            if(args[0].equalsIgnoreCase("items")) {
                new ItemGui().getAllItemsGui().open(player);
            }
            if(args[0].equalsIgnoreCase("spawn")) {
                if(args.length == 1) {
                    new GameMobsGui().getAllItemsGui().open(player);
                    return true;
                }
                for(MasterMob createMob : Main.gameMobs) {
                    MasterMob mob = createMob.getMob(ChatColor.stripColor(args[1]));
                    if(mob == null) {
                        return true;
                    } else {
                        mob.spawnMob(player.getLocation(), player);
                    }
                }
            }
            if(args[0].equalsIgnoreCase("craft") || args[0].equalsIgnoreCase("cr")) {
                player.openInventory(new CraftingGui().getInventory());
            }
            if(args[0].equalsIgnoreCase("kill")) {
                for (MasterMob mob : Main.activeMobs) {
                    mob.killMob();
                }
                for (MasterBoss mob : Main.activeBoss) {
                    mob.killMob();
                }
            }
        }
        return false;
    }
}
