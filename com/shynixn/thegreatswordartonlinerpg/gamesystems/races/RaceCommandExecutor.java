/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.races;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.races.Race;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.races.RaceSystem;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class RaceCommandExecutor
extends BukkitCommands {
    private RaceSystem manager;

    public RaceCommandExecutor(RaceSystem manager, JavaPlugin plugin) {
        super("aloraces", plugin);
        this.manager = manager;
    }

    @Override
    public void playerSendCommandEvent(Player player, String[] args) {
        if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
            this.createRaceCommand(player, args[1], args[2]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            this.removeRaceCommand(player, args[1]);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("races")) {
            this.printRacesCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("spawnpoint")) {
            this.setRaceSpawnPointCommand(player, args[1]);
        } else {
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.TITLE) + ": Races   ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/aloraces create <name> <displayname>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/aloraces remove <name>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/aloraces spawnpoint <name>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/aloraces races");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/1\u2510                            ");
            player.sendMessage("");
        }
    }

    private void createRaceCommand(Player player, String name, String displayName) {
        if (this.manager.contains(name)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RACES_RACE_EXISTS);
        } else {
            this.manager.addItem(new Race(name, displayName));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.RACES_RACE_CREATED);
        }
    }

    private void removeRaceCommand(Player player, String name) {
        if (!this.manager.contains(name)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RACES_RACE_NOT_EXIST);
        } else {
            this.manager.removeItem(this.manager.getItemFromName(name));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.RACES_RACE_REMOVED);
        }
    }

    private void printRacesCommand(Player player) {
        Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.ITALIC + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.RACE_LIST_RACES);
        for (BukkitObject object : this.manager.getItems()) {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + object.getName() + " " + object.getDisplayName());
        }
    }

    private void setRaceSpawnPointCommand(Player player, String name) {
        if (!this.manager.contains(name)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.RACES_RACE_NOT_EXIST);
        } else {
            this.manager.getItemFromName(name).setSpawnPoint(new BukkitLocation(player.getLocation()));
            this.manager.save(this.manager.getItemFromName(name));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.RACE_SET_SPAWNPOINT);
        }
    }
}

