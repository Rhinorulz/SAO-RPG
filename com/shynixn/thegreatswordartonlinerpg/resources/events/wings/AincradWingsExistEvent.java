/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.wings;

import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;

public final class AincradWingsExistEvent
extends AincradEvent {
    private boolean exist = false;
    private String wingsName;

    public AincradWingsExistEvent(String wingsName) {
        if (wingsName == null) {
            throw new IllegalArgumentException("WingsName cannot be null!");
        }
        this.wingsName = wingsName;
    }

    public boolean isExist() {
        return this.exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getWingsName() {
        return this.wingsName;
    }
}

