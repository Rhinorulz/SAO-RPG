/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
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

public final class ParticleEffect
implements Serializable {
    private static final long serialVersionUID = 1;
    private int effectcount = 1;
    private float effectspeed = 0.1f;
    private float effectsizex = 5.0f;
    private float effectsizey = 5.0f;
    private float effectsizez = 5.0f;
    private libraries.com.darkblade12.particleeffects.ParticleEffect effect = null;
    private int delay;

    public ParticleEffect(libraries.com.darkblade12.particleeffects.ParticleEffect effect, int count, float speed, float sizex, float sizey, float sizez, int delay) {
        this.effect = effect;
        this.effectcount = count;
        this.effectsizex = sizex;
        this.effectsizey = sizey;
        this.effectsizez = sizez;
        this.effectspeed = speed;
        this.delay = delay;
    }

    public ParticleEffect(libraries.com.darkblade12.particleeffects.ParticleEffect effect, int count, float speed, float sizex, float sizey, float sizez) {
        this(effect, count, speed, sizex, sizey, sizez, 0);
    }

    public ParticleEffect(libraries.com.darkblade12.particleeffects.ParticleEffect effect, double count, double speed, double sizex, double sizey, double sizez) {
        this(effect, (int)count, (float)speed, (float)sizex, (float)sizey, (float)sizez, 0);
    }

    public ParticleEffect() {
    }

    public ParticleEffect(libraries.com.darkblade12.particleeffects.ParticleEffect particelEffectByName, int count, double speed, double sizex, double sizey, double sizez, int delay) {
        this(particelEffectByName, count, (float)speed, (float)sizex, (float)sizey, (float)sizez, delay);
    }

    public void play(Entity entity) {
        this.effect.display(this.getSizeX(), this.getSizeY(), this.getSizeZ(), this.getSpeed(), this.getCount(), entity.getLocation(), entity.getWorld().getPlayers());
    }

    public int getCount() {
        return this.effectcount;
    }

    public float getSpeed() {
        return this.effectspeed;
    }

    public float getSizeX() {
        return this.effectsizex;
    }

    public float getSizeY() {
        return this.effectsizey;
    }

    public float getSizeZ() {
        return this.effectsizez;
    }

    public libraries.com.darkblade12.particleeffects.ParticleEffect getEffect() {
        return this.effect;
    }

    public int getDelay() {
        return this.delay;
    }
}

