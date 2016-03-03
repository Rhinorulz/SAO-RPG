/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package libraries.com.shynixn.utilities;

import java.util.HashMap;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitMenue
implements Listener {
    private ItemStack[] itemStacks;
    private HashMap<Player, Inventory> inventories;

    public BukkitMenue(JavaPlugin plugin, ItemStack[] itemStacks) {
        if (itemStacks == null || itemStacks.length < 27 || plugin == null) {
            throw new IllegalArgumentException("Itemstacks array length has to be greater than 26.");
        }
        this.itemStacks = itemStacks;
        this.inventories = new HashMap();
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }

    public final void openMenue(Player player) {
        Inventory inventory = Bukkit.getServer().createInventory((InventoryHolder)player, InventoryType.CHEST);
        int i = 0;
        while (i < 27) {
            inventory.setItem(i, this.itemStacks[i]);
            ++i;
        }
        this.inventories.put(player, inventory);
        player.openInventory(inventory);
    }

    @EventHandler
    public final void closeMenueEvent(InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        if (this.inventories.containsKey((Object)player) && event.getInventory().equals((Object)this.inventories.get((Object)player))) {
            this.inventories.remove((Object)player);
        }
    }

    @EventHandler
    public final void inventoryclickEvent(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (this.inventories.containsKey((Object)player) && event.getInventory().equals((Object)this.inventories.get((Object)player))) {
            if (BukkitUtilities.u().compareItemStacks(this.itemStacks[0], event.getCurrentItem()) && event.getSlot() == 0) {
                this.clickOnItemRow1Slot0(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[1], event.getCurrentItem()) && event.getSlot() == 1) {
                this.clickOnItemRow1Slot1(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[2], event.getCurrentItem()) && event.getSlot() == 2) {
                this.clickOnItemRow1Slot2(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[3], event.getCurrentItem()) && event.getSlot() == 3) {
                this.clickOnItemRow1Slot3(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[4], event.getCurrentItem()) && event.getSlot() == 4) {
                this.clickOnItemRow1Slot4(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[5], event.getCurrentItem()) && event.getSlot() == 5) {
                this.clickOnItemRow1Slot5(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[6], event.getCurrentItem()) && event.getSlot() == 6) {
                this.clickOnItemRow1Slot6(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[7], event.getCurrentItem()) && event.getSlot() == 7) {
                this.clickOnItemRow1Slot7(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[8], event.getCurrentItem()) && event.getSlot() == 8) {
                this.clickOnItemRow1Slot8(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[9], event.getCurrentItem()) && event.getSlot() == 9) {
                this.clickOnItemRow2Slot0(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[10], event.getCurrentItem()) && event.getSlot() == 10) {
                this.clickOnItemRow1Slot1(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[11], event.getCurrentItem()) && event.getSlot() == 11) {
                this.clickOnItemRow2Slot2(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[12], event.getCurrentItem()) && event.getSlot() == 12) {
                this.clickOnItemRow2Slot3(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[13], event.getCurrentItem()) && event.getSlot() == 13) {
                this.clickOnItemRow2Slot4(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[14], event.getCurrentItem()) && event.getSlot() == 14) {
                this.clickOnItemRow2Slot5(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[15], event.getCurrentItem()) && event.getSlot() == 15) {
                this.clickOnItemRow2Slot6(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[16], event.getCurrentItem()) && event.getSlot() == 16) {
                this.clickOnItemRow2Slot7(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[17], event.getCurrentItem()) && event.getSlot() == 17) {
                this.clickOnItemRow2Slot8(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[18], event.getCurrentItem()) && event.getSlot() == 18) {
                this.clickOnItemRow3Slot0(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[19], event.getCurrentItem()) && event.getSlot() == 19) {
                this.clickOnItemRow3Slot1(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[20], event.getCurrentItem()) && event.getSlot() == 20) {
                this.clickOnItemRow3Slot2(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[21], event.getCurrentItem()) && event.getSlot() == 21) {
                this.clickOnItemRow3Slot3(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[22], event.getCurrentItem()) && event.getSlot() == 22) {
                this.clickOnItemRow3Slot4(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[23], event.getCurrentItem()) && event.getSlot() == 23) {
                this.clickOnItemRow3Slot5(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[24], event.getCurrentItem()) && event.getSlot() == 24) {
                this.clickOnItemRow3Slot6(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[25], event.getCurrentItem()) && event.getSlot() == 25) {
                this.clickOnItemRow3Slot7(player);
            } else if (BukkitUtilities.u().compareItemStacks(this.itemStacks[26], event.getCurrentItem()) && event.getSlot() == 26) {
                this.clickOnItemRow3Slot8(player);
            }
            event.setCancelled(true);
        }
    }

    public void clickOnItemRow1Slot0(Player player) {
    }

    public void clickOnItemRow1Slot1(Player player) {
    }

    public void clickOnItemRow1Slot2(Player player) {
    }

    public void clickOnItemRow1Slot3(Player player) {
    }

    public void clickOnItemRow1Slot4(Player player) {
    }

    public void clickOnItemRow1Slot5(Player player) {
    }

    public void clickOnItemRow1Slot6(Player player) {
    }

    public void clickOnItemRow1Slot7(Player player) {
    }

    public void clickOnItemRow1Slot8(Player player) {
    }

    public void clickOnItemRow2Slot0(Player player) {
    }

    public void clickOnItemRow2Slot1(Player player) {
    }

    public void clickOnItemRow2Slot2(Player player) {
    }

    public void clickOnItemRow2Slot3(Player player) {
    }

    public void clickOnItemRow2Slot4(Player player) {
    }

    public void clickOnItemRow2Slot5(Player player) {
    }

    public void clickOnItemRow2Slot6(Player player) {
    }

    public void clickOnItemRow2Slot7(Player player) {
    }

    public void clickOnItemRow2Slot8(Player player) {
    }

    public void clickOnItemRow3Slot0(Player player) {
    }

    public void clickOnItemRow3Slot1(Player player) {
    }

    public void clickOnItemRow3Slot2(Player player) {
    }

    public void clickOnItemRow3Slot3(Player player) {
    }

    public void clickOnItemRow3Slot4(Player player) {
    }

    public void clickOnItemRow3Slot5(Player player) {
    }

    public void clickOnItemRow3Slot6(Player player) {
    }

    public void clickOnItemRow3Slot7(Player player) {
    }

    public void clickOnItemRow3Slot8(Player player) {
    }
}

