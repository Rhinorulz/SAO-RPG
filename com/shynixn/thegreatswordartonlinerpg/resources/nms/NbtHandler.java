/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public interface NbtHandler {
    public void prepareArmorStand(ArmorStand var1);

    public void damageArmorStand(ArmorStand var1);

    public void updateFlyPosition(Player var1, ArmorStand var2);
}

