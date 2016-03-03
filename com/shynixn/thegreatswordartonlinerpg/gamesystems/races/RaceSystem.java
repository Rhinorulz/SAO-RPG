/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.races;

import com.shynixn.thegreatswordartonlinerpg.TheGreatSwordArtOnlineRPG;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.races.Race;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.races.RaceCommandExecutor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.races.RaceFileManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.PlayerSave;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradPlayerLoginEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradRespawnEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.races.RacesRequestEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradRequestPlayerSaveEvent;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitLocation;
import libraries.com.shynixn.utilities.BukkitManager;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class RaceSystem
extends BukkitManager
implements CardinalSystem {
    public RaceSystem(TheGreatSwordArtOnlineRPG plugin) {
        super(new RaceFileManager(plugin), BukkitManager.SaveType.SINGEL);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.races.RaceCommandExecutor(this, plugin);
    }

    @Override
    public Race getItemFromName(String name) {
        return (Race)super.getItemFromName(name);
    }

    @Override
    public void handleCardinalEvent(AincradEvent event) throws Exception {
        if (event instanceof RacesRequestEvent) {
            ((RacesRequestEvent)event).setRaces(this.getItems());
        } else if (event instanceof AincradPlayerLoginEvent) {
            this.handleAincradPlayerLogInEvent((AincradPlayerLoginEvent)event);
        } else if (event instanceof AincradRespawnEvent) {
            this.handleRespawnEvent((AincradRespawnEvent)event);
        }
    }

    private void handleRespawnEvent(AincradRespawnEvent event) {
        AincradRequestPlayerSaveEvent playerSaveEvent = new AincradRequestPlayerSaveEvent(event.getPlayer());
        Cardinal.call().notifyStorageSystem(playerSaveEvent);
        if (this.getItemFromName(playerSaveEvent.getPlayerSave().getRaceName()) == null) {
            Cardinal.call().sendException(new CardinalException("Race not found", "You cannot respawn correctly", "Create a race", Priority.MEDIUM));
        } else if (this.getItemFromName(playerSaveEvent.getPlayerSave().getRaceName()).getSpawnPoint() == null) {
            Cardinal.call().sendException(new CardinalException("Racespawnpoint not found", "You cannot respawn correctly", "Create a racespawnpoint", Priority.MEDIUM));
        } else {
            event.setRespawnLocation(this.getItemFromName(playerSaveEvent.getPlayerSave().getRaceName()).getSpawnPoint().getLocation());
        }
    }

    private void handleAincradPlayerLogInEvent(AincradPlayerLoginEvent event) throws Exception {
        AincradRequestPlayerSaveEvent playerSaveEvent = new AincradRequestPlayerSaveEvent(event.getPlayer());
        Cardinal.call().notifyStorageSystem(playerSaveEvent);
        if (Cardinal.getSettings().isLoginSpawnLastLocation() && !playerSaveEvent.getPlayerSave().getRaceName().equals("") && playerSaveEvent.getPlayerSave().getLastLocation() != null && playerSaveEvent.getPlayerSave().getLastLocation().getWorld() != null && playerSaveEvent.getPlayerSave().getLastLocation().getLocation() != null) {
            event.getPlayer().teleport(playerSaveEvent.getPlayerSave().getLastLocation().getLocation());
        } else if (!playerSaveEvent.getPlayerSave().getRaceName().equals("")) {
            try {
                event.getPlayer().teleport(this.getItemFromName(playerSaveEvent.getPlayerSave().getRaceName()).getSpawnPoint().getLocation());
            }
            catch (Exception var3_3) {
                // empty catch block
            }
        }
    }
}

