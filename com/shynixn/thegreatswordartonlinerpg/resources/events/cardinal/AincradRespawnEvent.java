/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal;

import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradPlayerEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AincradRespawnEvent
extends AincradPlayerEvent {
    private Location respawnLocation;

    public AincradRespawnEvent(Player player) {
        super(player);
    }

    public Location getRespawnLocation() {
        return this.respawnLocation;
    }

    public void setRespawnLocation(Location respawnLocation) {
        this.respawnLocation = respawnLocation;
    }
}

