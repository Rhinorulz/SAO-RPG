/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Sign
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.ItemDespawnEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Permission;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.PlayerSave;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradRequestPlayerSaveEvent;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitEvents;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class CardinalGenericListener
extends BukkitEvents {
    public CardinalGenericListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onCommandBlockerEvent(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().hasPermission(Permission.SAO_COMMANDS.toString()) && Cardinal.getSettings().isCommandblocker()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void joinServerEvent(PlayerJoinEvent event) {
        if (Cardinal.getSettings().getSystem() == CardinalSystem.CLASSIC) {
            Cardinal.getGenericSystem().loginPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onClickSignEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().hasPermission(Permission.SAO_JOIN.toString()) && event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.WALL_SIGN || event.getClickedBlock().getType() == Material.SIGN_POST)) {
            if (Cardinal.getStorageItems().isSignSelector(event.getPlayer().getItemInHand())) {
                Sign s = (Sign)event.getClickedBlock().getState();
                s.setLine(0, (Object)((Object)BukkitChatColor.DARK_RED) + "[SAO]");
                s.setLine(1, (Object)ChatColor.BLACK + "Click to start");
                s.setLine(2, (Object)((Object)BukkitChatColor.WHITE) + "--------");
                s.setLine(3, "");
                s.update();
            } else {
                Sign s = (Sign)event.getClickedBlock().getState();
                String[] lines = s.getLines();
                if (lines[0].equals((Object)((Object)BukkitChatColor.DARK_RED) + "[SAO]")) {
                    Cardinal.getGenericSystem().loginPlayer(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        if (Cardinal.getGenericSystem().isValidAction(event.getEntity())) {
            AincradRequestPlayerSaveEvent playerSaveEvent = new AincradRequestPlayerSaveEvent(event.getEntity());
            Cardinal.call().notifyStorageSystem(playerSaveEvent);
            if (Cardinal.getSettings().isKeepInventory()) {
                event.setKeepInventory(true);
            }
            if (Cardinal.getSettings().isNoDrops()) {
                event.getDrops().clear();
                event.setDroppedExp(0);
            }
            if (Cardinal.getSettings().isCompleteInventoryReset()) {
                playerSaveEvent.getPlayerSave().reset();
            }
            if (Cardinal.getSettings().isDeathKick()) {
                Cardinal.getGenericSystem().putOnGrayList(event.getEntity());
            } else if (Cardinal.getSettings().isDeathBan()) {
                Cardinal.getGenericSystem().putOnBlackList(event.getEntity());
            }
        }
    }

    @EventHandler
    public void leaveServerEvent(PlayerQuitEvent event) {
        Cardinal.getGenericSystem().logoutPlayer(event.getPlayer());
    }

    @EventHandler
    public void entityDeathPlayEffectEvent(EntityDeathEvent event) {
        if (Cardinal.getGenericSystem().isValidAction(event.getEntity().getLocation())) {
            if (event.getEntity() instanceof Player && Cardinal.getSettings().isDestroyingPlayerKilledAnimation()) {
                Cardinal.getSettings().getDestroyingParticleEffect().play((Entity)event.getEntity());
                Cardinal.getSettings().getDestroyingSound().play((Entity)event.getEntity());
            } else if (Cardinal.getSettings().isDestroyingMobKilledAnimation()) {
                Cardinal.getSettings().getDestroyingParticleEffect().play((Entity)event.getEntity());
                Cardinal.getSettings().getDestroyingSound().play((Entity)event.getEntity());
            }
        }
    }

    @EventHandler
    public void itemDespawnPlayEffectEvent(ItemDespawnEvent event) {
        if (Cardinal.getGenericSystem().isValidAction(event.getEntity().getLocation()) && Cardinal.getSettings().isDestroyingIemDespawnAnimation()) {
            Cardinal.getSettings().getDestroyingParticleEffect().play((Entity)event.getEntity());
            Cardinal.getSettings().getDestroyingSound().play((Entity)event.getEntity());
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onRespawnEvent(PlayerRespawnEvent event) {
        if (Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && (Cardinal.getSettings().getSystem() == CardinalSystem.SAO || Cardinal.getSettings().getSystem() == CardinalSystem.ALO)) {
            event.setRespawnLocation(Cardinal.getGenericSystem().getRespawnPoint(event.getPlayer()));
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void cancelInteractEvent(PlayerInteractEvent event) {
        if (this.isInteracting(event) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && !event.getPlayer().hasPermission(Permission.SAO_BUILD.toString()) && Cardinal.getSettings().isWorldprotection()) {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void cancelSpecialItem(PlayerInteractEvent event) {
        if (this.isInteracting(event) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && Cardinal.getStorageItems().isStorageItem(event.getPlayer().getItemInHand())) {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }

    private boolean isInteracting(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            return true;
        }
        return false;
    }
}

