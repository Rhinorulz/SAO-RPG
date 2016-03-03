/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.floors;

import com.shynixn.thegreatswordartonlinerpg.TheGreatSwordArtOnlineRPG;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.Floor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.FloorCommandExecutor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.FloorFileManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.FloorListener;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.PlayerSave;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerLogOutEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerLoginEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerPreLoginEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradRespawnEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.floors.AincradBeatBossEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.floors.AincradBeatFloorEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradRequestPlayerSaveEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.ScreenMessenger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitManager;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class FloorSystem
extends BukkitManager
implements CardinalSystem {
    private HashMap<Player, Integer> currentfloor = new HashMap();

    public FloorSystem(TheGreatSwordArtOnlineRPG plugin) {
        super(new FloorFileManager(plugin), BukkitManager.SaveType.SINGEL);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.FloorCommandExecutor(this, plugin);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.FloorListener(this, plugin);
    }

    @Override
    public void handleCardinalEvent(AincradEvent event) throws Exception {
        if (event instanceof AincradPlayerPreLoginEvent) {
            this.handleAincradPlayerPreLogInEvent((AincradPlayerPreLoginEvent)event);
        } else if (event instanceof AincradPlayerLoginEvent) {
            this.handleAincradPlayerLogInEvent((AincradPlayerLoginEvent)event);
        } else if (event instanceof AincradPlayerLogOutEvent) {
            this.handleAincradPlayerLogOutEvent((AincradPlayerLogOutEvent)event);
        } else if (event instanceof AincradRespawnEvent) {
            this.handleAincradRespawnEvent((AincradRespawnEvent)event);
        } else if (event instanceof AincradBeatFloorEvent) {
            this.handleBeatFloorEvent((AincradBeatFloorEvent)event);
        } else if (event instanceof AincradBeatBossEvent) {
            this.handleAincradBeatBossEvent((AincradBeatBossEvent)event);
        }
    }

    private void handleBeatFloorEvent(AincradBeatFloorEvent event) {
        if (this.getFloorFromId(event.getNextFloorId()) != null) {
            this.setCurrentFloor(event.getPlayer(), event.getNextFloorId());
        }
        if (Cardinal.getSettings().isFloorBeatautomaticTeleport()) {
            event.getPlayer().teleport(this.getFloorFromId(event.getNextFloorId()).getSpawnPoint().getLocation());
        }
        if (Cardinal.getSettings().isFloorBeatScreenMessage()) {
            Cardinal.getRegistry().getScreenMessenger().setPlayerTitle(event.getPlayer(), Cardinal.getSettings().getFloorBeatScreenTitle(), String.valueOf(CardinalLanguage.fix(Cardinal.getSettings().getFloorBeatScreenSubTitle())[0]) + (event.getNextFloorId() - 1) + CardinalLanguage.fix(Cardinal.getSettings().getFloorBeatScreenSubTitle())[1], 5, 1200, 10);
        }
        if (Cardinal.getSettings().isFloorBeatAnounceMent()) {
            BukkitUtilities.u().sendServerMessage(Cardinal.getSettings().getFloorBeatAnounceMentText(), BukkitUtilities.u().getOnlinePlayers());
        }
    }

    private void handleAincradBeatBossEvent(AincradBeatBossEvent event) {
        for (BukkitObject object : this.getItems()) {
            Floor floor = (Floor)object;
            try {
                if (floor.getBossName() == null || !this.currentfloor.containsKey((Object)event.getPlayer()) || !floor.getBossName().equals(event.getMobName()) || !this.currentfloor.containsKey((Object)event.getPlayer()) || floor.getFloorId() != this.currentfloor.get((Object)event.getPlayer()).intValue()) continue;
                AincradBeatFloorEvent beatFloorEvent = new AincradBeatFloorEvent(event.getPlayer(), floor.getFloorId() + 1);
                Cardinal.call().notifyStorageSystem(beatFloorEvent);
                Cardinal.call().notifyFloorSystem(beatFloorEvent);
                break;
            }
            catch (Exception beatFloorEvent) {
                // empty catch block
            }
        }
    }

    private void handleAincradPlayerPreLogInEvent(AincradPlayerPreLoginEvent event) throws Exception {
        if (this.getFloorFromId(1) == null) {
            event.setCancelled(true);
            throw new CardinalException("Floor 1 not found", "You cannot link start correctly", "Set a floor 1 with the floor commands", Priority.MEDIUM);
        }
    }

    private void handleAincradRespawnEvent(AincradRespawnEvent event) {
        Floor floor = this.getFloorFromId(this.currentfloor.get((Object)event.getPlayer()));
        event.setRespawnLocation(floor.getSpawnPoint().getLocation());
    }

    private void handleAincradPlayerLogInEvent(AincradPlayerLoginEvent event) throws Exception {
        AincradRequestPlayerSaveEvent playerSaveEvent = new AincradRequestPlayerSaveEvent(event.getPlayer());
        Cardinal.call().notifyStorageSystem(playerSaveEvent);
        if (Cardinal.getSettings().isLoginSpawnLastLocation() && this.getFloorFromId(playerSaveEvent.getPlayerSave().getFloorId()) != null && playerSaveEvent.getPlayerSave().getLastLocation() != null && playerSaveEvent.getPlayerSave().getLastLocation().getWorld() != null && playerSaveEvent.getPlayerSave().getLastLocation().getLocation() != null) {
            event.getPlayer().teleport(playerSaveEvent.getPlayerSave().getLastLocation().getLocation());
            this.setCurrentFloor(event.getPlayer(), playerSaveEvent.getPlayerSave().getFloorId());
        } else if (this.getFloorFromId(1) != null) {
            event.getPlayer().teleport(this.getFloorFromId(1).getSpawnPoint().getLocation());
            this.currentfloor.put(event.getPlayer(), 1);
        }
    }

    private void handleAincradPlayerLogOutEvent(AincradPlayerLogOutEvent event) {
        AincradRequestPlayerSaveEvent playerSaveEvent = new AincradRequestPlayerSaveEvent(event.getPlayer());
        Cardinal.call().notifyStorageSystem(playerSaveEvent);
        playerSaveEvent.getPlayerSave().setFloorId(this.getCurrentFloorFromPlayer(event.getPlayer()).getFloorId());
        this.removePlayerFromFloors(event.getPlayer());
    }

    protected void setCurrentFloor(Player player, int floorid) {
        this.currentfloor.put(player, floorid);
    }

    protected boolean hasFloorPermission(Player player, int floorid) {
        AincradRequestPlayerSaveEvent requestPlayerSaveEvent = new AincradRequestPlayerSaveEvent(player);
        Cardinal.call().notifyStorageSystem(requestPlayerSaveEvent);
        if (this.getItemKeys().contains(String.valueOf(floorid)) && requestPlayerSaveEvent.getPlayerSave().getAccessFloors().contains(floorid)) {
            return true;
        }
        return false;
    }

    private void removePlayerFromFloors(Player player) {
        if (this.currentfloor.containsKey(player.getUniqueId().toString())) {
            this.currentfloor.remove(player.getUniqueId().toString());
        }
    }

    protected Floor getCurrentFloorFromPlayer(Player player) {
        if (this.currentfloor.containsKey((Object)player)) {
            return this.getFloorFromId(this.currentfloor.get((Object)player));
        }
        return null;
    }

    protected Floor getFloorFromId(int id) {
        return (Floor)this.getItemFromName(String.valueOf(id));
    }

    protected List<Integer> getFloorIds() {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (String s : this.getItemKeys()) {
            ids.add(Integer.parseInt(s));
        }
        return ids;
    }
}

