/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.enums;

public enum CatType {
    JUNGLE(0),
    BLACK(1),
    YELLOW(2),
    WHITE(3);
    
    private int number;

    private CatType(String number, int n2, int n3) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public static CatType getCatTypeFromName(String name) {
        CatType[] arrcatType = CatType.values();
        int n = arrcatType.length;
        int n2 = 0;
        while (n2 < n) {
            CatType catType = arrcatType[n2];
            if (catType.name().equalsIgnoreCase(name)) {
                return catType;
            }
            ++n2;
        }
        return null;
    }
}

