/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  com.sucy.skill.SkillAPI
 *  com.sucy.skill.api.classes.RPGClass
 *  com.sucy.skill.api.player.PlayerClass
 *  com.sucy.skill.api.player.PlayerData
 *  com.sucy.skill.api.skills.Skill
 *  com.sucy.skill.tree.SkillTree
 *  com.sucy.skill.tree.basic.InventoryTree
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.storage.PlayerSave;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.races.RacesRequestEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradRequestPlayerSaveEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi.SkillAPICompatibilitySkill;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.classes.RPGClass;
import com.sucy.skill.api.player.PlayerClass;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.skills.Skill;
import com.sucy.skill.tree.SkillTree;
import com.sucy.skill.tree.basic.InventoryTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class SkillAPICompatibility {
    public SkillAPICompatibilitySkill getSkill(String name) {
        Skill skill = SkillAPI.getSkill((String)name);
        if (skill == null) {
            return null;
        }
        SkillAPICompatibilitySkill skill2 = new SkillAPICompatibilitySkill(skill.getName(), skill.getType(), skill.getDescription());
        return skill2;
    }

    public String getProfessionFromPlayer(Player player) {
        Iterator iterator = SkillAPI.getPlayerData((OfflinePlayer)player).getClasses().iterator();
        if (iterator.hasNext()) {
            PlayerClass c = (PlayerClass)iterator.next();
            return c.getData().getName();
        }
        return null;
    }

    public ItemStack[] getSkillsFromPlayer(Player player) {
        Iterator iterator = SkillAPI.getPlayerData((OfflinePlayer)player).getClasses().iterator();
        if (iterator.hasNext()) {
            PlayerClass c = (PlayerClass)iterator.next();
            return ((InventoryTree)c.getData().getSkillTree()).getInventory(SkillAPI.getPlayerData((OfflinePlayer)player)).getContents();
        }
        return new ItemStack[0];
    }

    public void switchtoSkills(Player player) {
        if (Cardinal.getSettings().isSkillapiInsertskills()) {
            player.updateInventory();
            player.getInventory().setHeldItemSlot(7);
            ItemStack[] arritemStack = this.getSkillsFromPlayer(player);
            int n = arritemStack.length;
            int n2 = 0;
            while (n2 < n) {
                ItemStack item = arritemStack[n2];
                if (item != null) {
                    this.saveInventoryAdding(player, item);
                }
                ++n2;
            }
        }
    }

    public boolean joinRace(Player player, AincradRequestPlayerSaveEvent playerSaveEvent) {
        try {
            RacesRequestEvent requestEvent = new RacesRequestEvent();
            Cardinal.call().notifyRaceSystem(requestEvent);
            for (BukkitObject object : requestEvent.getRaces()) {
                if (!object.getName().equalsIgnoreCase(this.getProfessionFromPlayer(player))) continue;
                playerSaveEvent.getPlayerSave().setRaceName(object.getName());
                break;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    private void saveInventoryAdding(Player player, ItemStack item) {
        int i = 0;
        ArrayList<String> lore = new ArrayList<String>();
        item.setAmount(1);
        this.setFancyDisplayName(item);
        lore.add((Object)ChatColor.YELLOW + "SkillAPI " + ChatColor.stripColor((String)item.getItemMeta().getDisplayName()));
        for (String s : item.getItemMeta().getLore()) {
            lore.add(s);
        }
        BukkitUtilities.u().nameItem(item, null, lore);
        ItemStack[] arritemStack = player.getInventory().getContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack = arritemStack[n2];
            try {
                if (BukkitUtilities.u().compareItemNames(item, itemStack.getItemMeta().getDisplayName(), (String)itemStack.getItemMeta().getLore().get(0), null)) {
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                }
            }
            catch (Exception var9_10) {
                // empty catch block
            }
            ++i;
            ++n2;
        }
        player.getInventory().addItem(new ItemStack[]{item});
    }

    private void setFancyDisplayName(ItemStack itemStack) {
        try {
            String newName = "";
            String[] parts = itemStack.getItemMeta().getDisplayName().split(" ");
            int i = 0;
            while (i < parts.length - 1) {
                newName = i == 0 ? String.valueOf(newName) + parts[i] : String.valueOf(newName) + " " + parts[i];
                ++i;
            }
            BukkitUtilities.u().nameItemDisplay(itemStack, newName);
        }
        catch (Exception newName) {
            // empty catch block
        }
    }
}

