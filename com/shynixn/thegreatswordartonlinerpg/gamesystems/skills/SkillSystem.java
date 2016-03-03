/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.skills;

import com.shynixn.thegreatswordartonlinerpg.TheGreatSwordArtOnlineRPG;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillArena;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillArenaManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillCommandExecutor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillFileManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillListener;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.cardinal.AincradBackCommandEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.skill.AincradRequestSkillEvent;
import java.util.HashMap;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitManager;
import libraries.com.shynixn.utilities.BukkitObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkillSystem
extends BukkitManager
implements CardinalSystem {
    private SkillArenaManager arenaManager;

    public SkillSystem(TheGreatSwordArtOnlineRPG plugin) {
        super(new SkillFileManager(plugin), BukkitManager.SaveType.SINGEL);
        this.arenaManager = new SkillArenaManager(this, plugin);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillListener(this, plugin);
        new com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillCommandExecutor(this, this.arenaManager, plugin);
    }

    @Override
    public Skill getItemFromName(String name) {
        return (Skill)super.getItemFromName(name);
    }

    @Override
    public void handleCardinalEvent(AincradEvent event) throws Exception {
        Player player;
        if (event instanceof AincradRequestSkillEvent) {
            AincradRequestSkillEvent skillEvent = (AincradRequestSkillEvent)event;
            skillEvent.setSkill(this.getItemFromName(skillEvent.getSkillName()).clone());
        } else if (event instanceof AincradBackCommandEvent && this.arenaManager.arenas.containsKey((Object)(player = ((AincradBackCommandEvent)event).getPlayer()))) {
            player.teleport(this.arenaManager.arenas.get((Object)player).getArea().getCenter());
        }
    }

    public void reset() {
        this.arenaManager.reset();
    }
}

