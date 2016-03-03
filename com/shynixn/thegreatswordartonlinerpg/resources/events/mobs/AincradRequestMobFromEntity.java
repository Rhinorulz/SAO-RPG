/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.LivingEntity
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.mobs;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import org.bukkit.entity.LivingEntity;

public class AincradRequestMobFromEntity
extends AincradEvent {
    private LivingEntity entity;
    private Mob mob;

    public AincradRequestMobFromEntity(LivingEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public Mob getMob() {
        return this.mob;
    }

    public void setMob(Mob mob) {
        this.mob = mob;
    }
}

