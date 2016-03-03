/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.skills;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.cardinal.Permission;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillExecutor;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.InventoryType;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.skill.AincradLearnSkillEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.storage.AincradRequestInventoryTypeEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.ScreenMessenger;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi.SkillAPICompatibility;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi.SkillAPICompatibilitySkill;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitEvents;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkillListener
extends BukkitEvents
implements SkillExecutor.SkillHandler {
    private SkillSystem skillManager;

    public SkillListener(SkillSystem skillSystem, JavaPlugin plugin) {
        super(plugin);
        this.skillManager = skillSystem;
        SkillExecutor.getInstance(plugin).registerSkillHandler(this);
    }

    @EventHandler
    public void cancelFirstSlotItemMoveEvent(InventoryClickEvent event) {
        if (Cardinal.getSkillExecutor().isUsingSkills((LivingEntity)((Player)event.getWhoClicked())) && (event.getSlot() == 0 || event.getRawSlot() == 0 || event.getSlot() == 36 || event.getSlot() == 37 || event.getSlot() == 38 || event.getSlot() == 39)) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        }
    }

    @EventHandler
    public void cancelOnPlayerTeleportEvent(PlayerTeleportEvent event) {
        if (Cardinal.getSkillExecutor().isUsingSkills((LivingEntity)event.getPlayer()) && event.getTo().getBlock().getType() != Material.AIR) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void cancelPlayerDropItemEvent(PlayerDropItemEvent event) {
        if (Cardinal.getSkillExecutor().isUsingSkills((LivingEntity)event.getPlayer()) && event.getPlayer().getInventory().getHeldItemSlot() == 0) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void sendOutHitEffectsEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity && Cardinal.getSkillExecutor().isUsingSkills((LivingEntity)event.getDamager())) {
            for (Skill skill : Cardinal.getSkillExecutor().getExecutingSkills((LivingEntity)event.getDamager())) {
                Cardinal.getSkillExecutor().sendOutHitEffects((LivingEntity)event.getDamager(), skill);
                Cardinal.getSkillExecutor().sendOutHitEnemyEffects((LivingEntity)event.getEntity(), skill);
            }
        }
    }

    @EventHandler
    public void activateSkillEvent(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && Cardinal.getGenericSystem().isValidAction(event.getPlayer())) {
            Skill skill = this.getSkillItem(event.getPlayer().getItemInHand());
            if (skill != null && (event.getPlayer().hasPermission((Object)((Object)Permission.SKILL_USE_ONE) + skill.getName()) || event.getPlayer().hasPermission((String)((Object)Permission.SKILL_USE_ALL)))) {
                AincradRequestInventoryTypeEvent inventoryTypeEvent = new AincradRequestInventoryTypeEvent(event.getPlayer());
                Cardinal.call().notifyStorageSystem(inventoryTypeEvent);
                this.learnOrExecuteSkill(event, skill, inventoryTypeEvent);
            } else {
                SkillAPICompatibilitySkill apiSkill = this.getApiSKill(event.getPlayer().getItemInHand());
                if (apiSkill != null) {
                    AincradRequestInventoryTypeEvent inventoryTypeEvent = new AincradRequestInventoryTypeEvent(event.getPlayer());
                    Cardinal.call().notifyStorageSystem(inventoryTypeEvent);
                    this.learnOrExecuteSkillApi(event, apiSkill, inventoryTypeEvent);
                }
            }
        }
    }

    private void learnOrExecuteSkillApi(PlayerInteractEvent event, SkillAPICompatibilitySkill apiSkill, AincradRequestInventoryTypeEvent inventoryTypeEvent) {
        if (inventoryTypeEvent.getInventoryType() != null && inventoryTypeEvent.getInventoryType() != InventoryType.SKILLS) {
            this.manageNewSkill(event.getPlayer());
        } else {
            Bukkit.getServer().dispatchCommand((CommandSender)event.getPlayer(), "class cast " + apiSkill.getName());
        }
    }

    private void learnOrExecuteSkill(PlayerInteractEvent event, Skill skill, AincradRequestInventoryTypeEvent inventoryTypeEvent) {
        if (inventoryTypeEvent.getInventoryType() != null && inventoryTypeEvent.getInventoryType() != InventoryType.SKILLS) {
            this.manageNewSkill(event.getPlayer());
        } else {
            Cardinal.getSkillExecutor().executeSkill((LivingEntity)event.getPlayer(), skill);
        }
    }

    private void manageNewSkill(Player player) {
        AincradLearnSkillEvent learnSkillEvent = new AincradLearnSkillEvent(player, player.getItemInHand().clone());
        player.getInventory().clear(player.getInventory().getHeldItemSlot());
        Cardinal.call().notifyStorageSystem(learnSkillEvent);
        Cardinal.getLogger().logPlayer(player, CardinalLanguage.Cardinal.SKILL_DETECTED);
        player.updateInventory();
    }

    private Skill getSkillItem(ItemStack item) {
        for (BukkitObject object : this.skillManager.getItems()) {
            Skill skill = (Skill)object;
            if (!BukkitUtilities.u().compareItemNames(item, skill.getDisplayName(), new String[]{(Object)ChatColor.GREEN + "Skill " + skill.getName()}, null, new int[1])) continue;
            return skill;
        }
        return null;
    }

    private SkillAPICompatibilitySkill getApiSKill(ItemStack itemStack) {
        try {
            if (((String)itemStack.getItemMeta().getLore().get(0)).contains((Object)ChatColor.YELLOW + "SkillAPI")) {
                SkillAPICompatibilitySkill skill = Cardinal.getRegistry().getSkillAPIRegistry().getSkill(((String)itemStack.getItemMeta().getLore().get(0)).split((Object)ChatColor.YELLOW + "SkillAPI ")[1]);
                return skill;
            }
        }
        catch (Exception skill) {
            // empty catch block
        }
        return null;
    }

    @Override
    public void skillLoadEvent(LivingEntity entity, Skill skill) {
        if (entity instanceof Player) {
            Cardinal.getRegistry().getScreenMessenger().setActionBar((Player)entity, (Object)((Object)BukkitChatColor.YELLOW) + CardinalLanguage.Skill.SKILL_LOADING);
        }
    }

    @Override
    public void skillActivatedEvent(LivingEntity entity, Skill skill) {
        if (entity instanceof Player) {
            Cardinal.getRegistry().getScreenMessenger().setActionBar((Player)entity, CardinalLanguage.Skill.SKILL_ACTIVATED);
        }
    }

    @Override
    public void skillDeactivatedEvent(LivingEntity entity, Skill skill) {
        if (entity instanceof Player) {
            Cardinal.getRegistry().getScreenMessenger().setActionBar((Player)entity, CardinalLanguage.Skill.SKILL_DEACTIVATED);
        }
    }

    @Override
    public void skillCooldownEvent(LivingEntity entity, Skill skill) {
        if (entity instanceof Player) {
            Cardinal.getRegistry().getScreenMessenger().setActionBar((Player)entity, CardinalLanguage.Skill.SKILL_COOLDOWN);
        }
    }

    @Override
    public void skillDisabledEvent(LivingEntity entity, Skill skill) {
        if (entity instanceof Player) {
            Cardinal.getRegistry().getScreenMessenger().setActionBar((Player)entity, CardinalLanguage.Skill.SKILL_DISABLED);
        }
    }

    @Override
    public void skillWrongTypeEvent(LivingEntity entity, Skill skill) {
        if (entity instanceof Player) {
            Cardinal.getRegistry().getScreenMessenger().setActionBar((Player)entity, CardinalLanguage.Skill.SKILL_WRONG_TYPE);
        }
    }

    @Override
    public void skillEffectEvent(LivingEntity entity, Skill skill, int timeLeft) {
    }
}

