package me.TahaCheji.Mafana.crafting;

import me.TahaCheji.Mafana.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class CraftingEvent implements Listener {

    @EventHandler
    public void playerRightClick(InventoryOpenEvent e) {
        Player player = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
        if (!(inv.getType() == InventoryType.WORKBENCH)) {
            return;
        }
        e.setCancelled(true);
        CraftingGui gui = new CraftingGui();
        player.openInventory(gui.getInventory());
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 10, 10);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!(e.getView().getTitle().contains("MafanaCraft"))) {
            return;
        }
        Inventory GUI = e.getInventory();

        e.setCancelled(e.getSlot() != 1 && e.getSlot() != 2 && e.getSlot() != 3 &&
                e.getSlot() != 10 && e.getSlot() != 11 && e.getSlot() != 12 &&
                e.getSlot() != 19 && e.getSlot() != 20 && e.getSlot() != 21 && e.getSlot() != 14 && e.getClickedInventory() != player.getInventory());

        for (MasterTable masterTable : Main.recipes) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                masterTable.createRecipe(GUI);
                masterTable.craftedItem(e);
            }, 0L);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        if (!(e.getView().getTitle().contains("MafanaCraft"))) {
            return;
        }
        if (!(e.getInventory().getItem(1) == null)) {
            player.getInventory().addItem(e.getInventory().getItem(1));
        }
        if (!(e.getInventory().getItem(2) == null)) {
            player.getInventory().addItem(e.getInventory().getItem(2));
        }
        if (!(e.getInventory().getItem(3) == null)) {
            player.getInventory().addItem(e.getInventory().getItem(3));
        }

        if (!(e.getInventory().getItem(10) == null)) {
            player.getInventory().addItem(e.getInventory().getItem(10));
        }
        if (!(e.getInventory().getItem(11) == null)) {
            player.getInventory().addItem(e.getInventory().getItem(11));
        }
        if (!(e.getInventory().getItem(12) == null)) {
            player.getInventory().addItem(e.getInventory().getItem(12));
        }

        if (!(e.getInventory().getItem(19) == null)) {
            player.getInventory().addItem(e.getInventory().getItem(19));
        }
        if (!(e.getInventory().getItem(20) == null)) {
            player.getInventory().addItem(e.getInventory().getItem(20));
        }
        if (!(e.getInventory().getItem(21) == null)) {
            player.getInventory().addItem(e.getInventory().getItem(21));
        }

    }


}
