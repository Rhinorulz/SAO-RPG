/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.BannerMeta
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.util.EulerAngle
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.wings;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.BannerWingsApi;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.wings.WingsData;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.NbtHandler;
import libraries.com.shynixn.utilities.BukkitShyUtilities;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

public final class Wings {
    private Entity entity;
    private WingsData data;
    private ArmorStand rightWing;
    private ArmorStand leftWing;
    private double x = 0.0;
    private double y = -39.7;
    private double z = -1.0;
    private boolean switcher = true;

    public Wings(Entity entity, WingsData data) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        this.entity = entity;
        this.data = data;
    }

    public double getCycleNumber() {
        return this.z;
    }

    public boolean isWing(Entity entity) {
        if (entity.equals((Object)this.rightWing)) {
            return true;
        }
        if (entity.equals((Object)this.leftWing)) {
            return true;
        }
        return false;
    }

    public void changeRightWingDesign(BannerMeta meta) {
        if (meta != null) {
            this.rightWing.getHelmet().setItemMeta((ItemMeta)meta);
        }
    }

    public void changeLeftWingDesign(BannerMeta meta) {
        if (meta != null) {
            this.leftWing.getHelmet().setItemMeta((ItemMeta)meta);
        }
    }

    public void changeSpeed(WingsData.WingsSpeed speed) {
        this.data.setSpeed(speed);
    }

    public void forceUpdate() {
        if (this.rightWing != null && this.leftWing != null) {
            this.rightWing.teleport(this.entity.getLocation());
            this.leftWing.teleport(this.entity.getLocation());
            this.rightWing.setHeadPose(new EulerAngle(this.x, this.y, this.z));
            this.leftWing.setHeadPose(new EulerAngle(this.x, -1.0 * this.y, -1.0 * this.z));
            this.z = this.switcher ? (this.z -= 0.1 * this.getSpeedController()) : (this.z += 0.1 * this.getSpeedController());
            if (BukkitShyUtilities.getFormatedDouble(this.z).equals("-2.5") || BukkitShyUtilities.getFormatedDouble(this.z).equals("-0.5")) {
                this.switcher = !this.switcher;
            }
        }
    }

    private double getSpeedController() {
        if (this.data.getSpeed() == WingsData.WingsSpeed.NORMAL) {
            return 1.0;
        }
        if (this.data.getSpeed() == WingsData.WingsSpeed.FAST) {
            return 2.0;
        }
        return 0.5;
    }

    public boolean update() {
        if (this.rightWing != null && this.leftWing != null) {
            this.rightWing.teleport(this.entity.getLocation());
            this.leftWing.teleport(this.entity.getLocation());
            if (this.entity instanceof Player) {
                Cardinal.getRegistry().getNbtHandler().updateFlyPosition((Player)this.entity, this.rightWing);
                Cardinal.getRegistry().getNbtHandler().updateFlyPosition((Player)this.entity, this.leftWing);
            }
            if (this.entity.getLocation().add(0.0, -1.0, 0.0).getBlock().getType() == Material.AIR) {
                this.rightWing.setHeadPose(new EulerAngle(this.x, this.y, this.z));
                this.leftWing.setHeadPose(new EulerAngle(this.x, -1.0 * this.y, -1.0 * this.z));
                this.z = this.switcher ? (this.z -= 0.1 * this.getSpeedController()) : (this.z += 0.1 * this.getSpeedController());
                if (BukkitShyUtilities.getFormatedDouble(this.z).equals("-2.5") || this.z < -2.5 || this.z > -0.5 || BukkitShyUtilities.getFormatedDouble(this.z).equals("-0.5")) {
                    this.switcher = !this.switcher;
                }
                return true;
            }
        }
        return false;
    }

    public void playDamageAnimation() {
        Cardinal.getRegistry().getNbtHandler().damageArmorStand(this.rightWing);
        Cardinal.getRegistry().getNbtHandler().damageArmorStand(this.leftWing);
    }

    public void respawn() {
        if (this.rightWing != null && !this.rightWing.isDead()) {
            this.rightWing.remove();
        }
        if (this.leftWing != null && !this.leftWing.isDead()) {
            this.leftWing.remove();
        }
        this.leftWing = (ArmorStand)this.entity.getWorld().spawnEntity(this.entity.getLocation(), EntityType.ARMOR_STAND);
        this.rightWing = (ArmorStand)this.entity.getWorld().spawnEntity(this.entity.getLocation(), EntityType.ARMOR_STAND);
        Cardinal.getRegistry().getNbtHandler().prepareArmorStand(this.leftWing);
        Cardinal.getRegistry().getNbtHandler().prepareArmorStand(this.rightWing);
        if (this.data.isSmall()) {
            this.rightWing.setSmall(true);
            this.leftWing.setSmall(true);
        }
        this.rightWing.setHelmet(this.data.getRightWingItemStack());
        this.leftWing.setHelmet(this.data.getLeftWingItemStack());
    }

    public void despawn() {
        if (this.rightWing != null && !this.rightWing.isDead()) {
            this.rightWing.remove();
        }
        if (this.leftWing != null && !this.leftWing.isDead()) {
            this.leftWing.remove();
        }
        if (this.entity instanceof Player) {
            ItemStack[] itemStacks = ((Player)this.entity).getInventory().getContents();
            int i = 0;
            while (i < itemStacks.length) {
                if (BukkitUtilities.u().compareItemNames(itemStacks[i], BannerWingsApi.DISPLAY_REMOVER, BannerWingsApi.LORE_REMOVER, Material.STRING)) {
                    ItemStack itemStack2 = new ItemStack(Material.FEATHER);
                    BukkitUtilities.u().nameItem(itemStack2, this.data.getDisplayName(), (Object)ChatColor.YELLOW + "Wings: " + this.data.getName());
                    itemStacks[i] = itemStack2;
                }
                ++i;
            }
            ((Player)this.entity).getInventory().setContents(itemStacks);
            ((Player)this.entity).updateInventory();
        }
    }

    public WingsData getDataCopy() {
        return this.data.clone();
    }
}

