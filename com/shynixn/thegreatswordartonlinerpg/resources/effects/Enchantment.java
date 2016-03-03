/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.enchantments.Enchantment
 */
package com.shynixn.thegreatswordartonlinerpg.resources.effects;

import com.shynixn.thegreatswordartonlinerpg.resources.enums.SlotType;
import java.io.Serializable;

public final class Enchantment
implements Serializable {
    private static final long serialVersionUID = 1;
    private int e_id;
    private int level;
    private int delay;
    private SlotType position;

    public Enchantment(org.bukkit.enchantments.Enchantment enchantment, int level, SlotType position, int delay) {
        this.delay = delay;
        this.e_id = enchantment.getId();
        this.level = level;
        this.position = position;
    }

    public Enchantment(org.bukkit.enchantments.Enchantment enchantment, int level, SlotType position) {
        this(enchantment, level, position, 0);
    }

    public Enchantment() {
    }

    public SlotType getPosition() {
        return this.position;
    }

    public org.bukkit.enchantments.Enchantment getEnchantment() {
        return org.bukkit.enchantments.Enchantment.getById((int)this.e_id);
    }

    public int getLevel() {
        return this.level;
    }

    public int getDelay() {
        return this.delay;
    }
}

