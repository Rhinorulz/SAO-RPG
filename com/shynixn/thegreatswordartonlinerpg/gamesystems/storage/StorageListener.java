/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.storage;

import com.shynixn.thegreatswordartonlinerpg.TheGreatSwordArtOnlineRPG;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Permission;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.StorageSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.InventoryType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.ScoreboardType;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.scoreboard.AincradSwitchScoreboardEvent;
import libraries.com.shynixn.utilities.BukkitEvents;
import libraries.com.shynixn.utilities.BukkitShyUtilities;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class StorageListener
extends BukkitEvents {
    private TheGreatSwordArtOnlineRPG plugin;
    private StorageSystem storageSystem;

    public StorageListener(StorageSystem storageSystem, TheGreatSwordArtOnlineRPG plugin) {
        super(plugin);
        this.plugin = plugin;
        this.storageSystem = storageSystem;
    }

    @EventHandler
    public void openEquipmentInventoryEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isEquipmentItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().hasPermission(Permission.EQUIPMENTINVENTORY.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.EQUIPMENT_MATERIALS_1);
            event.setCancelled(true);
        } else if (this.isRightClick(event) && InventoryType.isEquipMent(this.storageSystem.getActualInventoryType(event.getPlayer())) && event.getPlayer().getInventory().getHeldItemSlot() == 3) {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }

    @EventHandler
    public void openEquipmentInventoryArmorEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isEquipmentArmorItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().hasPermission(Permission.EQUIPMENTINVENTORY.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.EQUIPMENT_ARMOR_1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openEquipmentInventoryWeaponEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isEquipmentWeaponItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().hasPermission(Permission.EQUIPMENTINVENTORY.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.EQUIPMENT_WEAPON_1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openEquipmentInventoryMaterialsEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isEquipmentMaterialItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().hasPermission(Permission.EQUIPMENTINVENTORY.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.EQUIPMENT_MATERIALS_1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openEquipmentInventoryFoodEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isEquipmentFoodItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().hasPermission(Permission.EQUIPMENTINVENTORY.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.EQUIPMENT_FOOD_1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openEquipmentInventoryDropsEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isEquipmentDropItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().hasPermission(Permission.EQUIPMENTINVENTORY.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.EQUIPMENT_DROPS_1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openSkillApiEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isApiItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().getInventory().getHeldItemSlot() != 0 && event.getPlayer().hasPermission(Permission.EQUIPMENTINVENTORY.toString())) {
            Bukkit.getServer().dispatchCommand((CommandSender)event.getPlayer(), "class skill");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openPetsApiEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isPetsApiItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer())) {
            Bukkit.getServer().dispatchCommand((CommandSender)event.getPlayer(), "pet select");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openEquipmentNextInventoryEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isNextItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().getInventory().getHeldItemSlot() != 0 && event.getPlayer().hasPermission(Permission.EQUIPMENTINVENTORY.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), this.getNextInventoryType(event.getPlayer()));
        }
    }

    @EventHandler
    public void openSkillInventoryEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isSkillItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().getInventory().getHeldItemSlot() != 0 && event.getPlayer().hasPermission(Permission.SKILLINVENTORY.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.SKILLS);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openMenuInventoryEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isMenuItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().getInventory().getHeldItemSlot() != 0 && event.getPlayer().hasPermission(Permission.SAO_JOIN.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.MENU);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openSocialEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isSocialItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().getInventory().getHeldItemSlot() != 0 && event.getPlayer().hasPermission(Permission.PARTY.toString())) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.SOCIAL);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openExitEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isExitItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().getInventory().getHeldItemSlot() != 0) {
            this.storageSystem.switchToInventory(event.getPlayer(), InventoryType.STANDARD);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openScoreboardInventoryEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isScoreboardItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && event.getPlayer().getInventory().getHeldItemSlot() != 0 && event.getPlayer().hasPermission(Permission.SCOREBOARD.toString())) {
            Cardinal.call().notifyScoreboardSystem(new AincradSwitchScoreboardEvent(event.getPlayer(), ScoreboardType.NEXT));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void openLogOutEvent(PlayerInteractEvent event) {
        if (this.isRightClick(event) && Cardinal.getStorageItems().isLogOutItem(event.getPlayer().getItemInHand()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer())) {
            Cardinal.getGenericSystem().logoutPlayer(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void interactSkillBar(PlayerInteractEvent event) {
        if (this.storageSystem.getActualInventoryType(event.getPlayer()) == InventoryType.SKILLS) {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }

    @EventHandler
    public void damageEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player)event.getDamager();
            if (Cardinal.getStorageItems().isEquipmentWeaponItem(player.getItemInHand()) || InventoryType.isEquipMent(this.storageSystem.getActualInventoryType(player)) && player.getInventory().getHeldItemSlot() == 3) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void pickUpItemEvent(PlayerPickupItemEvent event) {
        this.storageSystem.resetInventory(event.getPlayer());
    }

    @EventHandler
    public void moveSpecialItemEvent(InventoryClickEvent event) {
        if (Cardinal.getStorageItems().isExitItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
            this.storageSystem.switchToInventory((Player)event.getWhoClicked(), InventoryType.STANDARD);
        } else if (Cardinal.getStorageItems().isEquipmentItem(event.getCurrentItem()) && !event.getCurrentItem().equals((Object)event.getCursor())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
            this.storageSystem.switchToInventory((Player)event.getWhoClicked(), InventoryType.EQUIPMENT_MATERIALS_1);
        } else if (Cardinal.getStorageItems().isNextItem(event.getCurrentItem()) && !event.getCurrentItem().equals((Object)event.getCursor())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
            this.storageSystem.switchToInventory((Player)event.getWhoClicked(), this.getNextInventoryType((Player)event.getWhoClicked()));
        } else if (InventoryType.isEquipMent(this.storageSystem.getActualInventoryType((Player)event.getWhoClicked())) && event.getSlot() == 3) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        } else if (Cardinal.getStorageItems().isMenuItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        } else if (Cardinal.getStorageItems().isApiItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        } else if (Cardinal.getStorageItems().isLogOutItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        } else if (Cardinal.getStorageItems().isPetsApiItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        } else if (Cardinal.getStorageItems().isEquipmentArmorItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
            this.storageSystem.switchToInventory((Player)event.getWhoClicked(), InventoryType.EQUIPMENT_ARMOR_1);
        } else if (Cardinal.getStorageItems().isEquipmentWeaponItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
            this.storageSystem.switchToInventory((Player)event.getWhoClicked(), InventoryType.EQUIPMENT_WEAPON_1);
        } else if (Cardinal.getStorageItems().isEquipmentMaterialItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
            this.storageSystem.switchToInventory((Player)event.getWhoClicked(), InventoryType.EQUIPMENT_MATERIALS_1);
        } else if (Cardinal.getStorageItems().isEquipmentFoodItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
            this.storageSystem.switchToInventory((Player)event.getWhoClicked(), InventoryType.EQUIPMENT_FOOD_1);
        } else if (Cardinal.getStorageItems().isEquipmentDropItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
            this.storageSystem.switchToInventory((Player)event.getWhoClicked(), InventoryType.EQUIPMENT_DROPS_1);
        } else if (Cardinal.getStorageItems().isStorageItem(event.getCurrentItem())) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        }
    }

    @EventHandler
    public void dropSpecialItemEvent(PlayerDropItemEvent event) {
        if (Cardinal.getStorageItems().isStorageItem(event.getItemDrop().getItemStack())) {
            Player player = event.getPlayer();
            ItemStack itemStack = player.getItemInHand().clone();
            event.getItemDrop().remove();
            event.setCancelled(true);
            player.setItemInHand(itemStack);
            player.updateInventory();
            BukkitShyUtilities.refreshInventory(player, this.plugin);
        }
    }

    private boolean isRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            return true;
        }
        return false;
    }

    private InventoryType getNextInventoryType(Player player) {
        InventoryType actual = this.storageSystem.getActualInventoryType(player);
        if (InventoryType.isEquipMentArmor(actual)) {
            if (actual.getPage() == 1) {
                return InventoryType.EQUIPMENT_ARMOR_2;
            }
            if (actual.getPage() == 2) {
                return InventoryType.EQUIPMENT_ARMOR_3;
            }
            if (actual.getPage() == 3) {
                return InventoryType.EQUIPMENT_ARMOR_4;
            }
            if (actual.getPage() == 4) {
                return InventoryType.EQUIPMENT_ARMOR_1;
            }
        }
        if (InventoryType.isEquipMentWeapon(actual)) {
            if (actual.getPage() == 1) {
                return InventoryType.EQUIPMENT_WEAPON_2;
            }
            if (actual.getPage() == 2) {
                return InventoryType.EQUIPMENT_WEAPON_3;
            }
            if (actual.getPage() == 3) {
                return InventoryType.EQUIPMENT_WEAPON_4;
            }
            if (actual.getPage() == 4) {
                return InventoryType.EQUIPMENT_WEAPON_1;
            }
        }
        if (InventoryType.isEquipMentMaterial(actual)) {
            if (actual.getPage() == 1) {
                return InventoryType.EQUIPMENT_MATERIALS_2;
            }
            if (actual.getPage() == 2) {
                return InventoryType.EQUIPMENT_MATERIALS_3;
            }
            if (actual.getPage() == 3) {
                return InventoryType.EQUIPMENT_MATERIALS_4;
            }
            if (actual.getPage() == 4) {
                return InventoryType.EQUIPMENT_MATERIALS_1;
            }
        }
        if (InventoryType.isEquipMentDrop(actual)) {
            if (actual.getPage() == 1) {
                return InventoryType.EQUIPMENT_DROPS_2;
            }
            if (actual.getPage() == 2) {
                return InventoryType.EQUIPMENT_DROPS_3;
            }
            if (actual.getPage() == 3) {
                return InventoryType.EQUIPMENT_DROPS_4;
            }
            if (actual.getPage() == 4) {
                return InventoryType.EQUIPMENT_DROPS_1;
            }
        }
        if (InventoryType.isEquipMentFood(actual)) {
            if (actual.getPage() == 1) {
                return InventoryType.EQUIPMENT_FOOD_2;
            }
            if (actual.getPage() == 2) {
                return InventoryType.EQUIPMENT_FOOD_3;
            }
            if (actual.getPage() == 3) {
                return InventoryType.EQUIPMENT_FOOD_4;
            }
            if (actual.getPage() == 4) {
                return InventoryType.EQUIPMENT_FOOD_1;
            }
        }
        return null;
    }
}

