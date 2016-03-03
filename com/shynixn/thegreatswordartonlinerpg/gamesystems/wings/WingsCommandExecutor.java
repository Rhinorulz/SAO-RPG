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
package com.shynixn.thegreatswordartonlinerpg.gamesystems.wings;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsData;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsSystem;
import java.util.HashMap;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class WingsCommandExecutor
extends BukkitCommands {
    private WingsSystem manager;

    public WingsCommandExecutor(WingsSystem manager, JavaPlugin plugin) {
        super("alowings", plugin);
        this.manager = manager;
    }

    @Override
    public void playerSendCommandEvent(Player player, String[] args) {
        if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
            this.createWingsCommand(player, args[1], args[2]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            this.removeWingsCommand(player, args[1]);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            this.listWingsCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("leftwing")) {
            this.changeLeftWingCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("rightwing")) {
            this.changeRightWingCommand(player, args[1]);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("speed")) {
            this.changeSpeedCommand(player, args[1], args[2]);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("toggle") && args[2].equalsIgnoreCase("small")) {
            this.changeWingsSize(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            this.giveWingCommand(player, args[1]);
        } else {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Wings  ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/alowings create <name> <displayName>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/alowings remove <name>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/alowings leftwing <name>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/alowings rightwing <name>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/alowings speed <name> <fast/normal/slow>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/alowings toggle <name> small");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/alowings give <name>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/alowings list");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/1\u2510                            ");
            player.sendMessage("");
        }
    }

    private void changeWingsSize(Player player, String name) {
        if (!this.manager.contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "This name does not exist.");
        } else if (this.manager.getItemFromName(name).isSmall()) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + "Disabled small wings.");
            this.manager.save(this.manager.getItemFromName(name));
            this.manager.getItemFromName(name).setSmall(false);
        } else {
            this.manager.getItemFromName(name).setSmall(true);
            this.manager.save(this.manager.getItemFromName(name));
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + "Enabled small wings.");
        }
    }

    private void changeSpeedCommand(Player player, String name, String speed) {
        if (!this.manager.contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "This name does not exist.");
        } else if (WingsData.WingsSpeed.getWingsSpeedFromName(speed) == null) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "This speedtype does not exist.");
        } else {
            this.manager.getItemFromName(name).setSpeed(WingsData.WingsSpeed.getWingsSpeedFromName(speed));
            this.manager.save(this.manager.getItemFromName(name));
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + "Updated speed.");
        }
    }

    private void giveWingCommand(Player player, String name) {
        if (!this.manager.contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "This name does not exist.");
        } else {
            WingsData wingsData = this.manager.getItemFromName(name);
            ItemStack itemStack = new ItemStack(Material.FEATHER);
            BukkitUtilities.u().nameItem(itemStack, wingsData.getDisplayName(), (Object)ChatColor.YELLOW + "Wings: " + wingsData.getName());
            player.getInventory().addItem(new ItemStack[]{itemStack});
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + "Received wingsitem. Rightclick on it to activate your wings or punch another entity to activate its wings.");
        }
    }

    private void changeLeftWingCommand(Player player, String name) {
        if (!this.manager.contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "This name does not exist.");
        } else if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.BANNER) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "Put a banner in your hands to convert the design to the left wing.");
        } else {
            this.manager.getItemFromName(name).setLeftWingItemStack(player.getInventory().getItemInHand());
            this.manager.save(this.manager.getItemFromName(name));
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + "Updated left wing design.");
        }
    }

    private void changeRightWingCommand(Player player, String name) {
        if (!this.manager.contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "This name does not exist.");
        } else if (player.getItemInHand() == null || player.getItemInHand().getType() != Material.BANNER) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "Put a banner in your hands to convert the design to the right wing.");
        } else {
            this.manager.getItemFromName(name).setRightWingItemStack(player.getInventory().getItemInHand());
            this.manager.save(this.manager.getItemFromName(name));
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + "Updated right wing design.");
        }
    }

    private void listWingsCommand(Player player) {
        player.sendMessage((Object)((Object)BukkitChatColor.WHITE) + "Registered Wings:");
        for (BukkitObject data : this.manager.getItems()) {
            player.sendMessage((Object)ChatColor.GRAY + data.getName() + " " + data.getDisplayName());
        }
    }

    private void removeWingsCommand(Player player, String name) {
        if (!this.manager.contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "This name does not exist.");
        } else {
            this.manager.removeItem(this.manager.getItemFromName(name));
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + "Removed wings.");
        }
    }

    private void createWingsCommand(Player player, String name, String displayName) {
        if (this.manager.contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + "This name does already exist.");
        } else {
            this.manager.addItem(new WingsData(name, displayName));
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + "Created wings.");
        }
    }
}

