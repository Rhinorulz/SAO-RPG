/*
 * Decompiled with CFR 0_110.
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.skills;

import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;

public class SkillTmp {
    private Skill skill;
    private int runningtime = 0;
    private int schedulerid = 0;

    public SkillTmp(Skill skill) {
        this.skill = skill;
    }

    public int getschedulerId() {
        return this.schedulerid;
    }

    public void setschedulerId(int schedulerid) {
        this.schedulerid = schedulerid;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public void disabled() {
        this.runningtime = this.skill.getExecutingTime();
    }

    public void update() {
        ++this.runningtime;
    }

    public int getRunningTime() {
        return this.runningtime;
    }
}

