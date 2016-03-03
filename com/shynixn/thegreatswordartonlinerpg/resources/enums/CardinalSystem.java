/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.enums;

public enum CardinalSystem {
    SAO("You have enabled the floor system of Sword Art Online!"),
    GGO("You have enabled the race system of Alfheim Online!"),
    CLASSIC("You have enabled the classic RPG system without linking."),
    ALO("You have enabled the gun system of Gun Gale Online!");
    
    private String text;

    private CardinalSystem(String text, int n2, String string2) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static CardinalSystem getAincradSystemFromName(String name) {
        CardinalSystem[] arrcardinalSystem = CardinalSystem.values();
        int n = arrcardinalSystem.length;
        int n2 = 0;
        while (n2 < n) {
            CardinalSystem system = arrcardinalSystem[n2];
            if (system.name().equalsIgnoreCase(name)) {
                return system;
            }
            ++n2;
        }
        return null;
    }
}

