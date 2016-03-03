/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package libraries.com.shynixn.utilities;

import java.io.Serializable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BukkitPotionEffect
implements Serializable {
    private static final long serialVersionUID = 1;
    private int id;
    private int time;
    private int strength;

    public BukkitPotionEffect(PotionEffect effect) {
        this.id = effect.getType().getId();
        this.time = effect.getDuration();
        this.strength = effect.getAmplifier();
    }

    public BukkitPotionEffect() {
    }

    public PotionEffect getPotionEffect() {
        return new PotionEffect(PotionEffectType.getById((int)this.id), this.time, this.strength);
    }

    public static BukkitPotionEffect[] convertToBukkitPotionEffects(PotionEffect[] effects) {
        BukkitPotionEffect[] effects2 = new BukkitPotionEffect[effects.length];
        int i = 0;
        while (i < effects.length) {
            effects2[i] = new BukkitPotionEffect(effects[i]);
            ++i;
        }
        return effects2;
    }
}

