/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.skill;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;

public class AincradRequestSkillEvent
extends AincradEvent {
    private Skill skill;
    private String skillName;

    public AincradRequestSkillEvent(String skillName) {
        if (skillName == null) {
            throw new IllegalArgumentException("Skillname cannot be null!");
        }
        this.skillName = skillName;
    }

    public String getSkillName() {
        return this.skillName;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}

