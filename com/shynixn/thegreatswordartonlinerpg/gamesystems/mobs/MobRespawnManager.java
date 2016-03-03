/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnCommandExecutor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnFileManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnListener;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnpoint;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.skill.AincradRequestSkillEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitManager;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class MobRespawnManager
extends BukkitManager {
    private static MobRespawnManager instance;
    private JavaPlugin plugin;
    private MobSystem mobManager;
    private List<RunningSpawn> runningRespawnPoints = new ArrayList<RunningSpawn>();
    private List<Mob> tmpMobs = new ArrayList<Mob>();
    private boolean isMobsRunning = false;
    private boolean isSkillsRunning = false;

    protected static MobRespawnManager initialize(MobSystem mobManager, JavaPlugin plugin) {
        if (instance == null) {
            instance = new MobRespawnManager(mobManager, plugin);
        }
        return instance;
    }

    public static MobRespawnManager getInstance() {
        return instance;
    }

    protected Mob getMobFromEntity(LivingEntity entity) {
        for (Mob mob : this.getMobs()) {
            if (!mob.isSameEntity(entity)) continue;
            return mob;
        }
        return null;
    }

    private MobRespawnManager(MobSystem mobManager, JavaPlugin plugin) {
        super(new MobRespawnFileManager(plugin), BukkitManager.SaveType.SINGEL);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnCommandExecutor(this, mobManager, plugin);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnListener(this, plugin);
        this.plugin = plugin;
        this.mobManager = mobManager;
        this.startMainScheduler();
        this.skillScheduler();
    }

    private void skillScheduler() {
        if (!this.isSkillsRunning) {
            this.isSkillsRunning = true;
            this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    for (RunningSpawn runningSpawn : MobRespawnManager.this.runningRespawnPoints) {
                        for (Mob mob : runningSpawn.mobs) {
                            if (mob.isDead()) continue;
                            MobRespawnManager.this.executeSkill(mob);
                        }
                    }
                    for (Mob mob : MobRespawnManager.this.tmpMobs) {
                        if (mob.isDead()) continue;
                        MobRespawnManager.this.executeSkill(mob);
                    }
                }
            }, 0, 20);
        }
    }

    private void executeSkill(Mob mob) {
        if (mob.getEntity() != null && !mob.isDead()) {
            for (String s : mob.getEquipmentCopy().getSkills()) {
                AincradRequestSkillEvent requestSkillEvent = new AincradRequestSkillEvent(s);
                Cardinal.call().notifySkillSystem(requestSkillEvent);
                if (requestSkillEvent.getSkill() == null) {
                    Cardinal.call().sendException(new CardinalException("Mob " + mob.getName() + " tries to use an unknown skill", "Useless resources", "Remove skill from mob", Priority.MEDIUM));
                    continue;
                }
                if (mob.getHealth() > mob.getEquipmentCopy().getSkillsAndHealth().get(s)) continue;
                Cardinal.getSkillExecutor().executeSkill(mob.getEntity(), requestSkillEvent.getSkill());
            }
        }
    }

    private void startMainScheduler() {
        if (!this.isMobsRunning) {
            this.isMobsRunning = true;
            this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    for (RunningSpawn runningSpawn : MobRespawnManager.this.runningRespawnPoints) {
                        if (MobRespawnManager.this.isPlayerInRadius(runningSpawn.respawnpoint)) {
                            if (!runningSpawn.firstSpawn) {
                                runningSpawn.firstSpawn = true;
                                MobRespawnManager.this.spawnAllMobs(runningSpawn, 0);
                            }
                            if (MobRespawnManager.this.getAliveMobs(runningSpawn.respawnpoint) <= runningSpawn.respawnpoint.getAmount() / 2) {
                                --runningSpawn.respawnCounter;
                            }
                            if (runningSpawn.respawnCounter > 0) continue;
                            MobRespawnManager.this.spawnAllMobs(runningSpawn, MobRespawnManager.this.despawnAllMobs(runningSpawn));
                            runningSpawn.respawnCounter = runningSpawn.respawnpoint.getMaxRespawnDelay();
                            continue;
                        }
                        if (runningSpawn.isOnDespawnList) {
                            --runningSpawn.blackListCounter;
                            if (runningSpawn.blackListCounter > 0) continue;
                            MobRespawnManager.this.despawnAllMobs(runningSpawn);
                            runningSpawn.firstSpawn = false;
                            runningSpawn.isOnDespawnList = false;
                            continue;
                        }
                        if (!runningSpawn.firstSpawn) continue;
                        runningSpawn.isOnDespawnList = true;
                        runningSpawn.blackListCounter = runningSpawn.respawnpoint.getMaxDespawnDelay();
                    }
                }
            }, 0, 20);
        }
    }

    private int despawnAllMobs(RunningSpawn runningSpawn) {
        int i = 0;
        for (Mob mob : runningSpawn.mobs) {
            this.despawnMob(mob, i);
            ++i;
        }
        return i;
    }

    private void spawnAllMobs(RunningSpawn runningSpawn, int i) {
        for (Mob mob : runningSpawn.mobs) {
            this.spawnMob(mob, runningSpawn.respawnpoint, i);
            ++i;
        }
    }

    private void despawnMob(final Mob mob, int delay) {
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                mob.kill();
            }
        }, (long)delay * 1);
    }

    private void spawnMob(final Mob mob, final MobRespawnpoint mobRespawnpoint, int delay) {
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                Location location = MobRespawnManager.this.getRandomLocationInRadius(mobRespawnpoint.getLocation(), mobRespawnpoint.getXradius(), mobRespawnpoint.getYradius(), mobRespawnpoint.getZradius());
                while (!MobRespawnManager.this.isEnoughSpace(location)) {
                    location = location.add(0.0, 1.0, 0.0);
                }
                mob.respawn(location);
            }
        }, (long)delay * 1);
    }

    private boolean isEnoughSpace(Location location) {
        if (location.getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() + 1.0, location.getY(), location.getZ()).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() - 1.0, location.getY(), location.getZ()).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() + 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() - 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() + 1.0, location.getY(), location.getZ() + 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() - 1.0, location.getY(), location.getZ() - 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() + 1.0, location.getY(), location.getZ() - 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() - 1.0, location.getY(), location.getZ() + 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX(), location.getY() + 1.0, location.getZ()).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() + 1.0, location.getY() + 1.0, location.getZ()).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() - 1.0, location.getY() + 1.0, location.getZ()).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX(), location.getY() + 1.0, location.getZ() + 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX(), location.getY() + 1.0, location.getZ() - 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() + 1.0, location.getY() + 1.0, location.getZ() + 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() - 1.0, location.getY() + 1.0, location.getZ() - 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() + 1.0, location.getY() + 1.0, location.getZ() - 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() - 1.0, location.getY() + 1.0, location.getZ() + 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX(), location.getY() + 2.0, location.getZ()).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() + 1.0, location.getY() + 2.0, location.getZ()).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() - 1.0, location.getY() + 2.0, location.getZ()).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX(), location.getY() + 2.0, location.getZ() + 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX(), location.getY() + 2.0, location.getZ() - 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() + 1.0, location.getY() + 2.0, location.getZ() + 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() - 1.0, location.getY() + 2.0, location.getZ() - 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() + 1.0, location.getY() + 2.0, location.getZ() - 1.0).getBlock().getType() == Material.AIR && new Location(location.getWorld(), location.getX() - 1.0, location.getY() + 2.0, location.getZ() + 1.0).getBlock().getType() == Material.AIR) {
            return true;
        }
        return false;
    }

    private boolean isPlayerInRadius(MobRespawnpoint respawnpoint) {
        for (Entity entity : respawnpoint.getLocation().getWorld().getEntities()) {
            if (!(entity instanceof Player) || respawnpoint.getLocation().distance(entity.getLocation()) > (double)respawnpoint.getDetectionradius()) continue;
            return true;
        }
        return false;
    }

    public void spawnTmpMob(Mob mob, Location location) {
        this.tmpMobs.add(mob);
        mob.respawn(location);
    }

    protected void updateMobRespawnPoint(MobRespawnpoint respawnpoint) {
        this.clearRespawnPoint(respawnpoint);
        RunningSpawn runningSpawn = new RunningSpawn(respawnpoint);
        this.runningRespawnPoints.add(runningSpawn);
        int i = 0;
        while (i < respawnpoint.getAmount()) {
            if (this.mobManager.getItemFromName(respawnpoint.getMobName()) != null) {
                Mob mob = new Mob(this.mobManager.getItemFromName(respawnpoint.getMobName()));
                runningSpawn.mobs.add(mob);
            }
            ++i;
        }
    }

    protected void clearRespawnPoint(MobRespawnpoint respawnpoint) {
        RunningSpawn copy = null;
        for (RunningSpawn runningSpawn : this.runningRespawnPoints) {
            if (!runningSpawn.respawnpoint.equals(respawnpoint)) continue;
            for (Mob mob : runningSpawn.mobs) {
                mob.kill();
            }
            copy = runningSpawn;
            break;
        }
        if (copy != null) {
            this.runningRespawnPoints.remove(copy);
        }
    }

    public List<Mob> getMobs() {
        ArrayList<Mob> mobs = new ArrayList<Mob>();
        for (RunningSpawn runningSpawn : this.runningRespawnPoints) {
            for (Mob mob : runningSpawn.mobs) {
                mobs.add(mob);
            }
        }
        for (Mob mob : this.tmpMobs) {
            mobs.add(mob);
        }
        return mobs;
    }

    private RunningSpawn getRunningSpawnFromRespawnPoint(MobRespawnpoint respawnpoint) {
        for (RunningSpawn runningSpawn : this.runningRespawnPoints) {
            if (!runningSpawn.respawnpoint.equals(respawnpoint)) continue;
            return runningSpawn;
        }
        return null;
    }

    private int getAliveMobs(MobRespawnpoint respawnpoint) {
        int amount = 0;
        if (this.getRunningSpawnFromRespawnPoint(respawnpoint) != null) {
            for (Mob mob : this.getRunningSpawnFromRespawnPoint((MobRespawnpoint)respawnpoint).mobs) {
                if (mob.isDead()) continue;
                ++amount;
            }
        }
        return amount;
    }

    public void reset() {
        try {
            for (RunningSpawn runningSpawn : this.runningRespawnPoints) {
                for (Mob mob : runningSpawn.mobs) {
                    mob.kill();
                }
            }
        }
        catch (Exception runningSpawn) {
            // empty catch block
        }
    }

    @Override
    public void reload() {
        super.reload();
        Cardinal.getLogger().logHeadLine("RESTARTING MOB ENGINE");
        this.restartRespawnPoints();
        this.startMainScheduler();
    }

    public void restartRespawnPoints() {
        this.runningRespawnPoints.clear();
        for (BukkitObject object : this.getItems()) {
            if (!((MobRespawnpoint)object).isEnabled()) continue;
            this.updateMobRespawnPoint((MobRespawnpoint)object);
        }
    }

    @Override
    public MobRespawnpoint getItemFromName(String name) {
        return (MobRespawnpoint)super.getItemFromName(name);
    }

    private Location getRandomLocationInRadius(Location location, int rx, int ry, int rz) {
        Random random = new Random();
        int ax = random.nextInt(rx + 1);
        int ay = random.nextInt(ry + 1);
        int az = random.nextInt(rz + 1);
        if (random.nextInt() % 2 == 0) {
            ax *= -1;
        }
        if (random.nextInt() % 2 == 0) {
            az *= -1;
        }
        return location.add((double)ax, (double)ay, (double)az);
    }

    private static class RunningSpawn {
        public List<Mob> mobs = new ArrayList<Mob>();
        public boolean isOnDespawnList = false;
        public int blackListCounter;
        public int respawnCounter;
        public MobRespawnpoint respawnpoint;
        public boolean firstSpawn = false;

        public RunningSpawn(MobRespawnpoint respawnpoint) {
            this.respawnpoint = respawnpoint;
            this.respawnCounter = respawnpoint.getMaxRespawnDelay();
        }
    }

}

