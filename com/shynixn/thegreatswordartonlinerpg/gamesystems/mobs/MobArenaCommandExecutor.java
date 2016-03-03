/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.Mob;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArena;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobArenaManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobEquipment;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobRespawnManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.mobs.MobSystem;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.CatType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Direction;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.VillagerProfession;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.VillagerType;
import com.shynixn.thegreatswordartonlinerpg.resources.events.AincradEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.skill.AincradRequestSkillEvent;
import com.shynixn.thegreatswordartonlinerpg.resources.events.wings.AincradWingsExistEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import libraries.com.shynixn.utilities.LivingEntityType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public final class MobArenaCommandExecutor
extends BukkitCommands {
    private MobSystem mobManager;
    private MobArenaManager arenaManager;
    private MobRespawnManager respawnManager;

    public MobArenaCommandExecutor(MobSystem manager, MobArenaManager arenaManager, MobRespawnManager respawnManager, JavaPlugin plugin) {
        super("saomobs", plugin);
        this.mobManager = manager;
        this.arenaManager = arenaManager;
        this.respawnManager = respawnManager;
    }

    @Override
    public void playerSendCommandEvent(Player player, String[] args) {
        if (args.length == 4 && args[0].equalsIgnoreCase("create")) {
            this.createMobCommand(player, args[1], args[2], args[3]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            this.removeMobCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("edit")) {
            this.editMobCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("spawn")) {
            this.spawnMobCommand(player, args[1]);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("stopedit")) {
            this.stopeditMobCommand(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("transfer")) {
            this.transferCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("health") && BukkitUtilities.u().tryParseDouble(args[1])) {
            this.setMobHealthCommand(player, args[1], Double.parseDouble(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("damage") && BukkitUtilities.u().tryParseDouble(args[1])) {
            this.setMobDamageCommand(player, args[1], Double.parseDouble(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("kill")) {
            this.killMobCommand(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("mobs")) {
            this.printMobListCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("arenadaylightburn")) {
            this.toggleArenaDayLightCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("arenadamageable")) {
            this.toggleArenaDamageableCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("arenamoveable")) {
            this.toggleArenaMoveablemageableCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("arenaattacking")) {
            this.toggleArenaAttackingCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("arenahealth")) {
            this.toggleArenaHealthCommand(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("helmet")) {
            this.setHelmetCommand(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("chestplate")) {
            this.setChestPlateCommand(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("leggings")) {
            this.setLeggignsCommand(player);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("boots")) {
            this.setBootsCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("dchelmet") && BukkitUtilities.u().tryParseDouble(args[1])) {
            this.setHelmetDropChanceCommand(player, Double.parseDouble(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("dcchestplate") && BukkitUtilities.u().tryParseDouble(args[1])) {
            this.setChestPlateDropChanceCommand(player, Double.parseDouble(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("dcleggings") && BukkitUtilities.u().tryParseDouble(args[1])) {
            this.setLeggignsDropChanceCommand(player, Double.parseDouble(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("dcboots") && BukkitUtilities.u().tryParseDouble(args[1])) {
            this.setBootsDropChanceCommand(player, Double.parseDouble(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("dcweapon") && BukkitUtilities.u().tryParseDouble(args[1])) {
            this.setWeaponDropChanceCommand(player, Double.parseDouble(args[1]));
        } else if (args.length == 3 && args[0].equalsIgnoreCase("addskill") && BukkitUtilities.u().tryParseDouble(args[2])) {
            this.addSkillCommand(player, args[1], Double.parseDouble(args[2]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("removeskill")) {
            this.removeSkillCommand(player, args[1]);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("skills")) {
            this.printSkillsOnMObCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("rider")) {
            this.rideMobCommand(player, args[1]);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("knockback") && BukkitUtilities.u().tryParseDouble(args[2])) {
            this.knockBackCommand(player, args[1], Double.parseDouble(args[2]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("fireattack") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.fireAttackCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("daylightburn")) {
            this.toggleDayLightCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("damageable")) {
            this.toggleDamageableCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("moveable")) {
            this.toggleMoveAbleCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("attacking")) {
            this.toggleAttackingCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("health")) {
            this.toggleHealthCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("damaging")) {
            this.toggleDoesDamageCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("displayname")) {
            this.toggleDisplayNameCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("classicdrops")) {
            this.toggleClassicDropsCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("slimesize") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.setSlimeSizeCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("villager")) {
            this.setVillageTypeCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("profession")) {
            this.setProfessionCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("cat")) {
            this.setCatTypeCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("babymode")) {
            this.toggleIsBabyCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("withermode")) {
            this.toggleWitherMode(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("dogmode")) {
            this.toggleDogMode(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("catmode")) {
            this.toggleCatMode(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("powered")) {
            this.togglePowered(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("explosiondestroy")) {
            this.toggleExplosionDestroy(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("explosionradius") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.setExplosionradiusCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("flying")) {
            this.toggleFlyingCommand(player);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("maxflying") && BukkitUtilities.u().tryParseDouble(args[2])) {
            this.setMaxFlying(player, args[1], Double.parseDouble(args[2]));
        } else if (args.length == 3 && args[0].equalsIgnoreCase("minflying") && BukkitUtilities.u().tryParseDouble(args[2])) {
            this.setMinFlying(player, args[1], Double.parseDouble(args[2]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("toggle") && args[1].equalsIgnoreCase("wings")) {
            this.toggleWingsCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("wings")) {
            this.setWingsCommand(player, args[1]);
        } else if (args.length == 1 && args[0].equals("7")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Mobs  ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs explosionradius <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle explosiondestroy");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c7/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equals("6")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Mobs  ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs slimesize <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle withermode");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle babymode");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle dogmode");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle catmode");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle powered");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs cat <jungle/black/white/yellow>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs villager <peasant/warrior/ewarrior>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs profession <farmer/librarian/priest/blacksmith/butcher>");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c6/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equals("5")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Mobs  ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle flying");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs maxflying <horizontal/vertical> <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs minflying <horizontal/vertical> <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle wings");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs wings <name>");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c5/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equals("4")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Mobs  ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs addskill <skill> <health>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs removeskill <skill");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs skills");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs knockback <direction> <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs fireattack <duration>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs rider <mob/none>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle daylightburn");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle damageable");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle moveable");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle attacking");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle health");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle damaging");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle displayname");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle classicdrops");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c4/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equals("3")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Mobs  ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs helmet");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs chestplate");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs leggings");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs boots");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs dchelmet <percent>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs dcchestplate <percent>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs dcleggings <percent>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs dcboots <percent>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs dcweapon <percent>");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c3/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equals("2")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Mobs  ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle arenadaylightburn");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle arenadamageable");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle arenamoveable");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle arenaattacking");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs toggle arenahealth");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c2/7\u2510                            ");
            player.sendMessage("");
        } else {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Mobs  ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs create <type> <name> <displayname>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs remove <mob>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs edit <mob>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs spawn <mob/entity>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs stopedit");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs transfer");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs health <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs damage <amount>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs kill");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saomobs mobs");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/7\u2510                            ");
            player.sendMessage("");
        }
    }

    private void setWingsCommand(Player player, String wings) {
        AincradWingsExistEvent wingsExistEvent = new AincradWingsExistEvent(wings);
        Cardinal.call().notifyWingsSystem(wingsExistEvent);
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (!wingsExistEvent.isExist()) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_WINGS_NOT_EXIST);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            Mob mob = this.arenaManager.arenas.get((Object)player).getMob();
            mobEquipment.setWingsName(wings);
            mob.update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_WINGS);
        }
    }

    private void toggleWingsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            Mob mob = this.arenaManager.arenas.get((Object)player).getMob();
            if (!mobEquipment.isWings()) {
                mobEquipment.setWings(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_WINGS);
            } else {
                mobEquipment.setWings(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_WINGS);
            }
            mob.update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void setMinFlying(Player player, String type, double amount) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (!type.equalsIgnoreCase("horizontal") && !type.equalsIgnoreCase("vertical")) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_FLYDIRECTION_NOT_EXIST);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            Mob mob = this.arenaManager.arenas.get((Object)player).getMob();
            if (type.equalsIgnoreCase("horizontal")) {
                mobEquipment.setMinLeft(amount);
            } else if (type.equalsIgnoreCase("vertical")) {
                mobEquipment.setMinHight(amount);
            }
            mob.update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_MIN_FLYING);
        }
    }

    private void setMaxFlying(Player player, String type, double amount) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (!type.equalsIgnoreCase("horizontal") && !type.equalsIgnoreCase("vertical")) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_FLYDIRECTION_NOT_EXIST);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            Mob mob = this.arenaManager.arenas.get((Object)player).getMob();
            if (type.equalsIgnoreCase("horizontal")) {
                mobEquipment.setMaxLeft(amount);
            } else if (type.equalsIgnoreCase("vertical")) {
                mobEquipment.setMaxHight(amount);
            }
            mob.update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_MAX_FLYING);
        }
    }

    private void toggleFlyingCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            Mob mob = this.arenaManager.arenas.get((Object)player).getMob();
            if (!mobEquipment.isFlying()) {
                mobEquipment.setFlying(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_FLYING);
            } else {
                mobEquipment.setFlying(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_FLYING);
            }
            mob.update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void createMobCommand(Player player, String type, String name, String displayName) {
        if (LivingEntityType.getTypeFromName(type) == null) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_ENTITYTYPE_NOT_EXIST);
            BukkitUtilities.u().sendfancyValues(player, this.getLivingEntitys(), "Entitytypes", ChatColor.YELLOW);
        } else if (this.mobManager.contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_ALREADY_EXISTS);
        } else {
            MobEquipment equipment = new MobEquipment(name, displayName, EntityType.valueOf((String)type.toUpperCase()));
            this.mobManager.addItem(equipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ADDED);
        }
    }

    private void removeMobCommand(Player player, String name) {
        if (!this.mobManager.contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_NOT_EXISTS);
        } else {
            this.mobManager.removeItem(this.mobManager.getItemFromName(name));
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_REMOVED);
        }
    }

    private void editMobCommand(Player player, String name) {
        if (!this.mobManager.getItemKeys().contains(name)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_NOT_EXISTS);
        } else if (this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_CLOSE_OTHER_ARENA_FIRST);
        } else {
            BukkitAreaAPI.BukkitArea area = new BukkitAreaAPI.BukkitArea("tmp" + player.getName(), "", player.getLocation(), player.getLocation().add(50.0, 8.0, 50.0));
            this.arenaManager.arenas.put(player, new MobArena(player, area, new Mob(this.mobManager.getItemFromName(name))));
            this.arenaManager.arenas.get((Object)player).build();
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_CREATE_ARENA);
        }
    }

    private void spawnMobCommand(Player player, String mobType) {
        if (!this.mobManager.contains(mobType) && LivingEntityType.getTypeFromName(mobType) == null) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_NOT_EXISTS);
            BukkitUtilities.u().sendfancyValues(player, this.getLivingEntitys(), "Entitytypes", ChatColor.YELLOW);
        } else if (this.mobManager.contains(mobType)) {
            Mob mob = new Mob(this.mobManager.getItemFromName(mobType));
            this.respawnManager.spawnTmpMob(mob, player.getLocation());
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_SPAWN_CUSTOM_MOB);
        } else {
            Mob mob = new Mob(new MobEquipment("tmp128372", mobType, EntityType.valueOf((String)mobType.toUpperCase())));
            this.respawnManager.spawnTmpMob(mob, player.getLocation());
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_SPAWN_STANDARD_MOB);
        }
    }

    private void stopeditMobCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_DONT_HAVE_OPEN_ARENA);
        } else {
            this.arenaManager.arenas.get((Object)player).saveDelete();
            this.arenaManager.arenas.remove((Object)player);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DELETE_ARENA);
        }
    }

    private void transferCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setPotionEffects(player.getActivePotionEffects().toArray((T[])new PotionEffect[player.getActivePotionEffects().size()]));
            if (player.getInventory().getBoots() != null) {
                mobEquipment.setBoots(player.getInventory().getBoots().clone());
            } else {
                mobEquipment.setBoots(new ItemStack(Material.AIR));
            }
            if (player.getInventory().getLeggings() != null) {
                mobEquipment.setLeggings(player.getInventory().getLeggings().clone());
            } else {
                mobEquipment.setLeggings(new ItemStack(Material.AIR));
            }
            if (player.getInventory().getChestplate() != null) {
                mobEquipment.setChestPlate(player.getInventory().getChestplate().clone());
            } else {
                mobEquipment.setChestPlate(new ItemStack(Material.AIR));
            }
            if (player.getInventory().getHelmet() != null) {
                mobEquipment.setHelmet(player.getInventory().getHelmet().clone());
            } else {
                mobEquipment.setHelmet(new ItemStack(Material.AIR));
            }
            if (player.getItemInHand() != null) {
                try {
                    mobEquipment.setWeapon(player.getItemInHand().clone());
                }
                catch (Exception e) {
                    mobEquipment.setWeapon(new ItemStack(Material.AIR));
                }
            } else {
                mobEquipment.setWeapon(new ItemStack(Material.AIR));
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.arenaManager.arenas.get((Object)player).getMob().equip();
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_TRANSFERED_EFFECTS);
        }
    }

    private void setMobHealthCommand(Player player, String name, double health) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (health < 1.0 || health > 1.0E7) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_HEALTH_HAS_TO_BE);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setHealth(health);
            Mob mob = this.arenaManager.arenas.get((Object)player).getMob();
            mob.update(mobEquipment);
            if (this.arenaManager.arenas.get((Object)player).isShowHealth()) {
                player.sendMessage((Object)ChatColor.AQUA + "[MobArenaInfo]" + " " + (Object)((Object)BukkitChatColor.RED) + "Mobhealth: " + mob.getHealth());
            }
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_HEALTH);
        }
    }

    private void setMobDamageCommand(Player player, String name, double damage) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (damage < 1.0 || damage > 1.0E7) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_DAMAGE_HAS_TO_BE);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setDamage(damage);
            Mob mob = this.arenaManager.arenas.get((Object)player).getMob();
            mob.update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_DAMAGE);
        }
    }

    private void killMobCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobArena arena = this.arenaManager.arenas.get((Object)player);
            arena.getMob().kill();
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ARENA_KILLED);
        }
    }

    private void printMobListCommand(Player player) {
        player.sendMessage((Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.MOB_LIST_MOBS);
        for (BukkitObject object : this.mobManager.getItems()) {
            player.sendMessage((Object)ChatColor.GRAY + object.getName() + " " + object.getDisplayName());
        }
    }

    private void toggleArenaDayLightCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobArena arena = this.arenaManager.arenas.get((Object)player);
            if (!arena.isDayLightburn()) {
                arena.setDayLightburn(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_ARENA_DAYLIGHT);
            } else {
                arena.setDayLightburn(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_ARENA_DAYLIGHT);
            }
        }
    }

    private void toggleArenaDamageableCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobArena arena = this.arenaManager.arenas.get((Object)player);
            if (!arena.isDamageAble()) {
                arena.setDamageAble(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_ARENA_DAMAGEABLE);
            } else {
                arena.setDamageAble(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_ARENA_DAMAGEABLE);
            }
        }
    }

    private void toggleArenaMoveablemageableCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobArena arena = this.arenaManager.arenas.get((Object)player);
            if (!arena.isMoveAble()) {
                arena.setMoveAble(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_ARENA_MOVEABLE);
            } else {
                arena.setMoveAble(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_ARENA_MOVEABLE);
            }
        }
    }

    private void toggleArenaAttackingCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobArena arena = this.arenaManager.arenas.get((Object)player);
            if (!arena.isAttacking()) {
                arena.setAttacking(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_ARENA_ATTACKING);
            } else {
                arena.setAttacking(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_ARENA_ATTACKING);
            }
        }
    }

    private void toggleArenaHealthCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobArena arena = this.arenaManager.arenas.get((Object)player);
            if (!arena.isShowHealth()) {
                arena.setShowHealth(true);
                if (this.arenaManager.arenas.get((Object)player).isShowHealth()) {
                    player.sendMessage((Object)ChatColor.AQUA + "[MobArenaInfo]" + " " + (Object)((Object)BukkitChatColor.RED) + "Mobhealth: " + arena.getMob().getHealth());
                }
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_ARENA_ARENAHEALTH);
            } else {
                arena.setShowHealth(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_ARENA_ARENAHEALTH);
            }
        }
    }

    private void setHelmetCommand(Player player) {
        try {
            player.getInventory().setHelmet(player.getItemInHand());
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_MOVE_ITEM_HELMET);
        }
        catch (NullPointerException e) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_PUT_ITEM_HELMET);
        }
    }

    private void setChestPlateCommand(Player player) {
        try {
            player.getInventory().setChestplate(player.getItemInHand());
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_MOVE_ITEM_CHESTPLATE);
        }
        catch (NullPointerException e) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_PUT_ITEM_CHESTPLATE);
        }
    }

    private void setLeggignsCommand(Player player) {
        try {
            player.getInventory().setLeggings(player.getItemInHand());
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_MOVE_ITEM_LEGGINGS);
        }
        catch (NullPointerException e) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_PUT_ITEM_LEGGINGS);
        }
    }

    private void setBootsCommand(Player player) {
        try {
            player.getInventory().setBoots(player.getItemInHand());
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_MOVE_ITEM_BOOTS);
        }
        catch (NullPointerException e) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_PUT_ITEM_BOOTS);
        }
    }

    private void setHelmetDropChanceCommand(Player player, double percent) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (percent < 0.0 || percent > 100.0) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_PERCENTAGE);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setHelmetDropChance(percent);
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_HELMET_DC);
        }
    }

    private void setChestPlateDropChanceCommand(Player player, double percent) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (percent < 0.0 || percent > 100.0) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_PERCENTAGE);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setChestPlateDropChance(percent);
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_CHESTPLATE_DC);
        }
    }

    private void setLeggignsDropChanceCommand(Player player, double percent) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (percent < 0.0 || percent > 100.0) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_PERCENTAGE);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setLeggingsDropChance(percent);
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_LEGGINGS_DC);
        }
    }

    private void setBootsDropChanceCommand(Player player, double percent) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (percent < 0.0 || percent > 100.0) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_PERCENTAGE);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setBootsDropChance(percent);
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_BOOTS_DC);
        }
    }

    private void setWeaponDropChanceCommand(Player player, double percent) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (percent < 0.0 || percent > 100.0) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_PERCENTAGE);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setWeaponDropChance(percent);
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_WEAPON_DC);
        }
    }

    private void addSkillCommand(Player player, String skillname, double health) {
        AincradRequestSkillEvent requestSkillEvent = new AincradRequestSkillEvent(skillname);
        Cardinal.call().notifySkillSystem(requestSkillEvent);
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (requestSkillEvent.getSkill() == null) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_SKILL_NOT_EXIST);
        } else if (health < 1.0 || health > 1.0E7) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_HEALTH_HAS_TO_BE);
        } else if (this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName()).getSkills().contains(skillname)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_SKILL_ALREADY_KNOW);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.addSkill(health, requestSkillEvent.getSkill());
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_SKILL_ADDED);
        }
    }

    private void removeSkillCommand(Player player, String skillname) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (!this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName()).getSkills().contains(skillname)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_SKILL_NOT_KNOW);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.removeSkill(skillname);
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_SKILL_REMOVED);
        }
    }

    private void printSkillsOnMObCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            player.sendMessage((Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.MOB_SKILL_REGISTERED_SKILLS);
            for (String e : this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName()).getSkills()) {
                player.sendMessage((Object)ChatColor.GRAY + e);
            }
        }
    }

    private void rideMobCommand(Player player, String mobName) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (mobName.equalsIgnoreCase("none")) {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setRidingMob("");
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_CLEARED_RIDING_SPOT);
        } else if (!this.mobManager.getItemKeys().contains(mobName)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_NOT_EXISTS);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setRidingMob(mobName);
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.MOB_RIDES_NOW)[0] + mobName + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.MOB_RIDES_NOW)[1]);
        }
    }

    private void knockBackCommand(Player player, String direction, double amount) {
        direction = direction.toUpperCase();
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (amount < 0.0 || amount > 1000.0) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_KNOCKBACK_LOWER);
        } else if (Direction.getDirection(direction) == null) {
            BukkitUtilities.u().sendfancyValues(player, new String[]{Direction.DOWN.name(), Direction.UP.name(), Direction.LEFT.name(), Direction.RIGHT.name(), Direction.FORWARD.name(), Direction.BACK.name()}, "Launchdirections", ChatColor.YELLOW);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_KNOCKBACK_DIRECTION);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (Direction.getDirection(direction) == Direction.UP) {
                mobEquipment.setKnockBackUP(amount);
            }
            if (Direction.getDirection(direction) == Direction.DOWN) {
                mobEquipment.setKnockBackDOWN(amount);
            }
            if (Direction.getDirection(direction) == Direction.RIGHT) {
                mobEquipment.setKnockBackRIGHT(amount);
            }
            if (Direction.getDirection(direction) == Direction.LEFT) {
                mobEquipment.setKnockBackLEFT(amount);
            }
            if (Direction.getDirection(direction) == Direction.FORWARD) {
                mobEquipment.setKnockBackFORWARD(amount);
            }
            if (Direction.getDirection(direction) == Direction.BACK) {
                mobEquipment.setKnockBackBACK(amount);
            }
            Mob mob = this.arenaManager.arenas.get((Object)player).getMob();
            mob.update(mobEquipment);
            if (this.arenaManager.arenas.get((Object)player).isShowHealth()) {
                player.sendMessage((Object)ChatColor.AQUA + "[MobArenaInfo]" + " " + (Object)((Object)BukkitChatColor.RED) + "Mobhealth: " + mob.getHealth());
            }
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.MOB_UPDATED_KNOCKBACK)[0] + direction + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.MOB_UPDATED_KNOCKBACK)[1]);
        }
    }

    private void fireAttackCommand(Player player, int duration) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (duration < 0 || duration > 1000) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_DURATION_LOWER);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setFireTime(duration);
            Mob mob = this.arenaManager.arenas.get((Object)player).getMob();
            mob.update(mobEquipment);
            if (this.arenaManager.arenas.get((Object)player).isShowHealth()) {
                player.sendMessage((Object)ChatColor.AQUA + "[MobArenaInfo]" + " " + (Object)((Object)BukkitChatColor.RED) + "Mobhealth: " + mob.getHealth());
            }
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_FIREDAMAGE);
        }
    }

    private void toggleDayLightCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (!mobEquipment.isDayLightBurn()) {
                mobEquipment.setDayLightBurn(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_DAYLIGHT);
            } else {
                mobEquipment.setDayLightBurn(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_DAYLIGHT);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleDamageableCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (!mobEquipment.isDamageAble()) {
                mobEquipment.setDamageAble(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_DAMAGEABLE);
            } else {
                mobEquipment.setDamageAble(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_DAMAGEABLE);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleDoesDamageCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (!mobEquipment.isDoesDamage()) {
                mobEquipment.setDoesDamage(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_DAMAGEING);
            } else {
                mobEquipment.setDoesDamage(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_DAMAGEING);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleMoveAbleCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (!mobEquipment.isMoveAble()) {
                mobEquipment.setMoveAble(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_MOVEABLE);
            } else {
                mobEquipment.setMoveAble(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_MOVEABLE);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleAttackingCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (!mobEquipment.isAttacking()) {
                mobEquipment.setAttacking(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_ATTACKING);
            } else {
                mobEquipment.setAttacking(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_ATTACKING);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleHealthCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (!mobEquipment.isShowingHealth()) {
                mobEquipment.setShowingHealth(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_HEALTH);
            } else {
                mobEquipment.setShowingHealth(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_HEALTH);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleDisplayNameCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (!mobEquipment.isShowingName()) {
                mobEquipment.setShowingName(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_DISPLAYNAME);
            } else {
                mobEquipment.setShowingName(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_DISPLAYNAME);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleClassicDropsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (!mobEquipment.isClassicDrops()) {
                mobEquipment.setClassicDrops(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_DROPS);
            } else {
                mobEquipment.setClassicDrops(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_DROPS);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void setSlimeSizeCommand(Player player, int amount) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (amount < 0 || amount > 100) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_SIZE_MATTERS);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setSlimeSize(amount);
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.arenaManager.arenas.get((Object)player).getMob().respawn(this.arenaManager.arenas.get((Object)player).getMob().getLocation());
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_SLIME_SIZE);
        }
    }

    private void setVillageTypeCommand(Player player, String villagerType) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (VillagerType.getVillagerType(villagerType) == null) {
            BukkitUtilities.u().sendfancyValues(player, new String[]{VillagerType.PEASANT.name(), VillagerType.WARRIOR.name(), VillagerType.EWARRIOR.name()}, "Villagertypes", ChatColor.YELLOW);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_VILLAGERTYPE_MATTERS);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setVillagerType(VillagerType.getVillagerType(villagerType));
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_VILLAGERTYPE);
        }
    }

    private void setProfessionCommand(Player player, String villagerProfession) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (VillagerProfession.getVillagerProfession(villagerProfession) == null) {
            BukkitUtilities.u().sendfancyValues(player, new String[]{VillagerProfession.FARMER.name(), VillagerProfession.LIBRARIAN.name(), VillagerProfession.PRIEST.name(), VillagerProfession.BLACKSMITH.name(), VillagerProfession.BUTCHER.name()}, "Villagerprofessions", ChatColor.YELLOW);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_VILLAGERPROFESSION_MATTERS);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setProfession(VillagerProfession.getVillagerProfession(villagerProfession));
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_VILLAGERPROFESSION);
        }
    }

    private void toggleIsBabyCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (mobEquipment.isBaby()) {
                mobEquipment.setBaby(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_BABY);
            } else {
                mobEquipment.setBaby(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_BABY);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleWitherMode(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (mobEquipment.isWitherSkeleton()) {
                mobEquipment.setWitherSkeleton(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_WITHER);
            } else {
                mobEquipment.setWitherSkeleton(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_WITHER);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleCatMode(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (mobEquipment.isCatMode()) {
                mobEquipment.setCatMode(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_CATMODE);
            } else {
                mobEquipment.setCatMode(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_CATMODE);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleDogMode(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (mobEquipment.isDogMode()) {
                mobEquipment.setDogMode(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_DOGMODE);
            } else {
                mobEquipment.setDogMode(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_DOGMODE);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void setCatTypeCommand(Player player, String type) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (CatType.getCatTypeFromName(type) == null) {
            BukkitUtilities.u().sendfancyValues(player, new String[]{CatType.JUNGLE.name(), CatType.BLACK.name(), CatType.WHITE.name(), CatType.YELLOW.name()}, "Cats", ChatColor.YELLOW);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_CATCOLOR_MATTERS);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setCatType(CatType.getCatTypeFromName(type));
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_CATCOLOR);
        }
    }

    private void togglePowered(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (mobEquipment.isPowered()) {
                mobEquipment.setPowered(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_POWERED);
            } else {
                mobEquipment.setPowered(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_POWERED);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void toggleExplosionDestroy(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            if (mobEquipment.isExplosionDestroy()) {
                mobEquipment.setExplosionDestroy(false);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_DISABLED_EXPLOSION_DESTROY);
            } else {
                mobEquipment.setExplosionDestroy(true);
                player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_ENABLED_EXPLOSION_DESTROY);
            }
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.mobManager.save(mobEquipment);
        }
    }

    private void setExplosionradiusCommand(Player player, int value) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_HAVE_TO_EDIT);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_YOU_NOT_IN_ARENA);
        } else if (value < 0 || value > 100) {
            player.sendMessage(String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.MOB_MATTERS_EXPLOSIONRADIUS);
        } else {
            MobEquipment mobEquipment = this.mobManager.getItemFromName(this.arenaManager.arenas.get((Object)player).getMob().getName());
            mobEquipment.setExplosionRadius(value);
            this.arenaManager.arenas.get((Object)player).getMob().update(mobEquipment);
            this.arenaManager.arenas.get((Object)player).getMob().respawn(this.arenaManager.arenas.get((Object)player).getMob().getLocation());
            this.mobManager.save(mobEquipment);
            player.sendMessage(String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.MOB_UPDATED_EXPLOSIONRADIUS);
        }
    }

    private String[] getLivingEntitys() {
        String[] types = new String[LivingEntityType.values().length];
        int i = 0;
        while (i < LivingEntityType.values().length) {
            if (!LivingEntityType.values()[i].entity().name().toUpperCase().contains("MINECART")) {
                types[i] = LivingEntityType.values()[i].entity().name();
            }
            ++i;
        }
        return types;
    }
}

