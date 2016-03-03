/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.wings;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.Wings;
import org.bukkit.entity.Entity;

public interface BannerWingsApiListener {
    public void refreshWingsEvent(Entity var1, Wings var2);

    public void enableWingsEvent(Entity var1, Wings var2);

    public void disableWingsEvent(Entity var1, Wings var2);
}

