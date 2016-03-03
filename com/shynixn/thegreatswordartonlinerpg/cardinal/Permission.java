/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

public enum Permission {
    SKILL_USE_ALL("sao.skills.all"),
    SKILL_USE_ONE("sao.skills.use."),
    SAO_JOIN("sao.join"),
    SAO_COMMANDS("sao.commands"),
    SAO_BUILD("sao.build"),
    SCOREBOARD("sao.scoreboard.use"),
    EQUIPMENTINVENTORY("sao.scoreboard.use"),
    PARTY("sao.scoreboard.use"),
    SKILLINVENTORY("sao.skills.use"),
    PETSINVENTORY("sao.pets.use");
    
    private String text;

    private Permission(String text, int n2, String string2) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }
}

