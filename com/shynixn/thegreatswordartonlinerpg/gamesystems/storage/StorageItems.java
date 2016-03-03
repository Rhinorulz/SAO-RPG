/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.storage;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.CardinalSystem;
import libraries.com.shynixn.utilities.BukkitShyUtilities;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class StorageItems {
    private static StorageItems instance;

    private StorageItems() {
    }

    public static StorageItems getInstance() {
        if (instance == null) {
            instance = new StorageItems();
        }
        return instance;
    }

    public boolean isApiItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_SKILLAPI, CardinalLanguage.Items.LORE_SKILLAPI, Material.SUGAR);
    }

    public boolean isSignSelector(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_SIGNSELECTOR, CardinalLanguage.Items.LORE_SIGNSELECTOR, Material.GOLD_SWORD);
    }

    public boolean isMenuItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_MENU, CardinalLanguage.Items.LORE_MENU, Material.SIGN);
    }

    public boolean isNervegear(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_NERVEGEAR, CardinalLanguage.Items.LORE_NERVEGEAR, Material.IRON_HELMET);
    }

    public boolean isEquipmentItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT, CardinalLanguage.Items.LORE_EQUIPMENT, Material.CHEST);
    }

    public boolean isEquipmentArmorItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_ARMOR, CardinalLanguage.Items.LORE_EQUIPMENT_ARMOR, Material.IRON_CHESTPLATE);
    }

    public boolean isEquipmentWeaponItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_WEAPON, CardinalLanguage.Items.LORE_EQUIPMENT_WEAPON, Material.IRON_SWORD);
    }

    public boolean isEquipmentMaterialItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_MATERIALS, CardinalLanguage.Items.LORE_EQUIPMENT_MATERIALS, Material.IRON_INGOT);
    }

    public boolean isEquipmentFoodItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_FOOD, CardinalLanguage.Items.LORE_EQUIPMENT_FOOD, Material.COOKED_MUTTON);
    }

    public boolean isEquipmentDropItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT_DROPS, CardinalLanguage.Items.LORE_EQUIPMENT_DROPS, Material.RABBIT_HIDE);
    }

    public boolean isExitItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_EXIT, CardinalLanguage.Items.LORE_EXIT, Material.REDSTONE_BLOCK);
    }

    public boolean isSkillItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_SKILLS, CardinalLanguage.Items.LORE_SKILLS, Material.CLAY_BALL);
    }

    public boolean isSocialItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_SOCIAL, CardinalLanguage.Items.LORE_SOCIAL, Material.SKULL_ITEM);
    }

    public boolean isScoreboardItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_SCOREBOARD, CardinalLanguage.Items.LORE_SCOREBOARD, Material.PAINTING);
    }

    public boolean isNextItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_NEXT, CardinalLanguage.Items.LORE_NEXT, Material.SLIME_BLOCK);
    }

    public boolean isLogOutItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_LOGOUT, CardinalLanguage.Items.LORE_LOGOUT, Material.BEDROCK);
    }

    public boolean isPetsApiItem(ItemStack itemStack) {
        return BukkitUtilities.u().compareItemNames(itemStack, CardinalLanguage.Items.DISPLAYNAME_PETSAPI, CardinalLanguage.Items.LORE_PETSAPI, Material.MONSTER_EGG);
    }

    public void setStandardItems(Player player) {
        if (Cardinal.getSettings().isMenuEnabled() && Cardinal.getSettings().getSystem() != CardinalSystem.CLASSIC) {
            player.getInventory().setItem(Cardinal.getSettings().getMenuSlot(), BukkitUtilities.u().nameItem(new ItemStack(Material.SIGN, 1), CardinalLanguage.Items.DISPLAYNAME_MENU, CardinalLanguage.Items.LORE_MENU));
        }
        if (Cardinal.getSettings().isScoreboardEnabled()) {
            player.getInventory().setItem(Cardinal.getSettings().getScoreboardSlot(), BukkitUtilities.u().nameItem(new ItemStack(Material.PAINTING), CardinalLanguage.Items.DISPLAYNAME_SCOREBOARD, CardinalLanguage.Items.LORE_SCOREBOARD));
        }
        if (Cardinal.getSettings().isSocialEnabled()) {
            player.getInventory().setItem(Cardinal.getSettings().getSocialSlot(), BukkitShyUtilities.activateHead(player, BukkitUtilities.u().nameItem(new ItemStack(Material.SKULL_ITEM, 1, 3), CardinalLanguage.Items.DISPLAYNAME_SOCIAL, CardinalLanguage.Items.LORE_SOCIAL)));
        }
        if (Cardinal.getSettings().isEquipMentEnabled()) {
            player.getInventory().setItem(Cardinal.getSettings().getEquipMentdSlot(), BukkitUtilities.u().nameItem(new ItemStack(Material.CHEST), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT, CardinalLanguage.Items.LORE_EQUIPMENT));
        }
        if (Cardinal.getSettings().isSkillEnabled()) {
            player.getInventory().setItem(Cardinal.getSettings().getSkilldSlot(), BukkitUtilities.u().nameItem(new ItemStack(Material.CLAY_BALL), CardinalLanguage.Items.DISPLAYNAME_SKILLS, CardinalLanguage.Items.LORE_SKILLS));
        }
        player.updateInventory();
    }

    public boolean isStorageItem(ItemStack itemStack) {
        if (this.isEquipmentItem(itemStack) || this.isExitItem(itemStack) || this.isSkillItem(itemStack) || this.isSocialItem(itemStack) || this.isScoreboardItem(itemStack) || this.isMenuItem(itemStack) || this.isNextItem(itemStack) || this.isApiItem(itemStack) || this.isLogOutItem(itemStack) || this.isPetsApiItem(itemStack)) {
            return true;
        }
        return false;
    }
}

