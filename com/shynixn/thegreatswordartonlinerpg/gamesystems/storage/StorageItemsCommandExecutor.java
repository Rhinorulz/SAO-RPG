/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.storage;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import java.util.HashMap;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitShyUtilities;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class StorageItemsCommandExecutor
extends BukkitCommands {
    public StorageItemsCommandExecutor(JavaPlugin plugin) {
        super("saoitems", plugin);
    }

    @Override
    public void playerSendCommandEvent(Player player, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("scoreboard")) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.PAINTING), CardinalLanguage.Items.DISPLAYNAME_SCOREBOARD, CardinalLanguage.Items.LORE_SCOREBOARD)});
        } else if (args.length == 1 && args[0].equalsIgnoreCase("signselector")) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.GOLD_SWORD), CardinalLanguage.Items.DISPLAYNAME_SIGNSELECTOR, CardinalLanguage.Items.LORE_SIGNSELECTOR)});
        } else if (args.length == 1 && args[0].equalsIgnoreCase("skillapi")) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.SUGAR), CardinalLanguage.Items.DISPLAYNAME_SKILLAPI, CardinalLanguage.Items.LORE_SKILLAPI)});
        } else if (args.length == 1 && args[0].equalsIgnoreCase("petsapi")) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.MONSTER_EGG, 1, 101), CardinalLanguage.Items.DISPLAYNAME_PETSAPI, CardinalLanguage.Items.LORE_PETSAPI)});
        } else if (args.length == 1 && args[0].equalsIgnoreCase("skillbar")) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.CLAY_BALL), CardinalLanguage.Items.DISPLAYNAME_SKILLS, CardinalLanguage.Items.LORE_SKILLS)});
        } else if (args.length == 1 && args[0].equalsIgnoreCase("equipment")) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.CHEST), CardinalLanguage.Items.DISPLAYNAME_EQUIPMENT, CardinalLanguage.Items.LORE_EQUIPMENT)});
        } else if (args.length == 1 && args[0].equalsIgnoreCase("social")) {
            player.getInventory().addItem(new ItemStack[]{BukkitShyUtilities.activateHead(player, BukkitUtilities.u().nameItem(new ItemStack(Material.SKULL_ITEM, 1, 3), CardinalLanguage.Items.DISPLAYNAME_SOCIAL, CardinalLanguage.Items.LORE_SOCIAL))});
        } else if (args.length == 1 && args[0].equalsIgnoreCase("nervegear")) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.IRON_HELMET, 1), CardinalLanguage.Items.DISPLAYNAME_NERVEGEAR, CardinalLanguage.Items.LORE_NERVEGEAR)});
        } else if (args.length == 1 && args[0].equalsIgnoreCase("menu")) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.SIGN, 1), CardinalLanguage.Items.DISPLAYNAME_MENU, CardinalLanguage.Items.LORE_MENU)});
        } else if (args.length == 1 && args[0].equalsIgnoreCase("logout")) {
            player.getInventory().addItem(new ItemStack[]{BukkitUtilities.u().nameItem(new ItemStack(Material.BEDROCK, 1), CardinalLanguage.Items.DISPLAYNAME_LOGOUT, CardinalLanguage.Items.LORE_LOGOUT)});
        } else {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Items    ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems signselector");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems skillbar");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems skillapi");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems petsapi");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems equipment");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems scoreboard");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems social");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems nervegear");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems menu");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems logout");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/1\u2510                            ");
            player.sendMessage("");
        }
    }
}

