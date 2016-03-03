/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.effects;

import com.shynixn.thegreatswordartonlinerpg.resources.enums.Direction;
import java.io.Serializable;

public final class Teleport
implements Serializable {
    private static final long serialVersionUID = 1;
    private Direction direction;
    private int delay;
    private int blockamount;

    public Teleport(Direction direction, int blockamount, int delay) {
        this.direction = direction;
        this.blockamount = blockamount;
        this.delay = delay;
    }

    public Teleport(Direction direction, int blockamount) {
        this(direction, blockamount, 0);
    }

    public Teleport() {
    }

    public int getBlockAmount() {
        return this.blockamount;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getDelay() {
        return this.delay;
    }
}

