/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.shynixn.thegreatswordartonlinerpg.resources.events.skill;

import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradPlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class AincradLearnSkillEvent
extends AincradPlayerEvent {
    private ItemStack itemStack;

    public AincradLearnSkillEvent(Player player, ItemStack itemStack) {
        super(player);
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}

