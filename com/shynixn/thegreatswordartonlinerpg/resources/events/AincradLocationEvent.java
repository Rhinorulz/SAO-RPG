/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events;

import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import org.bukkit.Location;
import org.bukkit.World;

public class AincradLocationEvent
extends AincradEvent {
    private Location location;

    public AincradLocationEvent(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        }
        this.location = location;
    }

    public World getWorld() {
        return this.location.getWorld();
    }

    public Location getLocation() {
        return this.location;
    }
}

