/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArena;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArenaCommandExecutor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArenaManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobFileManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnManager;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradBackCommandEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.mobs.AincradMobExistEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.mobs.AincradRequestMobFromEntity;
import com.shynixn.thegreatswordartonlinerpg.resources.events.mobs.AincradSpawnMobEvent;
import java.util.HashMap;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitManager;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobSystem
extends BukkitManager
implements CardinalSystem {
    private MobArenaManager arenaManager;
    private MobRespawnManager respawnManager;
    protected static MobSystem lastInstance;

    public MobSystem(JavaPlugin plugin) {
        super(new MobFileManager(plugin, ""), BukkitManager.SaveType.SINGEL);
        this.arenaManager = new MobArenaManager(plugin);
        this.respawnManager = MobRespawnManager.initialize(this, plugin);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArenaCommandExecutor(this, this.arenaManager, this.respawnManager, plugin);
        lastInstance = this;
    }

    @Override
    public void handleCardinalEvent(AincradEvent event) throws Exception {
        Player player;
        if (event instanceof AincradRequestMobFromEntity) {
            this.handleRequestMobFromEntity((AincradRequestMobFromEntity)event);
        } else if (event instanceof AincradSpawnMobEvent) {
            this.handleSpawnMob((AincradSpawnMobEvent)event);
        } else if (event instanceof AincradMobExistEvent) {
            ((AincradMobExistEvent)event).setExist(this.contains(((AincradMobExistEvent)event).getName()));
        } else if (event instanceof AincradBackCommandEvent && this.arenaManager.arenas.containsKey((Object)(player = ((AincradBackCommandEvent)event).getPlayer()))) {
            player.teleport(this.arenaManager.arenas.get((Object)player).getArea().getCenter());
        }
    }

    private void handleSpawnMob(AincradSpawnMobEvent spawnMobEvent) {
        this.respawnManager.spawnTmpMob(spawnMobEvent.getMob(), spawnMobEvent.getLocation());
    }

    private void handleRequestMobFromEntity(AincradRequestMobFromEntity requestMobFromEntity) {
        requestMobFromEntity.setMob(this.respawnManager.getMobFromEntity(requestMobFromEntity.getEntity()));
    }

    public void reset() {
        this.arenaManager.reset();
        this.respawnManager.reset();
    }

    @Override
    public void reload() {
        super.reload();
        this.respawnManager.reload();
    }

    @Override
    public MobEquipment getItemFromName(String name) {
        return (MobEquipment)super.getItemFromName(name);
    }
}

