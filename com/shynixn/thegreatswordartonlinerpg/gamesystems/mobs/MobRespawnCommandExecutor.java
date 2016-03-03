/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnpoint;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobSystem;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobRespawnCommandExecutor
extends BukkitCommands {
    private MobRespawnManager manager;
    private MobSystem mobManager;

    public MobRespawnCommandExecutor(MobRespawnManager manager, MobSystem mobManager, JavaPlugin plugin) {
        super("saospawns", plugin);
        this.manager = manager;
        this.mobManager = mobManager;
    }

    @Override
    public void playerSendCommandEvent(Player player, String[] args) {
        if (args.length == 8 && args[0].equalsIgnoreCase("add") && BukkitUtilities.u().tryParseInt(args[3]) && BukkitUtilities.u().tryParseInt(args[4]) && BukkitUtilities.u().tryParseInt(args[5]) && BukkitUtilities.u().tryParseInt(args[6]) && BukkitUtilities.u().tryParseInt(args[7])) {
            this.addMobSpawnCommand(player, args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            this.removeMobSpawnCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
            this.toggleMobSpawn(player, args[1]);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("maxrespawndelay") && BukkitUtilities.u().tryParseInt(args[2])) {
            this.setMaxRespawningDelayCommand(player, args[1], Integer.parseInt(args[2]));
        } else if (args.length == 3 && args[0].equalsIgnoreCase("maxdespawndelay") && BukkitUtilities.u().tryParseInt(args[2])) {
            this.setMaxDespawningDelayCommand(player, args[1], Integer.parseInt(args[2]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("spawns")) {
            this.printMobSpawnsCommand(player);
        } else {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Spawns  ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saospawns add <id> <mob> <rx> <ry> <rz> <rd> <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saospawns remove <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saospawns toggle <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saospawns maxrespawndelay <id> <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saospawns maxdespawndelay <id> <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saospawns spawns");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/1\u2510                            ");
            player.sendMessage("");
        }
    }

    private void setMaxDespawningDelayCommand(Player player, String mob, int amount) {
        if (!this.manager.getItemKeys().contains(mob)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_SPAWN_NOT_EXIST);
        } else if (amount < 1 && amount > 1000000) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_AMOUNT_SMALLER);
        } else {
            MobRespawnpoint respawnpoint = this.manager.getItemFromName(mob);
            respawnpoint.setMaxDespawnDelay(amount);
            this.manager.save(respawnpoint);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.RESPAWN_UPDATE_MAXDESPAWN);
        }
    }

    private void setMaxRespawningDelayCommand(Player player, String mob, int amount) {
        if (!this.manager.getItemKeys().contains(mob)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_SPAWN_NOT_EXIST);
        } else if (amount < 1 && amount > 1000000) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_AMOUNT_SMALLER);
        } else {
            MobRespawnpoint respawnpoint = this.manager.getItemFromName(mob);
            respawnpoint.setMaxRespawnDelay(amount);
            this.manager.save(respawnpoint);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.RESPAWN_UPDATE_MAXRESPAWN);
        }
    }

    private void toggleMobSpawn(Player player, String mob) {
        if (!this.manager.getItemKeys().contains(mob)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_SPAWN_NOT_EXIST);
        } else {
            MobRespawnpoint respawnpoint = this.manager.getItemFromName(mob);
            if (respawnpoint.isEnabled()) {
                respawnpoint.setEnabled(false);
                this.manager.clearRespawnPoint(respawnpoint);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.RESPAWN_DISABLED);
            } else {
                respawnpoint.setEnabled(true);
                this.manager.updateMobRespawnPoint(respawnpoint);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.RESPAWN_ENABLED);
            }
            this.manager.save(respawnpoint);
        }
    }

    private void printMobSpawnsCommand(Player player) {
        player.sendMessage((Object)ChatColor.WHITE + (Object)ChatColor.ITALIC + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.RESPAWN_LIST);
        for (BukkitObject object : this.manager.getItems()) {
            player.sendMessage((Object)ChatColor.GRAY + ((MobRespawnpoint)object).toString());
        }
    }

    private void removeMobSpawnCommand(Player player, String name) {
        if (!this.manager.getItemKeys().contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_SPAWN_NOT_EXIST);
        } else {
            this.manager.removeItem(this.manager.getItemFromName(name));
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.RESPAWN_REMOVE);
        }
    }

    private void addMobSpawnCommand(Player player, String name, String mob, int xr, int yr, int zr, int dr, int amount) {
        if (this.manager.getItemKeys().contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_SPAWN_ALREADY_EXIST);
        } else if (!this.mobManager.getItemKeys().contains(mob)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_MOB_NOT_EXIST);
        } else if (xr < 1 || xr > 1000) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_RADIUS_X);
        } else if (yr < 1 || yr > 1000) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_RADIUS_Y);
        } else if (zr < 1 || zr > 1000) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_RADIUS_Z);
        } else if (dr < 1 || dr > 1000) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_RADIUS_D);
        } else if (amount < 1 || amount > 1000) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RESPAWN_AMOUNT_SMALLER2);
        } else {
            MobRespawnpoint respawnpoint = new MobRespawnpoint(name, new BukkitLocation(player.getLocation()), xr, yr, zr, dr, amount, mob);
            this.manager.addItem(respawnpoint);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.RESPAWN_ADDED);
        }
    }
}

