/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.races;

import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitObject;

public final class RacesRequestEvent
extends AincradEvent {
    private List<BukkitObject> races;

    public List<BukkitObject> getRaces() {
        return this.races;
    }

    public void setRaces(List<BukkitObject> races) {
        this.races = races;
    }
}

