/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.block.banner.Pattern
 *  org.bukkit.block.banner.PatternType
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.BannerMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.wings;

import java.util.List;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

public final class WingsData
extends BukkitObject {
    private static final long serialVersionUID = 1;
    private ItemStack rightWingItemStack = new ItemStack(Material.BANNER);
    private ItemStack leftWingItemStack = new ItemStack(Material.BANNER);
    private boolean small = false;
    private WingsSpeed speed = WingsSpeed.NORMAL;

    public WingsData(String name, String displayName) {
        super(name, displayName);
    }

    public WingsData(String name, String displayName, ItemStack rightWingItemStack, ItemStack leftWingItemStack, WingsSpeed speed) {
        super(name, displayName);
        this.rightWingItemStack = rightWingItemStack;
        this.leftWingItemStack = leftWingItemStack;
    }

    public WingsData clone() {
        return new WingsData(this.getName(), this.getDisplayName(), this.rightWingItemStack, this.leftWingItemStack, WingsSpeed.NORMAL);
    }

    public ItemStack getRightWingItemStack() {
        return this.rightWingItemStack;
    }

    public void setRightWingItemStack(ItemStack itemStack) {
        if (itemStack.getType() == Material.BANNER) {
            this.rightWingItemStack = itemStack;
        }
    }

    public ItemStack getLeftWingItemStack() {
        return this.leftWingItemStack;
    }

    public void setLeftWingItemStack(ItemStack itemStack) {
        if (itemStack.getType() == Material.BANNER) {
            this.leftWingItemStack = itemStack;
        }
    }

    public WingsSpeed getSpeed() {
        return this.speed;
    }

    public void setSpeed(WingsSpeed speed) {
        this.speed = speed;
    }

    public void toLeftItemStack(String[] data) {
        try {
            ItemStack itemStack = new ItemStack(Material.BANNER);
            BannerMeta m = (BannerMeta)itemStack.getItemMeta();
            m.setBaseColor(DyeColor.valueOf((String)data[0]));
            int i = 1;
            while (i < data.length) {
                try {
                    String[] parts = data[i].split(";");
                    Pattern pattern = new Pattern(DyeColor.valueOf((String)parts[0]), PatternType.valueOf((String)parts[1]));
                    m.addPattern(pattern);
                }
                catch (Exception parts) {
                    // empty catch block
                }
                ++i;
            }
            itemStack.setItemMeta((ItemMeta)m);
            this.leftWingItemStack = itemStack;
        }
        catch (Exception e) {
            this.leftWingItemStack = new ItemStack(Material.BANNER);
        }
    }

    public void toRightItemStack(String[] data) {
        try {
            ItemStack itemStack = new ItemStack(Material.BANNER);
            BannerMeta m = (BannerMeta)itemStack.getItemMeta();
            m.setBaseColor(DyeColor.valueOf((String)data[0]));
            int i = 1;
            while (i < data.length) {
                try {
                    String[] parts = data[i].split(";");
                    Pattern pattern = new Pattern(DyeColor.valueOf((String)parts[0]), PatternType.valueOf((String)parts[1]));
                    m.addPattern(pattern);
                }
                catch (Exception parts) {
                    // empty catch block
                }
                ++i;
            }
            itemStack.setItemMeta((ItemMeta)m);
            this.rightWingItemStack = itemStack;
        }
        catch (Exception e) {
            this.rightWingItemStack = new ItemStack(Material.BANNER);
        }
    }

    public String toLeftTxt() {
        try {
            String txt = ((BannerMeta)this.leftWingItemStack.getItemMeta()).getBaseColor().name().toUpperCase() + "\n";
            for (Pattern pattern : ((BannerMeta)this.leftWingItemStack.getItemMeta()).getPatterns()) {
                txt = String.valueOf(txt) + pattern.getColor().name().toUpperCase() + ";" + pattern.getPattern().name().toUpperCase() + "\n";
            }
            return txt;
        }
        catch (Exception e) {
            return "";
        }
    }

    public String toRightTxt() {
        try {
            String txt = ((BannerMeta)this.rightWingItemStack.getItemMeta()).getBaseColor().name().toUpperCase() + "\n";
            for (Pattern pattern : ((BannerMeta)this.rightWingItemStack.getItemMeta()).getPatterns()) {
                txt = String.valueOf(txt) + pattern.getColor().name().toUpperCase() + ";" + pattern.getPattern().name().toUpperCase() + "\n";
            }
            return txt;
        }
        catch (Exception e) {
            return "";
        }
    }

    public boolean isSmall() {
        return this.small;
    }

    public void setSmall(boolean small) {
        this.small = small;
    }

    public static enum WingsSpeed {
        FAST,
        NORMAL,
        SLOW;
        

        private WingsSpeed(String string2, int n2) {
        }

        public static WingsSpeed getWingsSpeedFromName(String name) {
            WingsSpeed[] arrwingsSpeed = WingsSpeed.values();
            int n = arrwingsSpeed.length;
            int n2 = 0;
            while (n2 < n) {
                WingsSpeed value = arrwingsSpeed[n2];
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
                ++n2;
            }
            return null;
        }
    }

}

