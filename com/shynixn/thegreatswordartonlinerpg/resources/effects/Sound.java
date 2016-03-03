/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package com.shynixn.thegreatswordartonlinerpg.resources.effects;

import java.io.Serializable;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class Sound
implements Serializable {
    private static final long serialVersionUID = 1;
    private org.bukkit.Sound sound;
    private int delay;
    private double volume;
    private double pitch;

    public Sound(org.bukkit.Sound sound, double volume, double pitch, int delay) {
        this.delay = delay;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public Sound(org.bukkit.Sound sound, double volume, double pitch) {
        this(sound, volume, pitch, 0);
    }

    public Sound() {
    }

    public void play(Entity entity) {
        for (Player p : entity.getWorld().getPlayers()) {
            p.playSound(entity.getLocation(), this.getSound(), (float)this.getVolume(), (float)this.getPitch());
        }
    }

    public org.bukkit.Sound getSound() {
        return this.sound;
    }

    public int getDelay() {
        return this.delay;
    }

    public double getVolume() {
        return this.volume;
    }

    public double getPitch() {
        return this.pitch;
    }
}

