/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.mobs;

import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;

public final class AincradMobExistEvent
extends AincradEvent {
    private boolean exist = false;
    private String name;

    public AincradMobExistEvent(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        this.name = name;
    }

    public boolean isExist() {
        return this.exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getName() {
        return this.name;
    }
}

