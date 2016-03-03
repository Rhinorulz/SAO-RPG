/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.effects;

import com.shynixn.thegreatswordartonlinerpg.resources.enums.Direction;
import java.io.Serializable;

public class Launch
implements Serializable {
    private static final long serialVersionUID = 1;
    private Direction direction;
    private int delay;
    private double amplifier;

    public Launch(Direction direction, double amplifier, int delay) {
        this.direction = direction;
        this.delay = delay;
        this.amplifier = amplifier;
    }

    public Launch(Direction direction, double amplifier) {
        this(direction, amplifier, 0);
    }

    public Launch() {
    }

    public Direction getDirection() {
        return this.direction;
    }

    public double getAmplifier() {
        return this.amplifier;
    }

    public int getDelay() {
        return this.delay;
    }
}

