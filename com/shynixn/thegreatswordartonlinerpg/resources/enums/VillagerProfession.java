/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.enums;

public enum VillagerProfession {
    FARMER(0),
    LIBRARIAN(1),
    PRIEST(2),
    BLACKSMITH(3),
    BUTCHER(4);
    
    private int number;

    private VillagerProfession(String number, int n2, int n3) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public static VillagerProfession getVillagerProfession(String name) {
        VillagerProfession[] arrvillagerProfession = VillagerProfession.values();
        int n = arrvillagerProfession.length;
        int n2 = 0;
        while (n2 < n) {
            VillagerProfession villagerProfession = arrvillagerProfession[n2];
            if (villagerProfession.name().equalsIgnoreCase(name)) {
                return villagerProfession;
            }
            ++n2;
        }
        return null;
    }
}

