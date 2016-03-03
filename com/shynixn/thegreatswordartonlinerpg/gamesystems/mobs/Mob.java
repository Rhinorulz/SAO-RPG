/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.EnderDragon
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Giant
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.PigZombie
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.entity.Zombie
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.BannerWingsApi;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.floors.AincradBeatBossEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.MobRegistry;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.SaoMob;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import libraries.com.shynixn.utilities.BukkitChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Mob {
    private LivingEntity entity;
    private MobEquipment equipment;
    private double health;
    private Mob ridingMob;

    public Mob(MobEquipment equipment) {
        this.equipment = equipment.clone();
        this.health = this.equipment.getHealth();
        if (MobSystem.lastInstance.getItemFromName(equipment.getRidingMob()) != null) {
            this.ridingMob = new Mob(MobSystem.lastInstance.getItemFromName(equipment.getRidingMob()));
        }
    }

    public void update(MobEquipment equipment) {
        this.equipment = equipment.clone();
        this.health = this.equipment.getHealth();
        this.showHealthAndName(1);
        this.ridingMob = MobSystem.lastInstance.getItemFromName(equipment.getRidingMob()) != null ? new Mob(MobSystem.lastInstance.getItemFromName(equipment.getRidingMob())) : null;
    }

    public double getDamage() {
        return this.equipment.getDamage();
    }

    public MobEquipment getEquipmentCopy() {
        return this.equipment;
    }

    public void health(double amount) {
        if (this.health > 0.0) {
            this.health += amount;
            this.showHealthAndName(1);
        }
    }

    public boolean damage(double amount) {
        this.health -= amount;
        this.showHealthAndName(1);
        if (this.health <= 0.0) {
            this.showHealthAndName(0);
            if (this.entity instanceof EnderDragon) {
                this.entity.setHealth(0.0);
            }
            if (this.equipment.getBoots() != null && this.isPercent(this.equipment.getBootsDropChance())) {
                this.entity.getLocation().getWorld().dropItemNaturally(this.entity.getLocation(), this.equipment.getBoots());
            }
            if (this.equipment.getLeggings() != null && this.isPercent(this.equipment.getLeggingsDropChance())) {
                this.entity.getLocation().getWorld().dropItemNaturally(this.entity.getLocation(), this.equipment.getLeggings());
            }
            if (this.equipment.getChestPlate() != null && this.isPercent(this.equipment.getChestPlateDropChance())) {
                this.entity.getLocation().getWorld().dropItemNaturally(this.entity.getLocation(), this.equipment.getChestPlate());
            }
            if (this.equipment.getHelmet() != null && this.isPercent(this.equipment.getHelmetDropChance())) {
                this.entity.getLocation().getWorld().dropItemNaturally(this.entity.getLocation(), this.equipment.getHelmet());
            }
            if (this.equipment.getWeapon() != null && this.isPercent(this.equipment.getWeaponDropChance())) {
                this.entity.getLocation().getWorld().dropItemNaturally(this.entity.getLocation(), this.equipment.getWeapon());
            }
            this.sendBeatFloorEvent();
            return true;
        }
        return false;
    }

    private void sendBeatFloorEvent() {
        for (Entity entity : this.entity.getWorld().getEntities()) {
            if (!(entity instanceof Player) || this.entity.getLocation().distance(entity.getLocation()) > 20.0) continue;
            AincradBeatBossEvent event = new AincradBeatBossEvent((Player)entity, this.getEquipmentCopy().getName());
            Cardinal.call().notifyFloorSystem(event);
        }
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public boolean equals(Object arg0) {
        if (arg0 instanceof Mob) {
            return this.entity.equals((Object)((Mob)arg0).getEntity());
        }
        return false;
    }

    private boolean isPercent(double percent) {
        float minX = 0.0f;
        float maxX = 100.0f;
        Random rand = new Random();
        float finalX = rand.nextFloat() * (maxX - minX) + minX;
        if ((double)finalX <= percent) {
            return true;
        }
        return false;
    }

    public boolean isSameEntity(LivingEntity entity) {
        return entity.equals((Object)this.entity);
    }

    public String getName() {
        return this.equipment.getName();
    }

    public boolean isBaby() {
        if (this.entity instanceof Zombie && ((Zombie)this.entity).isBaby()) {
            return true;
        }
        return false;
    }

    public void respawn(Location location) {
        if (this.ridingMob != null) {
            if (this.ridingMob.getEntity() != null) {
                this.ridingMob.getEntity().remove();
            }
            MobRespawnManager.getInstance().spawnTmpMob(this.ridingMob, location);
        }
        if (this.entity != null) {
            this.entity.remove();
        }
        SaoMob saomob = Cardinal.getRegistry().getMobRegistry().createMob(location.getWorld(), this.equipment.getEntityType().name(), this.equipment);
        this.entity = (LivingEntity)saomob.getBukkitEntity();
        saomob.spawn(location);
        this.entity.setRemoveWhenFarAway(false);
        this.health = this.equipment.getHealth();
        this.equip();
        if (!this.equipment.isMoveAble()) {
            this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999999, 120, true));
            this.entity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999999, -120, true));
        }
        this.showHealthAndName(1);
        if (this.ridingMob != null) {
            this.entity.setPassenger((Entity)this.ridingMob.getEntity());
        }
        if (this.equipment.isWings()) {
            try {
                BannerWingsApi.setWings((Entity)this.entity, this.equipment.getWingsName());
                BannerWingsApi.showWings((Entity)this.entity);
            }
            catch (Exception var3_3) {
                // empty catch block
            }
        }
    }

    private void showHealthAndName(int modifier) {
        try {
            if (this.ridingMob != null) {
                this.ridingMob.showHealthAndName(1);
            }
            if (this.equipment.isShowingHealth() && this.equipment.isShowingName()) {
                int greenfieldsamount = (int)(this.health / (this.equipment.getHealth() / 20.0)) + modifier;
                String text = String.valueOf(this.equipment.getDisplayName()) + " ";
                int i = 0;
                while (i < 20) {
                    text = i < greenfieldsamount ? String.valueOf(text) + (Object)((Object)BukkitChatColor.GREEN) + "\u258d" : String.valueOf(text) + (Object)((Object)BukkitChatColor.DARK_RED) + "\u258d";
                    ++i;
                }
                this.entity.setCustomName(text);
                this.entity.setCustomNameVisible(true);
            } else if (this.equipment.isShowingHealth()) {
                int greenfieldsamount = (int)(this.health / (this.equipment.getHealth() / 20.0));
                String text = "";
                int i = 0;
                while (i < 20) {
                    text = i < greenfieldsamount ? String.valueOf(text) + (Object)((Object)BukkitChatColor.GREEN) + "\u258d" : String.valueOf(text) + (Object)((Object)BukkitChatColor.DARK_RED) + "\u258d";
                    ++i;
                }
                this.entity.setCustomName(text);
                this.entity.setCustomNameVisible(true);
            } else if (this.equipment.isShowingName()) {
                this.entity.setCustomName(this.equipment.getDisplayName());
                this.entity.setCustomNameVisible(true);
            } else {
                this.entity.setCustomName("");
                this.entity.setCustomNameVisible(false);
            }
        }
        catch (ArithmeticException greenfieldsamount) {
            // empty catch block
        }
    }

    public Location getLocation() {
        return this.entity.getLocation();
    }

    public void setVelocity(Vector vector) {
        this.entity.setVelocity(vector);
    }

    public void teleport(Location location) {
        this.entity.teleport(location);
    }

    public boolean isDead() {
        if (this.entity == null && this.ridingMob == null) {
            return true;
        }
        if (this.entity == null && this.ridingMob != null && this.ridingMob.isDead()) {
            return true;
        }
        if (this.entity != null && this.ridingMob == null && this.entity.isDead()) {
            return true;
        }
        if (this.entity != null && this.ridingMob != null && this.entity.isDead() && this.ridingMob.isDead()) {
            return true;
        }
        return false;
    }

    public void kill() {
        if (!this.isDead()) {
            if (this.ridingMob != null) {
                this.ridingMob.kill();
            }
            this.entity.remove();
        }
    }

    public double getHealth() {
        return this.health;
    }

    public double getOriginDamage() {
        return this.equipment.getDamage();
    }

    public void equip() {
        if (this.ridingMob != null) {
            this.ridingMob.equip();
        }
        try {
            if (this.entity instanceof Zombie) {
                ((Zombie)this.entity).getEquipment().setArmorContents(new ItemStack[]{this.equipment.getBoots(), this.equipment.getLeggings(), this.equipment.getChestPlate(), this.equipment.getHelmet()});
                ((Zombie)this.entity).getEquipment().setItemInHand(this.equipment.getWeapon());
            } else if (this.entity instanceof Skeleton) {
                ((Skeleton)this.entity).getEquipment().setArmorContents(new ItemStack[]{this.equipment.getBoots(), this.equipment.getLeggings(), this.equipment.getChestPlate(), this.equipment.getHelmet()});
                ((Skeleton)this.entity).getEquipment().setItemInHand(this.equipment.getWeapon());
            }
            if (this.entity instanceof Giant) {
                ((Giant)this.entity).getEquipment().setArmorContents(new ItemStack[]{this.equipment.getBoots(), this.equipment.getLeggings(), this.equipment.getChestPlate(), this.equipment.getHelmet()});
                ((Giant)this.entity).getEquipment().setItemInHand(this.equipment.getWeapon());
            }
            if (this.entity instanceof PigZombie) {
                ((PigZombie)this.entity).getEquipment().setArmorContents(new ItemStack[]{this.equipment.getBoots(), this.equipment.getLeggings(), this.equipment.getChestPlate(), this.equipment.getHelmet()});
                ((PigZombie)this.entity).getEquipment().setItemInHand(this.equipment.getWeapon());
            }
            for (PotionEffect potionEffect2 : this.entity.getActivePotionEffects()) {
                this.entity.removePotionEffect(potionEffect2.getType());
            }
            PotionEffect[] arrpotionEffect = this.equipment.getPotionEffects();
            int n = arrpotionEffect.length;
            int n2 = 0;
            while (n2 < n) {
                PotionEffect potionEffect2;
                potionEffect2 = arrpotionEffect[n2];
                this.entity.addPotionEffect(new PotionEffect(potionEffect2.getType(), potionEffect2.getDuration(), potionEffect2.getAmplifier(), false));
                ++n2;
            }
        }
        catch (Exception potionEffect) {
            // empty catch block
        }
    }
}

