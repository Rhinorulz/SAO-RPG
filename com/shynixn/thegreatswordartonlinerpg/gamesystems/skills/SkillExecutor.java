/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.Vector
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.skills;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillTmp;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Launch;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Teleport;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Direction;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.InventoryType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.SkillType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.SlotType;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradRequestInventoryTypeEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradResetInventoryEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import libraries.com.darkblade12.particleeffects.ParticleEffect;
import libraries.com.shynixn.utilities.BukkitShyUtilities;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public final class SkillExecutor {
    private static SkillExecutor instance;
    private JavaPlugin plugin;
    private HashMap<LivingEntity, ArrayList<SkillTmp>> activatedskills = new HashMap();
    private HashMap<LivingEntity, ArrayList<Skill>> cooldowns = new HashMap();
    private HashMap<LivingEntity, ItemStack[]> originEquipment = new HashMap();
    private HashMap<LivingEntity, ItemStack[]> actualEquipment = new HashMap();
    private List<SkillHandler> skillHandlers = new ArrayList<SkillHandler>();

    private SkillExecutor(JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        this.plugin = plugin;
    }

    public static SkillExecutor getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new SkillExecutor(plugin);
        }
        return instance;
    }

    public void registerSkillHandler(SkillHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("SkillHandler cannot be null!");
        }
        this.skillHandlers.add(handler);
    }

    public void executeSkill(LivingEntity entity, Skill skill) {
        if (skill == null) {
            throw new IllegalArgumentException("Skill cannot be null!");
        }
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        this.setUpResources(entity);
        this.executeSkill1(entity, skill, this.skillHandlers.toArray(new SkillHandler[this.skillHandlers.size()]));
    }

    public void executeSkill(LivingEntity entity, Skill skill, SkillHandler handler) {
        if (skill == null) {
            throw new IllegalArgumentException("Skill cannot be null!");
        }
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        this.setUpResources(entity);
        this.executeSkill1(entity, skill, new SkillHandler[]{handler});
    }

    public boolean isUsingSkills(LivingEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        return this.actualEquipment.containsKey((Object)entity);
    }

    public void cancelSkill(LivingEntity entity, Skill skill) {
        if (skill == null) {
            throw new IllegalArgumentException("Skill cannot be null!");
        }
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        this.setUpResources(entity);
        for (SkillTmp activated : this.activatedskills.get((Object)entity)) {
            if (!skill.equals(activated.getSkill())) continue;
            this.executeSkill5(entity, activated, this.skillHandlers.toArray(new SkillHandler[this.skillHandlers.size()]));
        }
    }

    public void cancelAllSkills(LivingEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        this.setUpResources(entity);
        SkillTmp[] arrskillTmp = this.activatedskills.get((Object)entity).toArray(new SkillTmp[this.activatedskills.size()]);
        int n = arrskillTmp.length;
        int n2 = 0;
        while (n2 < n) {
            SkillTmp activated = arrskillTmp[n2];
            this.executeSkill5(entity, activated, this.skillHandlers.toArray(new SkillHandler[this.skillHandlers.size()]));
            ++n2;
        }
    }

    public List<Skill> getExecutingSkills(LivingEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        this.setUpResources(entity);
        ArrayList<Skill> skills = new ArrayList<Skill>();
        for (SkillTmp skillActivated : this.activatedskills.get((Object)entity)) {
            skills.add(skillActivated.getSkill().clone());
        }
        return skills;
    }

    public void sendOutHitEffects(LivingEntity entity, Skill skill) {
        if (skill == null) {
            throw new IllegalArgumentException("Skill cannot be null!");
        }
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        SkillEffectHandler.sendOutSoundEffects(entity, skill, -1, skill.getHitSounds(), true);
        SkillEffectHandler.sendOutParticleEffects(entity, skill, -1, skill.getHitParticles(), true);
        SkillEffectHandler.sendOutPotionEffects(entity, skill, -1, skill.getHitOwnEffects(), true);
    }

    public void sendOutHitEnemyEffects(LivingEntity entity, Skill skill) {
        if (skill == null) {
            throw new IllegalArgumentException("Skill cannot be null!");
        }
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        SkillEffectHandler.sendOutPotionEffects(entity, skill, -1, skill.getHitEnemyEffects(), true);
    }

    private void executeSkill1(LivingEntity entity, Skill skill, SkillHandler[] handlers) {
        if (!skill.isEnabled()) {
            SkillHandler[] arrskillHandler = handlers;
            int n = arrskillHandler.length;
            int n2 = 0;
            while (n2 < n) {
                SkillHandler s = arrskillHandler[n2];
                s.skillDisabledEvent(entity, skill);
                ++n2;
            }
        } else if (this.isInCooldownScheduler(entity, skill)) {
            SkillHandler[] arrskillHandler = handlers;
            int n = arrskillHandler.length;
            int n3 = 0;
            while (n3 < n) {
                SkillHandler s = arrskillHandler[n3];
                s.skillCooldownEvent(entity, skill);
                ++n3;
            }
        } else if (!this.isSkillActivated(entity, skill)) {
            SkillHandler[] arrskillHandler = handlers;
            int n = arrskillHandler.length;
            int n4 = 0;
            while (n4 < n) {
                SkillHandler s = arrskillHandler[n4];
                s.skillLoadEvent(entity, skill);
                ++n4;
            }
            SkillTmp skillActivated = new SkillTmp(skill);
            this.activatedskills.get((Object)entity).add(skillActivated);
            this.executeSkill2(entity, skillActivated, handlers);
        }
    }

    private void executeSkill2(final LivingEntity entity, final SkillTmp skill, final SkillHandler[] handlers) {
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (!(entity.isDead() || entity instanceof Player && !((Player)entity).isOnline())) {
                    SkillExecutor.this.executeSkill3(entity, skill, handlers);
                }
            }
        }, (long)skill.getSkill().getLoadingTime() * 20);
    }

    private void executeSkill3(LivingEntity entity, SkillTmp skill, SkillHandler[] handlers) {
        SkillHandler s;
        if (entity instanceof Player) {
            Cardinal.call().notifyStorageSystem(new AincradResetInventoryEvent((Player)entity));
        }
        SkillHandler[] arrskillHandler = handlers;
        int n = arrskillHandler.length;
        int n2 = 0;
        while (n2 < n) {
            s = arrskillHandler[n2];
            s.skillActivatedEvent(entity, skill.getSkill());
            ++n2;
        }
        if (this.storeOriginItemStack(entity, skill.getSkill())) {
            this.executeSkill4Ticker(entity, skill, handlers);
            this.executeSkill4BackgroundDelay(entity, skill, handlers);
        } else {
            arrskillHandler = handlers;
            n = arrskillHandler.length;
            n2 = 0;
            while (n2 < n) {
                s = arrskillHandler[n2];
                s.skillWrongTypeEvent(entity, skill.getSkill());
                ++n2;
            }
            this.executeSkill5(entity, skill, handlers);
        }
    }

    private void executeSkill4BackgroundDelay(final LivingEntity entity, final SkillTmp skill, final SkillHandler[] handlers) {
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (SkillExecutor.this.activatedskills.containsKey((Object)entity)) {
                    SkillExecutor.this.executeSkill5(entity, skill, handlers);
                }
            }
        }, (long)skill.getSkill().getExecutingTime() * 20);
    }

    private void executeSkill4Ticker(final LivingEntity entity, final SkillTmp skill, final SkillHandler[] handlers) {
        skill.setschedulerId(this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                if (entity.isDead() || entity instanceof Player && !((Player)entity).isOnline()) {
                    SkillExecutor.this.executeSkill5(entity, skill, handlers);
                } else if (SkillExecutor.this.activatedskills.containsKey((Object)entity)) {
                    SkillExecutor.this.handleSkillEffects(entity, skill, handlers);
                } else {
                    BukkitUtilities.u().sendColoredConsoleMessage("SCHEDULER RUNNING PLUGIN GENERATES LAG", ChatColor.RED, Cardinal.PREFIX_CONSOLE);
                    BukkitUtilities.u().sendColoredConsoleMessage("Report this bug immediately to Shynixn!!!", ChatColor.RED, Cardinal.PREFIX_CONSOLE);
                }
            }
        }, 0, 20));
    }

    private void handleSkillEffects(LivingEntity entity, SkillTmp skill, SkillHandler[] handlers) {
        AincradRequestInventoryTypeEvent inventoryTypeEvent = null;
        if (entity instanceof Player) {
            inventoryTypeEvent = new AincradRequestInventoryTypeEvent((Player)entity);
            Cardinal.call().notifyStorageSystem(inventoryTypeEvent);
        }
        SkillEffectHandler.sendOutPotionEffects(entity, skill.getSkill(), skill.getRunningTime(), skill.getSkill().getEffects(), false);
        SkillEffectHandler.sendOutLaunchEffects(entity, skill.getSkill(), skill.getRunningTime(), skill.getSkill().getLaunches());
        SkillEffectHandler.sendOutParticleEffects(entity, skill.getSkill(), skill.getRunningTime(), skill.getSkill().getParticles(), false);
        SkillEffectHandler.sendOutSoundEffects(entity, skill.getSkill(), skill.getRunningTime(), skill.getSkill().getSounds(), false);
        SkillEffectHandler.sendOutTeleportEffects(entity, skill.getSkill(), skill.getRunningTime(), skill.getSkill().getTeleports(), false);
        if (!(this.originEquipment.get((Object)entity) == null || entity instanceof Player && inventoryTypeEvent.getInventoryType() != InventoryType.STANDARD && inventoryTypeEvent.getInventoryType() != InventoryType.OFFLINE)) {
            SkillEffectHandler.sendOutEnchantmentEffects(this.actualEquipment.get((Object)entity), skill.getSkill(), skill.getSkill().getEnchantments());
        }
        SkillHandler[] arrskillHandler = handlers;
        int n = arrskillHandler.length;
        int n2 = 0;
        while (n2 < n) {
            SkillHandler s = arrskillHandler[n2];
            s.skillEffectEvent(entity, skill.getSkill(), skill.getSkill().getExecutingTime() - skill.getRunningTime());
            ++n2;
        }
        skill.update();
    }

    private void executeSkill5(LivingEntity entity, SkillTmp skill, SkillHandler[] handlers) {
        if (skill == null) {
            return;
        }
        skill.disabled();
        this.disableSkillPlayer(entity, skill);
        this.coolDownSkillScheduler(entity, skill.getSkill());
        this.cancelSkillScheduler(skill);
        this.restoreOriginItemStack(entity);
        this.activatedskills.get((Object)entity).remove(skill);
        SkillHandler[] arrskillHandler = handlers;
        int n = arrskillHandler.length;
        int n2 = 0;
        while (n2 < n) {
            SkillHandler s = arrskillHandler[n2];
            s.skillDeactivatedEvent(entity, skill.getSkill());
            ++n2;
        }
        if (this.activatedskills.get((Object)entity).size() == 0) {
            this.activatedskills.remove((Object)entity);
            this.originEquipment.remove((Object)entity);
            this.actualEquipment.remove((Object)entity);
        }
    }

    private void cancelSkillScheduler(SkillTmp skill) {
        this.plugin.getServer().getScheduler().cancelTask(skill.getschedulerId());
        Bukkit.getServer().getScheduler().cancelTask(skill.getschedulerId());
    }

    private void disableSkillPlayer(LivingEntity entity, SkillTmp skill) {
        if (entity instanceof Player) {
            Cardinal.call().notifyStorageSystem(new AincradResetInventoryEvent((Player)entity));
        }
    }

    private void coolDownSkillScheduler(final LivingEntity entity, final Skill skill) {
        this.cooldowns.get((Object)entity).add(skill);
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                ((ArrayList)SkillExecutor.this.cooldowns.get((Object)entity)).remove(skill);
            }
        }, (long)skill.getCoolDownTime() * 20);
    }

    private void restoreOriginItemStack(LivingEntity entity) {
        try {
            if (this.originEquipment.containsKey((Object)entity)) {
                if (entity instanceof Player) {
                    ((Player)entity).getInventory().setItem(0, this.originEquipment.get((Object)entity)[0]);
                    ((Player)entity).getInventory().setBoots(this.originEquipment.get((Object)entity)[1]);
                    ((Player)entity).getInventory().setLeggings(this.originEquipment.get((Object)entity)[2]);
                    ((Player)entity).getInventory().setChestplate(this.originEquipment.get((Object)entity)[3]);
                    ((Player)entity).getInventory().setHelmet(this.originEquipment.get((Object)entity)[4]);
                    ((Player)entity).updateInventory();
                } else {
                    entity.getEquipment().setItemInHand(this.originEquipment.get((Object)entity)[0]);
                    entity.getEquipment().setBoots(this.originEquipment.get((Object)entity)[1]);
                    entity.getEquipment().setLeggings(this.originEquipment.get((Object)entity)[2]);
                    entity.getEquipment().setChestplate(this.originEquipment.get((Object)entity)[3]);
                    entity.getEquipment().setHelmet(this.originEquipment.get((Object)entity)[4]);
                }
                this.actualEquipment.put(entity, this.getImportantItemStacks(entity));
            }
        }
        catch (Exception var2_2) {
            // empty catch block
        }
    }

    private boolean storeOriginItemStack(LivingEntity entity, Skill skill) {
        ItemStack itemStack = this.getItemStackZeroFromEnity(entity);
        if (itemStack != null && this.isWrongSkillType(entity, skill, itemStack)) {
            return false;
        }
        this.actualEquipment.put(entity, this.getImportantItemStacks(entity));
        if (!this.originEquipment.containsKey((Object)entity)) {
            this.originEquipment.put(entity, this.cloneItemStacks(this.getImportantItemStacks(entity)));
        }
        return true;
    }

    private ItemStack[] cloneItemStacks(ItemStack[] itemStacks) {
        ItemStack[] itemStacks2 = new ItemStack[itemStacks.length];
        int i = 0;
        while (i < itemStacks.length) {
            if (itemStacks[i] != null) {
                itemStacks2[i] = itemStacks[i].clone();
            }
            ++i;
        }
        return itemStacks2;
    }

    private ItemStack[] getImportantItemStacks(LivingEntity entity) {
        ItemStack[] itemStacks = new ItemStack[5];
        try {
            if (entity instanceof Player) {
                itemStacks[0] = ((Player)entity).getInventory().getItem(0);
                itemStacks[1] = ((Player)entity).getInventory().getBoots();
                itemStacks[2] = ((Player)entity).getInventory().getLeggings();
                itemStacks[3] = ((Player)entity).getInventory().getChestplate();
                itemStacks[4] = ((Player)entity).getInventory().getHelmet();
            } else {
                itemStacks[0] = entity.getEquipment().getItemInHand();
                itemStacks[1] = entity.getEquipment().getBoots();
                itemStacks[2] = entity.getEquipment().getLeggings();
                itemStacks[3] = entity.getEquipment().getChestplate();
                itemStacks[4] = entity.getEquipment().getHelmet();
            }
        }
        catch (Exception var3_3) {
            // empty catch block
        }
        return itemStacks;
    }

    private void setUpResources(LivingEntity entity) {
        if (!this.activatedskills.containsKey((Object)entity)) {
            this.activatedskills.put(entity, new ArrayList());
        }
        if (!this.cooldowns.containsKey((Object)entity)) {
            this.cooldowns.put(entity, new ArrayList());
        }
    }

    private ItemStack getItemStackZeroFromEnity(LivingEntity entity) {
        try {
            if (entity instanceof Player) {
                return ((Player)entity).getInventory().getItem(0);
            }
            return entity.getEquipment().getItemInHand();
        }
        catch (Exception e) {
            return null;
        }
    }

    private boolean isInCooldownScheduler(LivingEntity entity, Skill skill) {
        if (this.cooldowns.containsKey((Object)entity)) {
            for (Skill skill2 : this.cooldowns.get((Object)entity)) {
                if (!skill2.equals(skill)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isWrongSkillType(LivingEntity entity, Skill skill, ItemStack itemStack) {
        if (skill.getType() == SkillType.UNIVERSAL) {
            return false;
        }
        if (skill.getType() == SkillType.AXE) {
            return !BukkitShyUtilities.isAxe(itemStack);
        }
        if (skill.getType() == SkillType.BOW) {
            return !BukkitShyUtilities.isBow(itemStack);
        }
        if (skill.getType() == SkillType.SWORD) {
            return !BukkitShyUtilities.isSword(itemStack);
        }
        return true;
    }

    private boolean isSkillActivated(LivingEntity entity, Skill skill) {
        for (SkillTmp activatedskill : this.activatedskills.get((Object)entity)) {
            if (!activatedskill.getSkill().equals(skill)) continue;
            return true;
        }
        return false;
    }

    public List<Entity> getEntitiesUsingSkills() {
        return Arrays.asList(this.activatedskills.keySet().toArray((T[])new Entity[this.activatedskills.size()]));
    }

    private static class SkillEffectHandler {
        private SkillEffectHandler() {
        }

        private static void sendOutSoundEffects(LivingEntity entity, Skill skill, int runningTime, com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound[] effects, boolean isHitEffect) {
            com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound[] arrsound = effects;
            int n = arrsound.length;
            int n2 = 0;
            while (n2 < n) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound effect = arrsound[n2];
                if (effect.getDelay() == runningTime || isHitEffect) {
                    for (Player p : entity.getWorld().getPlayers()) {
                        p.playSound(entity.getLocation(), effect.getSound(), (float)effect.getVolume(), (float)effect.getPitch());
                    }
                }
                ++n2;
            }
        }

        private static void sendOutParticleEffects(LivingEntity entity, Skill skill, int runningTime, com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect[] effects, boolean isHitEffect) {
            com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect[] arrparticleEffect = effects;
            int n = arrparticleEffect.length;
            int n2 = 0;
            while (n2 < n) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect effect = arrparticleEffect[n2];
                try {
                    if (effect.getDelay() == runningTime || isHitEffect) {
                        effect.getEffect().display(effect.getSizeX(), effect.getSizeY(), effect.getSizeZ(), effect.getSpeed(), effect.getCount(), entity.getLocation(), entity.getWorld().getPlayers());
                    }
                }
                catch (Exception var9_9) {
                    // empty catch block
                }
                ++n2;
            }
        }

        private static void sendOutTeleportEffects(LivingEntity entity, Skill skill, int runningTime, Teleport[] effects, boolean isHitEffect) {
            Teleport[] arrteleport = effects;
            int n = arrteleport.length;
            int n2 = 0;
            while (n2 < n) {
                Teleport effect = arrteleport[n2];
                if (effect.getDelay() == runningTime) {
                    String direction = BukkitUtilities.u().getCardinalDirection((Entity)entity);
                    if (effect.getDirection().equals((Object)Direction.UP)) {
                        entity.teleport(entity.getLocation().add(0.0, (double)effect.getBlockAmount(), 0.0));
                    } else if (effect.getDirection().equals((Object)Direction.DOWN)) {
                        entity.teleport(entity.getLocation().add(0.0, (double)(-1 * effect.getBlockAmount()), 0.0));
                    } else if (direction.equals("N")) {
                        SkillEffectHandler.teleportLivingEntityNorth(entity, skill, effect);
                    } else if (direction.equals("NE")) {
                        SkillEffectHandler.teleportLivingEntityNorthEast(entity, skill, effect);
                    } else if (direction.equals("E")) {
                        SkillEffectHandler.teleportLivingEntityEast(entity, skill, effect);
                    } else if (direction.equals("SE")) {
                        SkillEffectHandler.teleportLivingEntitySouthEast(entity, skill, effect);
                    } else if (direction.equals("S")) {
                        SkillEffectHandler.teleportLivingEntitySouth(entity, effect);
                    } else if (direction.equals("SW")) {
                        SkillEffectHandler.teleportLivingEntitySouthWest(entity, effect);
                    } else if (direction.equals("W")) {
                        SkillEffectHandler.teleportLivingEntityWest(entity, effect);
                    } else if (direction.equals("NW")) {
                        SkillEffectHandler.teleportLivingEntityNorthWest(entity, effect);
                    }
                }
                ++n2;
            }
        }

        private static void sendOutLaunchEffects(LivingEntity entity, Skill skill, int runningTime, Launch[] effects) {
            Launch[] arrlaunch = effects;
            int n = arrlaunch.length;
            int n2 = 0;
            while (n2 < n) {
                Launch effect = arrlaunch[n2];
                if (effect.getDelay() == runningTime) {
                    String direction = BukkitUtilities.u().getCardinalDirection((Entity)entity);
                    if (effect.getDirection().equals((Object)Direction.UP)) {
                        entity.setVelocity(entity.getVelocity().add(new Vector(0.0, effect.getAmplifier(), 0.0)));
                    }
                    if (effect.getDirection().equals((Object)Direction.DOWN)) {
                        entity.setVelocity(entity.getVelocity().add(new Vector(0.0, -1.0 * effect.getAmplifier(), 0.0)));
                    } else if (direction.equals("N")) {
                        SkillEffectHandler.launchLivingEntityNorth(entity, effect);
                    } else if (direction.equals("NE")) {
                        SkillEffectHandler.launchLivingEntityNorthEast(entity, effect);
                    } else if (direction.equals("E")) {
                        SkillEffectHandler.launchLivingEntityEast(entity, effect);
                    } else if (direction.equals("SE")) {
                        SkillEffectHandler.launchLivingEntitySouthEast(entity, effect);
                    } else if (direction.equals("S")) {
                        SkillEffectHandler.launchLivingEntitySouth(entity, effect);
                    } else if (direction.equals("SW")) {
                        SkillEffectHandler.launchLivingEntitySouthWest(entity, effect);
                    } else if (direction.equals("W")) {
                        SkillEffectHandler.launchLivingEntityWest(entity, effect);
                    } else if (direction.equals("NW")) {
                        SkillEffectHandler.launchLivingEntityNorthWest(entity, effect);
                    }
                }
                ++n2;
            }
        }

        private static void sendOutPotionEffects(LivingEntity entity, Skill skill, int runningTime, com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect[] effects, boolean isHitEffect) {
            boolean wasound = false;
            com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect[] arrpotionEffect = effects;
            int n = arrpotionEffect.length;
            int n2 = 0;
            while (n2 < n) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect effect = arrpotionEffect[n2];
                if (effect.getDelay() == runningTime || isHitEffect) {
                    wasound = false;
                    for (PotionEffect e : entity.getActivePotionEffects()) {
                        if (!e.getType().getName().equals(effect.getEffect().getType().getName())) continue;
                        wasound = true;
                        if (e.getAmplifier() >= effect.getEffect().getAmplifier() && e.getDuration() >= effect.getEffect().getDuration()) continue;
                        entity.removePotionEffect(effect.getEffect().getType());
                        entity.addPotionEffect(effect.getEffect());
                    }
                    if (!wasound) {
                        entity.addPotionEffect(effect.getEffect());
                    }
                }
                ++n2;
            }
        }

        private static void sendOutEnchantmentEffects(ItemStack[] equipment, Skill skill, com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment[] effects) {
            boolean wasound = false;
            com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment[] arrenchantment = effects;
            int n = arrenchantment.length;
            int n2 = 0;
            while (n2 < n) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment e = arrenchantment[n2];
                try {
                    wasound = false;
                    ItemStack item = e.getPosition() == SlotType.WEAPON ? equipment[0] : (e.getPosition() == SlotType.BOOTS ? equipment[1] : (e.getPosition() == SlotType.LEGGINGS ? equipment[2] : (e.getPosition() == SlotType.CHESTPLATE ? equipment[3] : equipment[4])));
                    for (Enchantment ienchantment : item.getEnchantments().keySet()) {
                        if (!ienchantment.getName().equals(e.getEnchantment().getName())) continue;
                        wasound = true;
                        if (item.getEnchantmentLevel(ienchantment) >= e.getLevel()) continue;
                        item.removeEnchantment(ienchantment);
                        item.addUnsafeEnchantment(e.getEnchantment(), e.getLevel());
                    }
                    if (!wasound) {
                        item.addUnsafeEnchantment(e.getEnchantment(), e.getLevel());
                    }
                }
                catch (Exception item) {
                    // empty catch block
                }
                ++n2;
            }
        }

        private static void teleportLivingEntityNorth(LivingEntity entity, Skill skill, Teleport effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.teleport(entity.getLocation().add(0.0, 0.0, (double)effect.getBlockAmount()));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.teleport(entity.getLocation().add(0.0, 0.0, (double)(-1 * effect.getBlockAmount())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, 0.0));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, 0.0));
            }
        }

        private static void teleportLivingEntityNorthEast(LivingEntity entity, Skill skill, Teleport effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, (double)effect.getBlockAmount()));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, (double)(-1 * effect.getBlockAmount())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, (double)(-1 * effect.getBlockAmount())));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, (double)effect.getBlockAmount()));
            }
        }

        private static void teleportLivingEntityEast(LivingEntity entity, Skill skill, Teleport effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, 0.0));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, 0.0));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.teleport(entity.getLocation().add(0.0, 0.0, (double)(-1 * effect.getBlockAmount())));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.teleport(entity.getLocation().add(0.0, 0.0, (double)effect.getBlockAmount()));
            }
        }

        private static void teleportLivingEntitySouthEast(LivingEntity entity, Skill skill, Teleport effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, (double)effect.getBlockAmount()));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, (double)(-1 * effect.getBlockAmount())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.teleport(entity.getLocation().add((double)(1 * effect.getBlockAmount()), 0.0, (double)(-1 * effect.getBlockAmount())));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, (double)effect.getBlockAmount()));
            }
        }

        private static void teleportLivingEntityNorthWest(LivingEntity entity, Teleport effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, (double)effect.getBlockAmount()));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, (double)(-1 * effect.getBlockAmount())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, (double)effect.getBlockAmount()));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, (double)(-1 * effect.getBlockAmount())));
            }
        }

        private static void teleportLivingEntityWest(LivingEntity entity, Teleport effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, 0.0));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, 0.0));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.teleport(entity.getLocation().add(0.0, 0.0, (double)effect.getBlockAmount()));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.teleport(entity.getLocation().add(0.0, 0.0, (double)(-1 * effect.getBlockAmount())));
            }
        }

        private static void teleportLivingEntitySouthWest(LivingEntity entity, Teleport effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, (double)(-1 * effect.getBlockAmount())));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, (double)effect.getBlockAmount()));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, (double)effect.getBlockAmount()));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, (double)(-1 * effect.getBlockAmount())));
            }
        }

        private static void teleportLivingEntitySouth(LivingEntity entity, Teleport effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.teleport(entity.getLocation().add(0.0, 0.0, (double)(-1 * effect.getBlockAmount())));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.teleport(entity.getLocation().add(0.0, 0.0, (double)effect.getBlockAmount()));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.teleport(entity.getLocation().add((double)effect.getBlockAmount(), 0.0, 0.0));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.teleport(entity.getLocation().add((double)(-1 * effect.getBlockAmount()), 0.0, 0.0));
            }
        }

        private static void launchLivingEntityNorth(LivingEntity entity, Launch effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, -1.0 * effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, 0.0)));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, 0.0)));
            }
        }

        private static void launchLivingEntityNorthEast(LivingEntity entity, Launch effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, effect.getAmplifier())));
            }
        }

        private static void launchLivingEntityEast(LivingEntity entity, Launch effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, 0.0)));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, 0.0)));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, -1.0 * effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, effect.getAmplifier())));
            }
        }

        private static void launchLivingEntitySouthEast(LivingEntity entity, Launch effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, effect.getAmplifier())));
            }
        }

        private static void launchLivingEntitySouth(LivingEntity entity, Launch effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, -1.0 * effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, 0.0)));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, 0.0)));
            }
        }

        private static void launchLivingEntitySouthWest(LivingEntity entity, Launch effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
            }
        }

        private static void launchLivingEntityWest(LivingEntity entity, Launch effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, 0.0)));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, 0.0)));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0, 0.0, -1.0 * effect.getAmplifier())));
            }
        }

        private static void launchLivingEntityNorthWest(LivingEntity entity, Launch effect) {
            if (effect.getDirection().equals((Object)Direction.LEFT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.RIGHT)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.FORWARD)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(-1.0 * effect.getAmplifier(), 0.0, effect.getAmplifier())));
            } else if (effect.getDirection().equals((Object)Direction.BACK)) {
                entity.setVelocity(entity.getVelocity().add(new Vector(effect.getAmplifier(), 0.0, -1.0 * effect.getAmplifier())));
            }
        }
    }

    public static interface SkillHandler {
        public void skillLoadEvent(LivingEntity var1, Skill var2);

        public void skillActivatedEvent(LivingEntity var1, Skill var2);

        public void skillDeactivatedEvent(LivingEntity var1, Skill var2);

        public void skillCooldownEvent(LivingEntity var1, Skill var2);

        public void skillDisabledEvent(LivingEntity var1, Skill var2);

        public void skillWrongTypeEvent(LivingEntity var1, Skill var2);

        public void skillEffectEvent(LivingEntity var1, Skill var2, int var3);
    }

}

