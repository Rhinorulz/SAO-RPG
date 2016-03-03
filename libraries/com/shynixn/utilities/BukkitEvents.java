/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package libraries.com.shynixn.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitEvents
implements Listener {
    public BukkitEvents(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }

    @EventHandler
    public final void playerClickEvent(PlayerInteractEvent event) {
        if (!(event.isCancelled() || event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
            this.playerRightClickEvent(event.getPlayer(), event.getPlayer().getItemInHand(), event);
        }
        if (!(event.isCancelled() || event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK)) {
            this.playerLeftClickEvent(event.getPlayer(), event.getPlayer().getItemInHand(), event);
        }
    }

    public void playerRightClickEvent(Player player, ItemStack itemStack, PlayerInteractEvent event) {
    }

    public void playerLeftClickEvent(Player player, ItemStack itemStack, PlayerInteractEvent event) {
    }
}

