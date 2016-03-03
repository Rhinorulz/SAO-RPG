/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.storage;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.PlayerSave;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradPlayerEvent;
import org.bukkit.entity.Player;

public class AincradRequestPlayerSaveEvent
extends AincradPlayerEvent {
    private PlayerSave playerSave;

    public AincradRequestPlayerSaveEvent(Player player) {
        super(player);
    }

    public PlayerSave getPlayerSave() {
        return this.playerSave;
    }

    public void setPlayerSave(PlayerSave playerSave) {
        this.playerSave = playerSave;
    }
}

