/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.enums;

public enum SlotType {
    HELMET("helmet"),
    LEGGINGS("leggings"),
    CHESTPLATE("chestplate"),
    BOOTS("boots"),
    WEAPON("weapon");
    
    private String text;

    private SlotType(String text, int n2, String string2) {
        this.text = text;
    }

    public static SlotType getSlotType(String name) {
        SlotType[] arrslotType = SlotType.values();
        int n = arrslotType.length;
        int n2 = 0;
        while (n2 < n) {
            SlotType type = arrslotType[n2];
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
            ++n2;
        }
        return null;
    }

    public String toString() {
        return this.text;
    }
}

