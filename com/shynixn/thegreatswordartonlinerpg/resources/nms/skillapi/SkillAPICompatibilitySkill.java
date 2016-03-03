/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi;

import java.util.List;

public class SkillAPICompatibilitySkill {
    private String name;
    private String[] lore;
    private String type;

    public SkillAPICompatibilitySkill(String name, String type, List<String> lore) {
        this.name = name;
        this.lore = lore.toArray(new String[lore.size()]);
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String[] getLore() {
        return this.lore;
    }

    public String getType() {
        return this.type;
    }
}

