/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events;

import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradLocationEvent;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AincradPlayerEvent
extends AincradLocationEvent {
    private Player player;

    public AincradPlayerEvent(Player player) {
        super(AincradPlayerEvent.getPlayerLocation(player));
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getUUID() {
        return this.player.getUniqueId().toString();
    }

    private static Location getPlayerLocation(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null!");
        }
        return player.getLocation();
    }
}

