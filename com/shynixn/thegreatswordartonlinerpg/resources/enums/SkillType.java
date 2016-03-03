/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.enums;

public enum SkillType {
    UNIVERSAL,
    SWORD,
    AXE,
    BOW;
    

    private SkillType(String string2, int n2) {
    }

    public static SkillType getSkillType(String name) {
        SkillType[] arrskillType = SkillType.values();
        int n = arrskillType.length;
        int n2 = 0;
        while (n2 < n) {
            SkillType skillType = arrskillType[n2];
            if (skillType.name().equalsIgnoreCase(name)) {
                return skillType;
            }
            ++n2;
        }
        return null;
    }
}

