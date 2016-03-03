/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.util.Vector
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.floors;

import com.shynixn.thegreatswordartonlinerpg.TheGreatSwordArtOnlineRPG;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.Floor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.floors.FloorSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.TeleportEffect;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.floors.AincradBeatFloorEvent;
import libraries.com.shynixn.utilities.BukkitEvents;
import libraries.com.shynixn.utilities.BukkitLocation;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class FloorListener
extends BukkitEvents
implements TeleportEffect.ITeleport {
    private JavaPlugin plugin;
    private FloorSystem floorSystem;

    public FloorListener(FloorSystem floorSystem, TheGreatSwordArtOnlineRPG plugin) {
        super(plugin);
        this.plugin = plugin;
        this.floorSystem = floorSystem;
        TeleportEffect.getInstance(plugin).register(this);
    }

    @EventHandler
    public void playerChangeFloorEvent(PlayerMoveEvent event) {
        Floor floor;
        Player player;
        if (!event.getFrom().getBlock().equals((Object)event.getTo().getBlock()) && Cardinal.getGenericSystem().isValidAction(event.getPlayer()) && Cardinal.getSettings().getSystem() == CardinalSystem.SAO && (floor = this.floorSystem.getCurrentFloorFromPlayer(player = event.getPlayer())).getSpawnPoint().getWorld().getName().equals(event.getPlayer().getLocation().getWorld().getName())) {
            if (floor.getFinishPoint() != null && floor.getFinishPoint().getLocation().distance(event.getTo()) <= 5.0) {
                this.playerChangeFloorFinishPoint(player, floor);
            } else if (floor.getPreviousPortal() != null && floor.getPreviousPortal().getLocation().distance(event.getTo()) <= 2.0) {
                this.playerChangeFloorPreviousPortal(player, floor);
            } else if (floor.getNextPortal() != null && floor.getNextPortal().getLocation().distance(event.getTo()) <= 2.0) {
                this.playerChangeFloorNextPortal(player, floor);
            }
        }
    }

    private void playerChangeFloorNextPortal(Player player, Floor floor) {
        Floor nextFloor = this.floorSystem.getFloorFromId(floor.getFloorId() + 1);
        if (nextFloor == null) {
            this.sendTeleportEffect(player, floor.getSpawnPoint().getLocation());
            Cardinal.getLogger().logPlayer(player, CardinalLanguage.Cardinal.NEXT_FLOOR_NOT_EXIST);
        } else if (!this.floorSystem.hasFloorPermission(player, nextFloor.getFloorId())) {
            this.sendTeleportEffect(player, floor.getSpawnPoint().getLocation());
            Cardinal.getLogger().logPlayer(player, CardinalLanguage.Cardinal.NEXT_FLOOR_NO_PERMS);
        } else {
            this.sendTeleportEffect(player, nextFloor.getSpawnPoint().getLocation());
            this.floorSystem.setCurrentFloor(player, floor.getFloorId() + 1);
        }
    }

    private void playerChangeFloorPreviousPortal(Player player, Floor floor) {
        Floor previousFloor = this.floorSystem.getFloorFromId(floor.getFloorId() - 1);
        if (previousFloor == null) {
            this.sendTeleportEffect(player, floor.getSpawnPoint().getLocation());
            Cardinal.getLogger().logPlayer(player, CardinalLanguage.Cardinal.PREVIOUS_FLOOR_NOT_EXIST);
        } else {
            this.sendTeleportEffect(player, previousFloor.getSpawnPoint().getLocation());
            this.floorSystem.setCurrentFloor(player, floor.getFloorId() - 1);
        }
    }

    private void playerChangeFloorFinishPoint(Player player, Floor floor) {
        AincradBeatFloorEvent beatFloorEvent = new AincradBeatFloorEvent(player, floor.getFloorId() + 1);
        Cardinal.call().notifyStorageSystem(beatFloorEvent);
        Cardinal.call().notifyFloorSystem(beatFloorEvent);
    }

    private void sendTeleportEffect(Player player, Location location) {
        if (Cardinal.getSettings().isTeleportEffectEnabled()) {
            TeleportEffect.getInstance(this.plugin).playTeleportEffect(player, location, Cardinal.getSettings().getTeleportDelay());
        } else {
            player.teleport(location);
        }
    }

    @Override
    public void tickTeleportCountDownPlayer(Player player, int timeLeft) {
        if (timeLeft == 0 && Cardinal.getSettings().isTeleportJump()) {
            player.setVelocity(new Vector(0.0, 1.5, 0.0));
        }
    }

    @Override
    public void teleportPlayer(Player player, Location location) {
        Cardinal.getSettings().getTeleportParticleEffect().play((Entity)player);
        Cardinal.getSettings().getTeleportSound().play((Entity)player);
        player.teleport(location);
        Cardinal.getSettings().getTeleportParticleEffect().play((Entity)player);
        Cardinal.getSettings().getTeleportSound().play((Entity)player);
    }
}

