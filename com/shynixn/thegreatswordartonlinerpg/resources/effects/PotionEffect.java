/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.shynixn.thegreatswordartonlinerpg.resources.effects;

import java.io.Serializable;
import org.bukkit.potion.PotionEffectType;

public final class PotionEffect
implements Serializable {
    private static final long serialVersionUID = 1;
    private int p_id;
    private int p_amplifier;
    private int p_time;
    private int delay;

    public PotionEffect(org.bukkit.potion.PotionEffect effect, int delay) {
        this.delay = delay;
        this.p_id = effect.getType().getId();
        this.p_amplifier = effect.getAmplifier();
        this.p_time = effect.getDuration();
    }

    public PotionEffect(org.bukkit.potion.PotionEffect effect) {
        this(effect, 0);
    }

    public PotionEffect() {
    }

    public org.bukkit.potion.PotionEffect getEffect() {
        return new org.bukkit.potion.PotionEffect(PotionEffectType.getById((int)this.p_id), this.p_time, this.p_amplifier);
    }

    public int getDelay() {
        return this.delay;
    }
}

