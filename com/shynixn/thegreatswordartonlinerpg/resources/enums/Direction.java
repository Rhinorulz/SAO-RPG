/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.enums;

public enum Direction {
    UP("up"),
    FORWARD("forward"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right"),
    BACK("back");
    
    private String text;

    private Direction(String text, int n2, String string2) {
        this.text = text;
    }

    public static Direction getDirection(String name) {
        Direction[] arrdirection = Direction.values();
        int n = arrdirection.length;
        int n2 = 0;
        while (n2 < n) {
            Direction direction = arrdirection[n2];
            if (direction.name().equalsIgnoreCase(name)) {
                return direction;
            }
            ++n2;
        }
        return null;
    }

    public String toString() {
        return this.text;
    }
}

