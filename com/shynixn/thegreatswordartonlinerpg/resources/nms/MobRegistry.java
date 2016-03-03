/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import org.bukkit.World;

public interface MobRegistry {
    public void registerAll();

    public void unregisterAll();

    public SaoMob createMob(World var1, String var2, MobEquipment var3);
}

