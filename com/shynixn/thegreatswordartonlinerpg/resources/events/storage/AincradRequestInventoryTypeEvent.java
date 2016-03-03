/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.storage;

import com.shynixn.thegreatswordartonlinerpg.resources.enums.InventoryType;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradPlayerEvent;
import org.bukkit.entity.Player;

public class AincradRequestInventoryTypeEvent
extends AincradPlayerEvent {
    private InventoryType inventoryType;

    public AincradRequestInventoryTypeEvent(Player player) {
        super(player);
    }

    public InventoryType getInventoryType() {
        return this.inventoryType;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }
}

