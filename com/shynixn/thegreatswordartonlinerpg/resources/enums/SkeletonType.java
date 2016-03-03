/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.enums;

public enum SkeletonType {
    STANDARD(0),
    WITHER(1);
    
    private int number;

    private SkeletonType(String number, int n2, int n3) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }
}

