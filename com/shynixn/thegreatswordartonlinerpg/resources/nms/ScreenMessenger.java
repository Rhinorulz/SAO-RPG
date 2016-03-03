/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms;

import com.shynixn.thegreatswordartonlinerpg.resources.nms.NMSRegistry;
import org.bukkit.entity.Player;

public interface ScreenMessenger {
    public void setActionBar(Player var1, String var2);

    public void setPlayerTitle(Player var1, String var2, String var3, int var4, int var5, int var6);

    public void setHeaderAndFooter(Player var1, String var2, String var3);

    public void setPlayerTitleMoving(Player var1, String var2, int var3, NMSRegistry.TextSpeed var4, int var5);

    public void setPlayerSubTitlePercent(Player var1, String[] var2, NMSRegistry.TextSpeed var3);
}

