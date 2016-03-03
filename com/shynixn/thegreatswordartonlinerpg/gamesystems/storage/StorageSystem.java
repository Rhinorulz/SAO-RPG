/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.storage;

import com.shynixn.thegreatswordartonlinerpg.TheGreatSwordArtOnlineRPG;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.PlayerSave;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.StorageFileManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.StorageItemsCommandExecutor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.StorageListener;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.InventoryType;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerLogOutEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerLoginEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.floors.AincradBeatFloorEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.skill.AincradLearnSkillEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradRequestInventoryTypeEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradRequestPlayerSaveEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradResetInventoryEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi.SkillAPICompatibility;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class StorageSystem
implements CardinalSystem {
    private HashMap<Player, PlayerSave> playerSaves = new HashMap();
    private HashMap<Player, InventoryType> actualInventoryType = new HashMap();
    private StorageFileManager inventoryFileManager;

    public StorageSystem(TheGreatSwordArtOnlineRPG plugin) {
        this.inventoryFileManager = new StorageFileManager(plugin);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.StorageListener(this, plugin);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.StorageItemsCommandExecutor(plugin);
    }

    @Override
    public void handleCardinalEvent(AincradEvent event) throws Exception {
        if (event instanceof AincradPlayerLoginEvent) {
            this.logInPlayer(((AincradPlayerLoginEvent)event).getPlayer());
        } else if (event instanceof AincradPlayerLogOutEvent) {
            this.logOutPlayer(((AincradPlayerLogOutEvent)event).getPlayer());
        } else if (event instanceof AincradRequestInventoryTypeEvent) {
            this.handleRequestInventoryTypeEvent((AincradRequestInventoryTypeEvent)event);
        } else if (event instanceof AincradRequestPlayerSaveEvent) {
            this.handleRequestPlayerSaveEvent((AincradRequestPlayerSaveEvent)event);
        } else if (event instanceof AincradResetInventoryEvent) {
            this.resetInventory(((AincradResetInventoryEvent)event).getPlayer());
        } else if (event instanceof AincradBeatFloorEvent) {
            this.handleBeatFloorEvent((AincradBeatFloorEvent)event);
        } else if (event instanceof AincradLearnSkillEvent) {
            this.handleLearnSkillEvent((AincradLearnSkillEvent)event);
        }
    }

    private void handleLearnSkillEvent(AincradLearnSkillEvent event) {
        this.switchToInventory(event.getPlayer(), InventoryType.SKILLS);
        int i = 0;
        ItemStack[] arritemStack = event.getPlayer().getInventory().getContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack[n2];
            try {
                if (BukkitUtilities.u().compareItemNames(event.getItemStack(), itemStack.getItemMeta().getDisplayName(), (String)itemStack.getItemMeta().getLore().get(0), null)) {
                    event.getPlayer().getInventory().setItem(i, new ItemStack(Material.AIR));
                }
            }
            catch (Exception var7_7) {
                // empty catch block
            }
            ++i;
            ++n2;
        }
        event.getItemStack().setAmount(1);
        event.getPlayer().getInventory().addItem(new ItemStack[]{event.getItemStack()});
    }

    private void handleBeatFloorEvent(AincradBeatFloorEvent event) {
        this.playerSaves.get((Object)event.getPlayer()).addAccess(event.getNextFloorId());
    }

    private void handleRequestPlayerSaveEvent(AincradRequestPlayerSaveEvent event) {
        if (this.playerSaves.containsKey((Object)event.getPlayer())) {
            event.setPlayerSave(this.playerSaves.get((Object)event.getPlayer()));
        }
    }

    private void handleRequestInventoryTypeEvent(AincradRequestInventoryTypeEvent event) {
        if (this.actualInventoryType.containsKey((Object)event.getPlayer())) {
            event.setInventoryType(this.actualInventoryType.get((Object)event.getPlayer()));
        } else {
            event.setInventoryType(InventoryType.OFFLINE);
        }
    }

    private void logOutPlayer(Player player) {
        if (this.playerSaves.containsKey((Object)player)) {
            this.resetInventory(player);
            this.updatePlayerSave(player);
            this.inventoryFileManager.save(this.playerSaves.get((Object)player));
            this.actualInventoryType.remove((Object)player);
            this.toOfflineInventory(player);
            this.playerSaves.remove((Object)player);
        }
    }

    private void logInPlayer(Player player) {
        if (!this.playerSaves.containsKey((Object)player)) {
            PlayerSave playerSave = this.inventoryFileManager.load(player);
            if (playerSave == null) {
                playerSave = new PlayerSave(player.getUniqueId().toString());
            }
            this.playerSaves.put(player, playerSave);
            this.toOnlineInventory(player);
            this.actualInventoryType.put(player, InventoryType.STANDARD);
        }
    }

    private void toOfflineInventory(Player player) {
        PlayerSave playerSave = this.playerSaves.get((Object)player);
        player.getInventory().setContents(playerSave.getOfflinecontents());
        player.getInventory().setArmorContents(playerSave.getOfflinearmorContents());
        player.setExp(playerSave.getOfflineexp());
        player.setLevel(playerSave.getOfflinelevel());
        player.setHealth(playerSave.getOfflineHealth());
        player.setFoodLevel((int)playerSave.getOfflineHunger());
        player.updateInventory();
    }

    private void toOnlineInventory(Player player) {
        PlayerSave playerSave = this.playerSaves.get((Object)player);
        playerSave.setOfflinearmorContents(player.getInventory().getArmorContents());
        playerSave.setOfflinecontents(player.getInventory().getContents());
        playerSave.setOfflinelevel(player.getLevel());
        playerSave.setOfflineexp(player.getExp());
        playerSave.setOfflineHealth(player.getHealth());
        playerSave.setOfflineHunger(player.getFoodLevel());
        player.getInventory().setContents(playerSave.getOnlineContents());
        player.getInventory().setArmorContents(playerSave.getOnlineearmorContents());
        player.setExp(playerSave.getOnlineMCexp());
        player.setLevel(playerSave.getOnlineMClevel());
        player.setHealth(playerSave.getOnlineHealth());
        player.setFoodLevel((int)playerSave.getOnlineHunger());
        player.updateInventory();
    }

    private void updatePlayerSave(Player player) {
        PlayerSave playerSave = this.playerSaves.get((Object)player);
        playerSave.setOnlineContents(player.getInventory().getContents());
        playerSave.setOnlineearmorContents(player.getInventory().getArmorContents());
        playerSave.setOnlineMCexp(player.getExp());
        playerSave.setOnlineMClevel(player.getLevel());
        playerSave.setOnlineHealth(player.getHealth());
        playerSave.setOnlineHunger(player.getFoodLevel());
    }

    protected void switchToInventory(Player player, InventoryType inventoryType) {
        this.playerSaves.get((Object)player).setContents((ItemStack[])player.getInventory().getContents().clone(), this.actualInventoryType.get((Object)player));
        this.actualInventoryType.put(player, inventoryType);
        player.getInventory().clear();
        if (this.playerSaves.get((Object)player).getContents(inventoryType) == null) {
            player.getInventory().setContents(new ItemStack[36]);
        } else {
            player.getInventory().setContents(this.playerSaves.get((Object)player).getContents(inventoryType));
        }
        if (inventoryType != InventoryType.STANDARD) {
            player.getInventory().setItem(8, BukkitUtilities.u().nameItem(new ItemStack(Material.REDSTONE_BLOCK), CardinalLanguage.Items.DISPLAYNAME_EXIT, CardinalLanguage.Items.LORE_EXIT));
        }
        player.getInventory().setHeldItemSlot(7);
        if (inventoryType == InventoryType.STANDARD) {
            player.getInventory().setHeldItemSlot(0);
        } else if (inventoryType == InventoryType.MENU) {
            this.switchToMenue(player);
        } else if (InventoryType.isEquipMentArmor(inventoryType)) {
            this.switchToEquipMentArmor(player, inventoryType);
        } else if (InventoryType.isEquipMentWeapon(inventoryType)) {
            this.switchToEquipMentWeapon(player, inventoryType);
        } else if (InventoryType.isEquipMentMaterial(inventoryType)) {
            this.switchToEquipMentMaterials(player, inventoryType);
        } else if (InventoryType.isEquipMentFood(inventoryType)) {
            this.switchToEquipMentFood(player, inventoryType);
        } else if (InventoryType.isEquipMentDrop(inventoryType)) {
            this.switchToEquipMentDrops(player, inventoryType);
        } else if (inventoryType == InventoryType.SKILLS) {
            this.switchToSkill(player);
        }
        player.updateInventory();
    }

    private void switchToSkill(Player player) {
        if (Cardinal.getRegistry().isUsingSkillApi()) {
            Cardinal.getRegistry().getSkillAPIRegistry().switchtoSkills(player);
        }
    }

    private void switchToMenue(Player player) {
        player.getInventory().setItem(0, BukkitUtilities.u().nameItem(new ItemStack(Material.BEDROCK, 1), CardinalLanguage.Items.DISPLAYNAME_LOGOUT, CardinalLanguage.Items.LORE_LOGOUT));
        if (Cardinal.getSettings().isSkillapienabled()) {
            player.getInventory().setItem(Cardinal.getSettings().getSkillapiSlot(), BukkitUtilities.u().nameItem(new ItemStack(Material.SUGAR), CardinalLanguage.Items.DISPLAYNAME_SKILLAPI, CardinalLanguage.Items.LORE_SKILLAPI));
        }
        if (Cardinal.getSettings().isEchopetEnabled()) {
            player.getInventory().setItem(Cardinal.getSettings().getEchopetsSlot(), BukkitUtilities.u().nameItem(new ItemStack(Material.MONSTER_EGG, 1, 101), CardinalLanguage.Items.DISPLAYNAME_PETSAPI, CardinalLanguage.Items.LORE_PETSAPI));
        }
    }

    private void switchToEquipMentMaterials(Player player, InventoryType inventoryType) {
        player.getInventory().setItem(0, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_CHESTPLATE, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_ARMOR, CardinalLanguage.Items.LORE_EQUIPMENT_ARMOR));
        player.getInventory().setItem(1, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_SWORD, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_WEAPON, CardinalLanguage.Items.LORE_EQUIPMENT_WEAPON));
        player.getInventory().setItem(2, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(3, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_INGOT, 1), String.valueOf(CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_MATERIALS) + " " + inventoryType.getPage(), CardinalLanguage.Items.LORE_EQUIPMENT_MATERIALS));
        player.getInventory().setItem(4, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(5, BukkitUtilities.u().nameItem(new ItemStack(Material.COOKED_MUTTON, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_FOOD, CardinalLanguage.Items.LORE_EQUIPMENT_FOOD));
        player.getInventory().setItem(6, BukkitUtilities.u().nameItem(new ItemStack(Material.RABBIT_HIDE, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_DROPS, CardinalLanguage.Items.LORE_EQUIPMENT_DROPS));
    }

    private void switchToEquipMentWeapon(Player player, InventoryType inventoryType) {
        player.getInventory().setItem(0, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_CHESTPLATE, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_ARMOR, CardinalLanguage.Items.LORE_EQUIPMENT_ARMOR));
        player.getInventory().setItem(1, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_INGOT, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_MATERIALS, CardinalLanguage.Items.LORE_EQUIPMENT_MATERIALS));
        player.getInventory().setItem(2, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(3, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_SWORD, 1), String.valueOf(CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_WEAPON) + " " + inventoryType.getPage(), CardinalLanguage.Items.LORE_EQUIPMENT_WEAPON));
        player.getInventory().setItem(4, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(5, BukkitUtilities.u().nameItem(new ItemStack(Material.COOKED_MUTTON, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_FOOD, CardinalLanguage.Items.LORE_EQUIPMENT_FOOD));
        player.getInventory().setItem(6, BukkitUtilities.u().nameItem(new ItemStack(Material.RABBIT_HIDE, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_DROPS, CardinalLanguage.Items.LORE_EQUIPMENT_DROPS));
    }

    private void switchToEquipMentArmor(Player player, InventoryType inventoryType) {
        player.getInventory().setItem(0, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_INGOT, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_MATERIALS, CardinalLanguage.Items.LORE_EQUIPMENT_MATERIALS));
        player.getInventory().setItem(1, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_SWORD, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_WEAPON, CardinalLanguage.Items.LORE_EQUIPMENT_WEAPON));
        player.getInventory().setItem(2, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(3, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_CHESTPLATE, 1), String.valueOf(CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_ARMOR) + " " + inventoryType.getPage(), CardinalLanguage.Items.LORE_EQUIPMENT_ARMOR));
        player.getInventory().setItem(4, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(5, BukkitUtilities.u().nameItem(new ItemStack(Material.COOKED_MUTTON, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_FOOD, CardinalLanguage.Items.LORE_EQUIPMENT_FOOD));
        player.getInventory().setItem(6, BukkitUtilities.u().nameItem(new ItemStack(Material.RABBIT_HIDE, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_DROPS, CardinalLanguage.Items.LORE_EQUIPMENT_DROPS));
    }

    private void switchToEquipMentDrops(Player player, InventoryType inventoryType) {
        player.getInventory().setItem(0, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_CHESTPLATE, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_ARMOR, CardinalLanguage.Items.LORE_EQUIPMENT_ARMOR));
        player.getInventory().setItem(1, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_SWORD, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_WEAPON, CardinalLanguage.Items.LORE_EQUIPMENT_WEAPON));
        player.getInventory().setItem(2, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(3, BukkitUtilities.u().nameItem(new ItemStack(Material.RABBIT_HIDE, 1), String.valueOf(CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_DROPS) + " " + inventoryType.getPage(), CardinalLanguage.Items.LORE_EQUIPMENT_DROPS));
        player.getInventory().setItem(4, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(5, BukkitUtilities.u().nameItem(new ItemStack(Material.COOKED_MUTTON, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_FOOD, CardinalLanguage.Items.LORE_EQUIPMENT_FOOD));
        player.getInventory().setItem(6, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_INGOT, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_MATERIALS, CardinalLanguage.Items.LORE_EQUIPMENT_MATERIALS));
    }

    private void switchToEquipMentFood(Player player, InventoryType inventoryType) {
        player.getInventory().setItem(0, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_CHESTPLATE, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_ARMOR, CardinalLanguage.Items.LORE_EQUIPMENT_ARMOR));
        player.getInventory().setItem(1, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_SWORD, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_WEAPON, CardinalLanguage.Items.LORE_EQUIPMENT_WEAPON));
        player.getInventory().setItem(2, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(3, BukkitUtilities.u().nameItem(new ItemStack(Material.COOKED_MUTTON, 1), String.valueOf(CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_FOOD) + " " + inventoryType.getPage(), CardinalLanguage.Items.LORE_EQUIPMENT_FOOD));
        player.getInventory().setItem(4, BukkitUtilities.u().nameItem(new ItemStack(Material.SLIME_BLOCK, 1), CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT));
        player.getInventory().setItem(5, BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_INGOT, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_MATERIALS, CardinalLanguage.Items.LORE_EQUIPMENT_MATERIALS));
        player.getInventory().setItem(6, BukkitUtilities.u().nameItem(new ItemStack(Material.RABBIT_HIDE, 1), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_DROPS, CardinalLanguage.Items.LORE_EQUIPMENT_DROPS));
    }

    protected InventoryType getActualInventoryType(Player player) {
        if (!this.actualInventoryType.containsKey((Object)player)) {
            return InventoryType.STANDARD;
        }
        return this.actualInventoryType.get((Object)player);
    }

    protected void resetInventory(Player player) {
        if (this.playerSaves.containsKey((Object)player) && this.actualInventoryType.get((Object)player) != InventoryType.STANDARD) {
            this.switchToInventory(player, InventoryType.STANDARD);
        }
    }
}

