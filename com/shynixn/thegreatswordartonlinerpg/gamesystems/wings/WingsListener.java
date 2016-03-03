/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerInteractAtEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.world.ChunkUnloadEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.wings;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.BannerWingsApi;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.BannerWingsApiListener;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.Wings;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsData;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsSystem;
import java.util.List;
import libraries.com.darkblade12.particleeffects.ParticleEffect;
import libraries.com.shynixn.utilities.BukkitEvents;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class WingsListener
extends BukkitEvents
implements BannerWingsApiListener {
    private WingsSystem manager;

    public WingsListener(WingsSystem manager, JavaPlugin plugin) {
        super(plugin);
        this.manager = manager;
        BannerWingsApi.addListener(this);
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        if (BannerWingsApi.hasWings((Entity)event.getPlayer())) {
            BannerWingsApi.removeWings((Entity)event.getPlayer());
        }
    }

    @EventHandler
    public void playerRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (this.isWingsItem(event.getPlayer(), event.getPlayer().getItemInHand()) != null && !BannerWingsApi.hasWings((Entity)event.getPlayer())) {
                WingsData wingsData = this.isWingsItem(event.getPlayer(), event.getPlayer().getItemInHand());
                BannerWingsApi.setWings((Entity)event.getPlayer(), new Wings((Entity)event.getPlayer(), wingsData));
                ItemStack itemStack = new ItemStack(Material.STRING);
                BukkitUtilities.u().nameItem(itemStack, BannerWingsApi.DISPLAY_REMOVER, BannerWingsApi.LORE_REMOVER);
                event.getPlayer().setItemInHand(itemStack);
                event.getPlayer().updateInventory();
                BannerWingsApi.showWings((Entity)event.getPlayer());
                if (event.getPlayer().getInventory().getHeldItemSlot() < 8) {
                    event.getPlayer().getInventory().setHeldItemSlot(event.getPlayer().getInventory().getHeldItemSlot() + 1);
                } else {
                    event.getPlayer().getInventory().setHeldItemSlot(0);
                }
                event.setCancelled(true);
            } else if (BukkitUtilities.u().compareItemNames(event.getPlayer().getItemInHand(), BannerWingsApi.DISPLAY_REMOVER, BannerWingsApi.LORE_REMOVER, Material.STRING) && BannerWingsApi.hasWings((Entity)event.getPlayer())) {
                WingsData wingsData = BannerWingsApi.getWingsDataClone((Entity)event.getPlayer());
                ItemStack itemStack = new ItemStack(Material.FEATHER);
                BukkitUtilities.u().nameItem(itemStack, wingsData.getDisplayName(), (Object)ChatColor.YELLOW + "Wings: " + wingsData.getName());
                BannerWingsApi.removeWings((Entity)event.getPlayer());
                event.getPlayer().setItemInHand(itemStack);
                event.getPlayer().updateInventory();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntitChunkUnload(ChunkUnloadEvent event) {
        Entity[] arrentity = event.getChunk().getEntities();
        int n = arrentity.length;
        int n2 = 0;
        while (n2 < n) {
            Entity entity = arrentity[n2];
            if (BannerWingsApi.hasWings(entity)) {
                BannerWingsApi.removeWings(entity);
            }
            ++n2;
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (BannerWingsApi.hasWings((Entity)event.getEntity())) {
            BannerWingsApi.removeWings((Entity)event.getEntity());
        }
    }

    @EventHandler
    public void playerInterActEntityNotArmorStandEvent(PlayerInteractAtEntityEvent event) {
        if (BannerWingsApi.isWing(event.getRightClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent event) {
        if (BannerWingsApi.hasWings(event.getEntity()) && !event.isCancelled()) {
            BannerWingsApi.damageWings(event.getEntity());
        }
    }

    @Override
    public void refreshWingsEvent(Entity entity, Wings wings) {
        if (wings.getCycleNumber() < -2.5) {
            for (Player player : entity.getWorld().getPlayers()) {
                player.playSound(entity.getLocation(), Sound.ENDERDRAGON_WINGS, 2.0f, 1.0f);
            }
            ParticleEffect.CLOUD.display(1.0f, 1.0f, 1.0f, 0.1f, 10, entity.getLocation(), entity.getWorld().getPlayers());
        }
    }

    private WingsData isWingsItem(Player player, ItemStack itemStack) {
        try {
            for (BukkitObject object : this.manager.getItems()) {
                if (!((String)itemStack.getItemMeta().getLore().get(0)).equals((Object)ChatColor.YELLOW + "Wings: " + object.getName())) continue;
                return (WingsData)object;
            }
        }
        catch (Exception object) {
            // empty catch block
        }
        return null;
    }

    @Override
    public void enableWingsEvent(Entity entity, Wings wings) {
    }

    @Override
    public void disableWingsEvent(Entity entity, Wings wings) {
    }
}

