/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class CardinalDeveloperCommandExecutor
extends BukkitCommands {
    public CardinalDeveloperCommandExecutor(JavaPlugin plugin) {
        super("saodev", plugin);
    }

    @Override
    public void playerSendCommandEvent(Player player, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("join")) {
            this.playerJoinGameCommand(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
            this.playerLeaveGameCommand(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("killnext")) {
            this.killNextCommand(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("killall")) {
            this.killAllCommand(player);
        } else {
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.TITLE) + " : Dev");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saodev join");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saodev leave");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saodev killnext");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saodev killall");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + (Object)ChatColor.RED + CardinalLanguage.CommandExecutor.DEVELOPER_CRITICAL_DAMAGE_WARNING);
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/1\u2510                            ");
            player.sendMessage("");
        }
    }

    private void killAllCommand(Player player) {
        for (Entity entity : player.getWorld().getEntities()) {
            if (entity instanceof Player) continue;
            entity.remove();
        }
        player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.DEVELOPER_REMOVE_ALL_ENTITIES_SUCCESS);
    }

    private void killNextCommand(Player player) {
        double shortestdistance = 1000000.0;
        Entity entity2 = null;
        for (Entity entity : player.getWorld().getEntities()) {
            if (entity instanceof Player || entity.getLocation().distance(player.getLocation()) >= shortestdistance) continue;
            shortestdistance = entity.getLocation().distance(player.getLocation());
            entity2 = entity;
        }
        if (entity2 != null) {
            entity2.remove();
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.DEVELOPER_REMOVE_NEXT_ENTITY_SUCCESS + entity2.getType().name().toLowerCase() + ".");
        }
    }

    private void playerJoinGameCommand(Player player) {
        Cardinal.getGenericSystem().loginPlayer(player);
    }

    private void playerLeaveGameCommand(Player player) {
        Cardinal.getGenericSystem().logoutPlayer(player);
    }
}

