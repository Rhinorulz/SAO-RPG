/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Permission;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.SpigotPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TheGreatSwordArtOnlineRPG
extends SpigotPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.registerUpdater((Object)ChatColor.AQUA + "TheGreatSwordArtOnlineRPG is out dated!\n" + (Object)ChatColor.BOLD + "Download the newest file on http://dev.bukkit.org/bukkit-plugins/thegreatswordartonlinerpg/", (Object)ChatColor.GREEN + "There are no new updates for TheGreatSwordArtOnlineRPG!", 87768);
        this.checkfForUpdates();
        this.checkfPluginMetrics();
        Cardinal.activateCardinal(this);
        Cardinal.reload();
        Cardinal.reloadPlayers();
    }

    @Override
    public void onDisable() {
        Cardinal.shutdown(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (cmd.getName().equalsIgnoreCase("sao") && player.hasPermission(Permission.SAO_COMMANDS.toString())) {
                player.sendMessage("");
                player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin v2.7      ");
                player.sendMessage("");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/cardinal - All cardinal commands.");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoitems - All Commands for items.");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills - All Commands for skills.");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saofloors - All Commands for floors.");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/aloraces - All Commands for races.");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/alowings - All Commands for wings.");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saospawns - All Commands for spawnpoints.");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs - All Commands for mobs.");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saodev - All Commands for developers.");
                player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoreload - Reloads all resources.");
                player.sendMessage("");
                player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/1\u2510                            ");
                player.sendMessage("");
            }
            if (cmd.getName().equalsIgnoreCase("saoreload") && player.hasPermission(Permission.SAO_COMMANDS.toString())) {
                Cardinal.reload();
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + "Reloaded TheGreatSwordArtOnlineRPG.");
            }
        }
        return true;
    }
}

