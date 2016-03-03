/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.enums;

public enum VillagerType {
    PEASANT,
    WARRIOR,
    EWARRIOR;
    

    private VillagerType(String string2, int n2) {
    }

    public static VillagerType getVillagerType(String type) {
        VillagerType[] arrvillagerType = VillagerType.values();
        int n = arrvillagerType.length;
        int n2 = 0;
        while (n2 < n) {
            VillagerType sType = arrvillagerType[n2];
            if (sType.name().equalsIgnoreCase(type)) {
                return sType;
            }
            ++n2;
        }
        return null;
    }
}

