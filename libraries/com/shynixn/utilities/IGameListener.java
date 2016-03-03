/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package libraries.com.shynixn.utilities;

import libraries.com.shynixn.utilities.BukkitArenaAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public interface IGameListener {
    public void onPlayerDeathEvent(Player var1, BukkitArenaAPI.Game var2);

    public void onPlayerDamageEvent(Player var1, BukkitArenaAPI.Game var2, EntityDamageEvent var3);
}

