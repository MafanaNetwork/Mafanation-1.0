package me.TahaCheji.Mafana.crafting;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.TahaCheji.Mafana.Main;
import me.TahaCheji.Mafana.itemData.ItemType;
import me.TahaCheji.Mafana.itemData.MasterItems;
import me.TahaCheji.Mafana.utils.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RecipeEvent implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent e) {
        if(e.getView().getTitle().contains("Recipe")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        if(!(e.getAction() == Action.RIGHT_CLICK_AIR)) return;
        for(MasterTable table : Main.recipes) {
            for(ItemStack itemStack : table.getRecipe()) {
                if(e.getItem() == null) {
                    continue;
                }
                if(e.getItem().getItemMeta() == null) {
                    continue;
                }
                if(itemStack == null) {
                    continue;
                }
                if(itemStack.getItemMeta() == null) {
                    continue;
                }
                if(!new NBTItem(e.getItem()).hasNBTData()) {
                    continue;
                }
                if(!new NBTItem(e.getItem()).hasKey("ItemKey")) {
                    continue;
                }
                if(!new NBTItem(e.getItem()).getString("ItemKey").equalsIgnoreCase(new NBTItem(itemStack).getString("ItemKey"))) {
                    continue;
                }
                if(!new NBTItem(e.getItem()).getString("ItemType").contains(ItemType.MATERIAL.getLore())) {
                    return;
                }
                e.getPlayer().openInventory(new RecipeGui(table).getInventory());
            }
        }
    }

}
