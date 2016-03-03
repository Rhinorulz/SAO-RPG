/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.floors;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.Floor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.FloorSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.mobs.AincradMobExistEvent;
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

public final class FloorCommandExecutor
extends BukkitCommands {
    private FloorSystem floorManager;

    public FloorCommandExecutor(FloorSystem floorManager, JavaPlugin plugin) {
        super("saofloors", plugin);
        this.floorManager = floorManager;
    }

    @Override
    public void playerSendCommandEvent(Player player, String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("create") && BukkitUtilities.u().tryParseInt(args[1]) && Integer.parseInt(args[1]) > 0) {
            this.createFloorCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeFloorCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("floors")) {
            this.printFloorsCommand(player);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("boss")) {
            this.setBossCommand(player, Integer.parseInt(args[1]), args[2]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("finishfield") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.setFinishFloorLocationCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("previouswarpfield") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.setPreviousTeleportingLocationCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("nextwarpfield") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.setNextTeleportingLocationCommand(player, Integer.parseInt(args[1]));
        } else {
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.TITLE) + ": Floors   ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saofloors create <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saofloors remove <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saofloors boss <id> <mobname>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saofloors finishfield <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saofloors previouswarpfield <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saofloors nextwarpfield <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saofloors floors - Displays all added floors.");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/1\u2510                            ");
            player.sendMessage("");
        }
    }

    private void setBossCommand(Player player, int floorid, String mobName) {
        AincradMobExistEvent event = new AincradMobExistEvent(mobName);
        Cardinal.call().notifyMobSystem(event);
        if (!this.floorManager.getFloorIds().contains(floorid)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.FLOOR_FLOOR_NOT_EXIST);
        } else if (!event.isExist()) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_NOT_EXISTS);
        } else {
            this.floorManager.getFloorFromId(floorid).setBossName(mobName);
            this.floorManager.save(this.floorManager.getFloorFromId(floorid));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.FLOOR_SET_BOSS);
        }
    }

    private void setNextTeleportingLocationCommand(Player player, int floorid) {
        if (!this.floorManager.getFloorIds().contains(floorid)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.FLOOR_FLOOR_NOT_EXIST);
        } else {
            this.floorManager.getFloorFromId(floorid).setNextPortal(new BukkitLocation(player.getLocation()));
            this.floorManager.save(this.floorManager.getFloorFromId(floorid));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.FLOOR_SET_NEXT_PORTAL);
        }
    }

    private void setPreviousTeleportingLocationCommand(Player player, int floorid) {
        if (!this.floorManager.getFloorIds().contains(floorid)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.FLOOR_FLOOR_NOT_EXIST);
        } else {
            this.floorManager.getFloorFromId(floorid).setPreviousPortal(new BukkitLocation(player.getLocation()));
            this.floorManager.save(this.floorManager.getFloorFromId(floorid));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.FLOOR_SET_PREVIOUS_PORTAL);
        }
    }

    private void createFloorCommand(Player player, int floorid) {
        if (this.floorManager.getFloorIds().contains(floorid) && Cardinal.getGenericSystem().isValidAction(player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.FLOOR_FLOOR_EXISTS);
        } else {
            this.floorManager.addItem(new Floor(new BukkitLocation(player.getLocation()), floorid));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.FLOOR_FLOOR_CREATED)[0] + floorid + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.FLOOR_FLOOR_CREATED)[1]);
        }
    }

    private void removeFloorCommand(Player player, int floorid) {
        if (!this.floorManager.getFloorIds().contains(floorid)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.FLOOR_FLOOR_NOT_EXIST);
        } else {
            this.floorManager.removeItem(this.floorManager.getFloorFromId(floorid));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.FLOOR_FLOOR_REMOVED)[0] + floorid + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.FLOOR_FLOOR_REMOVED)[1]);
        }
    }

    private void setFinishFloorLocationCommand(Player player, int floorid) {
        if (!this.floorManager.getFloorIds().contains(floorid)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.FLOOR_FLOOR_NOT_EXIST);
        } else {
            this.floorManager.getFloorFromId(floorid).setFinishPoint(new BukkitLocation(player.getLocation()));
            this.floorManager.save(this.floorManager.getFloorFromId(floorid));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.FLOOR_FINISH_MESSAGE);
        }
    }

    private void printFloorsCommand(Player player) {
        Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.ITALIC + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.FLOOR_LIST_FLOORS);
        for (BukkitObject object : this.floorManager.getItems()) {
            Floor floor = (Floor)object;
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.FLOOR_SHOW_FLOOR)[0] + floor.getFloorId() + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.FLOOR_SHOW_FLOOR)[1] + floor.getSpawnPoint().toString());
        }
    }
}

