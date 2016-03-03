/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.skills;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalLanguage;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillArena;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillArenaManager;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.SkillSystem;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Launch;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Teleport;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Direction;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.EffectType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.SkillType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.SlotType;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi.SkillAPICompatibility;
import com.shynixn.thegreatswordartonlinerpg.resources.nms.skillapi.SkillAPICompatibilitySkill;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import libraries.com.darkblade12.particleeffects.ParticleEffect;
import libraries.com.shynixn.utilities.BukkitAreaAPI;
import libraries.com.shynixn.utilities.BukkitChatColor;
import libraries.com.shynixn.utilities.BukkitCommands;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class SkillCommandExecutor
extends BukkitCommands {
    private SkillSystem skillManager;
    private SkillArenaManager arenaManager;

    public SkillCommandExecutor(SkillSystem skillManager, SkillArenaManager arenaManager, JavaPlugin plugin) {
        super("saoskills", plugin);
        this.skillManager = skillManager;
        this.arenaManager = arenaManager;
    }

    @Override
    public void playerSendCommandEvent(Player player, String[] args) {
        if (args.length == 4 && args[0].equalsIgnoreCase("add")) {
            this.createSkillCommand(player, args[1], args[2], args[3]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            this.deleteSkillCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("type")) {
            this.setSkillTypeCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("edit")) {
            this.editSkillCommand(player, args[1]);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("stopedit")) {
            this.stopeditSkillCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("displayname")) {
            this.changeDisplayNameCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            this.getSkillItemCommand(player, args[1]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("giveskillapi")) {
            this.getSkillApiCommand(player, args[1]);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("skills")) {
            this.printSkillsCommand(player);
        } else if (args.length == 4 && args[0].equalsIgnoreCase("addenchant") && BukkitUtilities.u().tryParseInt(args[2])) {
            this.addEnchantmentCommand(player, args[1], Integer.parseInt(args[2]), args[3]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delenchant") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeEnchantmentCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listenchantments")) {
            BukkitUtilities.u().sendfancyValues(player, BukkitUtilities.u().getEnchantmentNames(), "Enchantments", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("enchantments")) {
            this.printEnchantMentsCommand(player);
        } else if (args.length == 5 && args[0].equalsIgnoreCase("addeffect") && BukkitUtilities.u().tryParseInt(args[2]) && BukkitUtilities.u().tryParseInt(args[3]) && BukkitUtilities.u().tryParseInt(args[4])) {
            this.addOwnEffectCommand(player, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("deleffect") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeOwnEffectCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listeffects")) {
            BukkitUtilities.u().sendfancyValues(player, BukkitUtilities.u().getEffectNames(), "Effects", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("effects")) {
            this.printPotionEffectsCommand(player);
        } else if (args.length == 5 && args[0].equalsIgnoreCase("addsound") && BukkitUtilities.u().tryParseDouble(args[2]) && BukkitUtilities.u().tryParseDouble(args[3]) && BukkitUtilities.u().tryParseInt(args[4])) {
            this.addSoundCommand(player, args[1], Integer.parseInt(args[4]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delsound") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeSoundCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listsounds")) {
            String[] tmp = new String[Sound.values().length];
            int i = 0;
            while (i < tmp.length) {
                tmp[i] = Sound.values()[i].toString();
                ++i;
            }
            BukkitUtilities.u().sendfancyValues(player, tmp, "Sounds", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("sounds")) {
            this.printSoundsCommand(player);
        } else if (args.length == 8 && args[0].equalsIgnoreCase("addparticle") && BukkitUtilities.u().tryParseInt(args[2]) && BukkitUtilities.u().tryParseDouble(args[3]) && BukkitUtilities.u().tryParseDouble(args[4]) && BukkitUtilities.u().tryParseDouble(args[5]) && BukkitUtilities.u().tryParseDouble(args[6]) && BukkitUtilities.u().tryParseInt(args[7])) {
            this.addParticleEffectCommand(player, args[1], Integer.parseInt(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]), Integer.parseInt(args[7]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delparticle") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeParticleEffectCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listparticles")) {
            String[] tmp = new String[ParticleEffect.values().length];
            int i = 0;
            while (i < tmp.length) {
                tmp[i] = ParticleEffect.values()[i].toString();
                ++i;
            }
            BukkitUtilities.u().sendfancyValues(player, tmp, "Particles", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("particles")) {
            this.printParticleEffectsCommand(player);
        } else if (args.length == 4 && args[0].equalsIgnoreCase("addlaunch") && BukkitUtilities.u().tryParseDouble(args[2]) && BukkitUtilities.u().tryParseInt(args[3])) {
            this.addLaunchCommand(player, args[1], Double.parseDouble(args[2]), Integer.parseInt(args[3]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("dellaunch") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeLaunchCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listlaunches")) {
            BukkitUtilities.u().sendfancyValues(player, new String[]{Direction.DOWN.name(), Direction.UP.name(), Direction.LEFT.name(), Direction.RIGHT.name(), Direction.FORWARD.name(), Direction.BACK.name()}, "Launchdirections", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("launches")) {
            this.printLaunchesCommand(player);
        } else if (args.length == 4 && args[0].equalsIgnoreCase("addteleport") && BukkitUtilities.u().tryParseInt(args[2]) && BukkitUtilities.u().tryParseInt(args[3])) {
            this.addTeleportingCommand(player, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delteleport") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeTeleportingCommand(player, Integer.parseInt(args[2]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listteleports")) {
            BukkitUtilities.u().sendfancyValues(player, new String[]{Direction.DOWN.name(), Direction.UP.name(), Direction.LEFT.name(), Direction.RIGHT.name(), Direction.FORWARD.name(), Direction.BACK.name()}, "Teleportingdirections", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("teleports")) {
            this.printTeleportingCommand(player);
        } else if (args.length == 4 && args[0].equalsIgnoreCase("addhitsound") && BukkitUtilities.u().tryParseDouble(args[2]) && BukkitUtilities.u().tryParseDouble(args[3])) {
            this.addHitSoundCommand(player, args[1], Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delhitsound") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeHitSoundCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listhitsounds")) {
            String[] tmp = new String[Sound.values().length];
            int i = 0;
            while (i < tmp.length) {
                tmp[i] = Sound.values()[i].toString();
                ++i;
            }
            BukkitUtilities.u().sendfancyValues(player, tmp, "Hitsounds", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("hitsounds")) {
            this.printHitSoundsCommand(player);
        } else if (args.length == 7 && args[0].equalsIgnoreCase("addhitparticle") && BukkitUtilities.u().tryParseInt(args[2]) && BukkitUtilities.u().tryParseDouble(args[3]) && BukkitUtilities.u().tryParseDouble(args[4]) && BukkitUtilities.u().tryParseDouble(args[5]) && BukkitUtilities.u().tryParseDouble(args[6])) {
            this.addHitParticleEffectCommand(player, args[1], Integer.parseInt(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delhitparticle") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeHitParticleEffectCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listhitparticles")) {
            String[] tmp = new String[ParticleEffect.values().length];
            int i = 0;
            while (i < tmp.length) {
                tmp[i] = ParticleEffect.values()[i].toString();
                ++i;
            }
            BukkitUtilities.u().sendfancyValues(player, tmp, "Hitparticles", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("hitparticles")) {
            this.printHitParticleEffectsCommand(player);
        } else if (args.length == 4 && args[0].equalsIgnoreCase("addhiteffect") && BukkitUtilities.u().tryParseInt(args[2]) && BukkitUtilities.u().tryParseInt(args[3])) {
            this.addOwnHitEffectCommand(player, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delhiteffect") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeOwnHitEffectCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listhiteffects")) {
            BukkitUtilities.u().sendfancyValues(player, BukkitUtilities.u().getEffectNames(), "Hiteffects", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("hiteffects")) {
            this.printOwnHitPotionEffectsCommand(player);
        } else if (args.length == 4 && args[0].equalsIgnoreCase("addhitenemyeffect") && BukkitUtilities.u().tryParseInt(args[2]) && BukkitUtilities.u().tryParseInt(args[3])) {
            this.addHitEnemyEffectCommand(player, args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delhitenemyeffect") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.removeHitEnemyEffectCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("listhitenemyeffects")) {
            BukkitUtilities.u().sendfancyValues(player, BukkitUtilities.u().getEffectNames(), "Hitenemyeffects", ChatColor.YELLOW);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("hitenemyeffects")) {
            this.printHitEnemyPotionEffectsCommand(player);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("loading") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.updateLoadingtimeCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("duration") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.updateDurationtimeCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("cooldown") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.updateCooldowntimeCommand(player, Integer.parseInt(args[1]));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            this.toogleskillCommand(player);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("lore") && BukkitUtilities.u().tryParseInt(args[1])) {
            this.setLoreCommand(player, Integer.parseInt(args[1]), args[2]);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("7")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Skills    ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills lore <1/2/3> <text>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills loading <time>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills duration <time>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills cooldown <time>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills toggle");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c7/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equalsIgnoreCase("6")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Skills    ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addhiteffect <effect> <duration> <amplifier>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills delhiteffect <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills hiteffects");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listhiteffects");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addhitenemyeffect <effect> <duration> <amplifier>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills delhitenemyeffect<id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills hitenemyeffects");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listhitenemyeffects");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c6/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equalsIgnoreCase("5")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Skills    ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addhitsound <sound> <volume> <pitch> ");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills delhitsound <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills hitsounds");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listhitsounds");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addhitparticle");
            player.sendMessage((Object)ChatColor.GOLD + "<particle> <count> <speed> <sizex> <sizey> <sizez>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills delhitparticle <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills hitparticles");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listhitparticles");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c5/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equalsIgnoreCase("4")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Skills    ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addlaunch <direction> <amplifier> <delay>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills dellaunch <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills launches");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listlaunches");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addteleport <direction> <blocks> <delay>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills delteleport <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills teleports");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listteleports");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c4/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equalsIgnoreCase("3")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Skills    ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addsound <sound> <volume> <pitch> <delay>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills delsound <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills sounds");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listsounds");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addparticle");
            player.sendMessage((Object)ChatColor.GOLD + "<particle> <count> <speed> <sizex> <sizey> <sizez> <delay>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills delparticle <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills particles");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listparticles");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c3/7\u2510                            ");
            player.sendMessage("");
        } else if (args.length == 1 && args[0].equalsIgnoreCase("2")) {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Skills    ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addenchant <enchantment> <level> <position>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills delenchant <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills enchantments");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listenchantments");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills addeffect <effect> <duration> <amplifier> <delay>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills deleffect <id>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills effects");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills listeffects");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c2/7\u2510                            ");
            player.sendMessage("");
        } else {
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "     The Great " + (Object)((Object)BukkitChatColor.RED) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Sword " + (Object)((Object)BukkitChatColor.GREEN) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Art " + (Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + "Online" + (Object)((Object)BukkitChatColor.AQUA) + (Object)ChatColor.ITALIC + (Object)ChatColor.UNDERLINE + " RPG Plugin: Skills    ");
            player.sendMessage("");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills add <skill>");
            player.sendMessage((Object)ChatColor.GOLD + "<universal/sword/bow/axe> <displayname>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills delete <skill>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills edit <skill>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills stopedit");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills type <universal/sword/bow/axe>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills displayname <displayname>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills give <skill>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills giveskillapi <skill>");
            player.sendMessage(String.valueOf(Cardinal.PREFIX) + "/saoskills skills");
            player.sendMessage("");
            player.sendMessage((Object)((Object)BukkitChatColor.GOLD) + (Object)ChatColor.UNDERLINE + "                           \u250c1/7\u2510                            ");
            player.sendMessage("");
        }
    }

    private void createSkillCommand(Player player, String skillid, String skilltype, String displayname) {
        if (this.skillManager.getItemKeys().contains(skillid)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_ALREADY_EXIST);
        } else if (displayname.length() > 16) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DISPLAYNAME_LONGER);
        } else if (SkillType.getSkillType(skilltype) == null) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_TYPE_NOT_EXIST);
        } else {
            this.skillManager.addItem(new Skill(skillid, displayname, SkillType.getSkillType(skilltype)));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_CREATED);
        }
    }

    private void deleteSkillCommand(Player player, String skillid) {
        if (!this.skillManager.getItemKeys().contains(skillid)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_NOT_EXIST);
        } else {
            this.skillManager.removeItem(this.skillManager.getItemFromName(skillid));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_REMOVED);
        }
    }

    private void editSkillCommand(Player player, String name) {
        if (!this.skillManager.contains(name)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_NOT_EXIST);
        } else if (this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_CLOSE_OTHER_ARENA);
        } else {
            BukkitAreaAPI.BukkitArea area = new BukkitAreaAPI.BukkitArea("tmp" + player.getName(), "", player.getLocation(), player.getLocation().add(50.0, 8.0, 50.0));
            this.arenaManager.arenas.put(player, new SkillArena(player, area, this.skillManager.getItemFromName(name)));
            this.arenaManager.arenas.get((Object)player).build();
            this.arenaManager.arenas.get((Object)player).getItem().remove();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_CREATED_ARENA);
        }
    }

    private void stopeditSkillCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else {
            this.arenaManager.arenas.get((Object)player).saveDelete();
            this.arenaManager.arenas.remove((Object)player);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_CLOSED_SKILL_EDITING_);
        }
    }

    private void setSkillTypeCommand(Player player, String skilltype) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (SkillType.getSkillType(skilltype) == null) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_TYPE_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.setType(SkillType.getSkillType(skilltype));
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_CHANGED_TYPE);
        }
    }

    private void changeDisplayNameCommand(Player player, String displayname) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.setDisplayName(displayname);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_CHANGED_DISPLAYNAME);
        }
    }

    private void getSkillItemCommand(Player player, String skillid) {
        if (!this.skillManager.getItemKeys().contains(skillid)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_NOT_EXIST);
        } else if (BukkitUtilities.u().isEmpty(player.getItemInHand())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_BIND_SKILL);
        } else {
            Skill skill = this.skillManager.getItemFromName(skillid);
            BukkitUtilities.u().nameItem(player.getItemInHand(), skill.getDisplayName(), new String[]{(Object)ChatColor.GREEN + "Skill " + skill.getName(), skill.getLore(0), skill.getLore(1), skill.getLore(2)});
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.SKILLS_ADDED_SKILL)[0] + skillid + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.SKILLS_ADDED_SKILL)[1]);
        }
    }

    private void getSkillApiCommand(Player player, String name) {
        name = name.replace('_', ' ');
        if (Cardinal.getRegistry().getSkillAPIRegistry() == null) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_API_IS_NOT_INSTALLED);
        } else if (Cardinal.getRegistry().getSkillAPIRegistry().getSkill(name) == null) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_API_SKILL_NOT_EXIST);
        } else if (!BukkitUtilities.u().isEmpty(player.getItemInHand())) {
            SkillAPICompatibilitySkill skill = Cardinal.getRegistry().getSkillAPIRegistry().getSkill(name);
            ArrayList<String> lore = new ArrayList<String>();
            lore.add((Object)ChatColor.YELLOW + "SkillAPI " + skill.getName());
            String[] arrstring = skill.getLore();
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                lore.add(s);
                ++n2;
            }
            BukkitUtilities.u().nameItem(player.getItemInHand(), (Object)((Object)BukkitChatColor.YELLOW) + skill.getName(), lore.toArray(new String[lore.size()]));
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.SKILLS_API_SKILL_ADDED)[0] + skill.getName() + CardinalLanguage.fix(CardinalLanguage.CommandExecutor.SKILLS_API_SKILL_ADDED)[1]);
        }
    }

    private void printSkillsCommand(Player player) {
        Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_LIST);
        for (BukkitObject object : this.skillManager.getItems()) {
            Skill skill = (Skill)object;
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + skill.getName() + " " + skill.getDisplayName() + (Object)ChatColor.GRAY + " enabled:" + skill.isEnabled());
        }
    }

    private void addEnchantmentCommand(Player player, String enchantmentname, int level, String position) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (BukkitUtilities.u().getEnchantMentByName(enchantmentname) == null) {
            BukkitUtilities.u().sendfancyValues(player, BukkitUtilities.u().getEnchantmentNames(), "Enchantments", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_ENCHANTMENT_NOT_EXIST);
        } else if (level <= 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_ENCHANTMENT_LEVEL);
        } else if (SlotType.getSlotType(position) == null) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_UNKNOWN_POSITIONS + (Object)((Object)SlotType.BOOTS) + ", " + (Object)((Object)SlotType.LEGGINGS) + ", " + (Object)((Object)SlotType.CHESTPLATE) + ", " + (Object)((Object)SlotType.HELMET) + ", " + (Object)((Object)SlotType.WEAPON) + ".");
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment enchantMentData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment(BukkitUtilities.u().getEnchantMentByName(enchantmentname), level, SlotType.getSlotType(position), 0);
            skill.addEnchantment(skill.getUnusedId(EffectType.Enchantment), enchantMentData);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_ENCHANTMENT_ADDED);
        }
    }

    private void removeEnchantmentCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getEnchantmentIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_ENCHANTMENT_ID_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeEnchantment(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_ENCHANTMENT_REMOVED);
        }
    }

    private void printEnchantMentsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_ENCHANTMENT_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getEnchantmentIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment e = this.arenaManager.arenas.get((Object)player).getSkill().getEnchantmentFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + e.getEnchantment().getName() + " " + e.getLevel() + " " + (Object)((Object)e.getPosition()) + " " + e.getDelay());
            }
        }
    }

    private void addOwnEffectCommand(Player player, String effectname, int duration, int amplifier, int delay) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (BukkitUtilities.u().getEffectByName(effectname) == null) {
            BukkitUtilities.u().sendfancyValues(player, BukkitUtilities.u().getEffectNames(), "Effects", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_NOT_EXIST);
        } else if (duration <= 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_DURATION);
        } else if (amplifier <= 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_AMPLIFIER);
        } else if (delay < 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DELAY);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect potionEffectData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect(new PotionEffect(BukkitUtilities.u().getEffectByName(effectname), duration * 20, amplifier), delay);
            skill.addEffect(skill.getUnusedId(EffectType.potionEffect), potionEffectData);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_ADDED);
        }
    }

    private void removeOwnEffectCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getEffectIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_ID_NOT_EXIT);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeEffect(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_REMOVED);
        }
    }

    private void printPotionEffectsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getEffectIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect e = this.arenaManager.arenas.get((Object)player).getSkill().getEffectFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + e.getEffect().getType().getName() + " " + e.getEffect().getDuration() + " " + e.getEffect().getAmplifier() + " " + e.getDelay());
            }
        }
    }

    private void addSoundCommand(Player player, String sound, int delay, double volume, double pitch) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (BukkitUtilities.u().getSoundByName(sound) == null) {
            String[] tmp = new String[Sound.values().length];
            int i = 0;
            while (i < tmp.length) {
                tmp[i] = Sound.values()[i].toString();
                ++i;
            }
            BukkitUtilities.u().sendfancyValues(player, tmp, "Sounds", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_SOUND_NOT_EXIST);
        } else if (delay < 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DELAY);
        } else if (volume > 2.0 || volume < 0.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_SOUND_VOLUME);
        } else if (pitch > 2.0 || pitch < 0.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_SOUND_PITCH);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound soundData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound(BukkitUtilities.u().getSoundByName(sound), volume, pitch, delay);
            skill.addSound(skill.getUnusedId(EffectType.Sound), soundData);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_SOUND_ADDED);
        }
    }

    private void removeSoundCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getSoundIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_SOUND_ID_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeSound(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_SOUND_REMOVED);
        }
    }

    private void printSoundsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_SOUND_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getSoundIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound e = this.arenaManager.arenas.get((Object)player).getSkill().getSoundFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + e.getSound().toString().toLowerCase() + " " + e.getVolume() + " " + e.getPitch() + " " + e.getDelay());
            }
        }
    }

    private void addParticleEffectCommand(Player player, String particle, int count, double speed, double sizex, double sizey, double sizez, int delay) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (ParticleEffect.getParticelEffectByName(particle) == null) {
            String[] tmp = new String[ParticleEffect.values().length];
            int i = 0;
            while (i < tmp.length) {
                tmp[i] = ParticleEffect.values()[i].toString();
                ++i;
            }
            BukkitUtilities.u().sendfancyValues(player, tmp, "Particles", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_NOT_EXIST);
        } else if (count < 1) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_COUNT);
        } else if (speed < 0.1) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_SPEED);
        } else if (sizex < 1.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_SIZE_X);
        } else if (sizey < 1.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_SIZE_Y);
        } else if (sizez < 1.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_SIZE_Z);
        } else if (delay < 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_DELAY);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect particleEffectData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect(ParticleEffect.getParticelEffectByName(particle), count, speed, sizex, sizey, sizez, delay);
            skill.addParticle(skill.getUnusedId(EffectType.ParticleEffect), particleEffectData);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_ADDED);
        }
    }

    private void removeParticleEffectCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getParticleIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_ID_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeParticle(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_REMOVED);
        }
    }

    private void printParticleEffectsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getParticleIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect e = this.arenaManager.arenas.get((Object)player).getSkill().getParticleFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + e.getEffect().getName() + " " + e.getCount() + " " + e.getSpeed() + " " + e.getSizeX() + " " + e.getSizeY() + " " + e.getSizeZ() + " " + e.getDelay());
            }
        }
    }

    private void addLaunchCommand(Player player, String direction, double amplifier, int delay) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (Direction.getDirection(direction) == null) {
            BukkitUtilities.u().sendfancyValues(player, new String[]{Direction.DOWN.name(), Direction.UP.name(), Direction.LEFT.name(), Direction.RIGHT.name(), Direction.FORWARD.name(), Direction.BACK.name()}, "Launchdirections", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DIRECTION);
        } else if (amplifier <= 0.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_LAUNCH_AMPLIFIER);
        } else if (delay < 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DELAY);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            Launch launch = new Launch(Direction.getDirection(direction), amplifier, delay);
            skill.addLaunch(skill.getUnusedId(EffectType.Launch), launch);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_LAUNCH_ADDED);
        }
    }

    private void removeLaunchCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getLaunchIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_LAUNCH_ID_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeLaunch(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_LAUNCH_REMOVED);
        }
    }

    private void printLaunchesCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_LAUNCH_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getLaunchIds()) {
                Launch e = this.arenaManager.arenas.get((Object)player).getSkill().getLaunchFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + (Object)((Object)e.getDirection()) + " " + e.getAmplifier() + " " + e.getDelay());
            }
        }
    }

    private void addTeleportingCommand(Player player, String direction, int blocks, int delay) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (Direction.getDirection(direction) == null) {
            BukkitUtilities.u().sendfancyValues(player, new String[]{Direction.DOWN.name(), Direction.UP.name(), Direction.LEFT.name(), Direction.RIGHT.name(), Direction.FORWARD.name(), Direction.BACK.name()}, "Teleportingdirections", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DIRECTION);
        } else if (blocks <= 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_TELEPORT_BLOCKS_AMOUNT);
        } else if (delay < 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DELAY);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            Teleport teleport = new Teleport(Direction.getDirection(direction), blocks, delay);
            skill.addTeleport(skill.getUnusedId(EffectType.Teleport), teleport);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_TELEPORT_ADDED);
        }
    }

    private void removeTeleportingCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getTeleportIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_TELEPORT_ID_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeTeleport(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_TELEPORT_REMOVED);
        }
    }

    private void printTeleportingCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_TELEPORT_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getTeleportIds()) {
                Teleport e = this.arenaManager.arenas.get((Object)player).getSkill().getTeleportFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + (Object)((Object)e.getDirection()) + " " + e.getBlockAmount() + " " + e.getDelay());
            }
        }
    }

    private void addHitSoundCommand(Player player, String sound, double volume, double pitch) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (BukkitUtilities.u().getSoundByName(sound) == null) {
            String[] tmp = new String[Sound.values().length];
            int i = 0;
            while (i < tmp.length) {
                tmp[i] = Sound.values()[i].toString();
                ++i;
            }
            BukkitUtilities.u().sendfancyValues(player, tmp, "Hitsounds", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_SOUND_NOT_EXIST);
        } else if (volume > 2.0 || volume < 0.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_SOUND_VOLUME);
        } else if (pitch > 2.0 || pitch < 0.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_SOUND_PITCH);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound soundData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound(BukkitUtilities.u().getSoundByName(sound), volume, pitch, 0);
            skill.addHitSound(skill.getUnusedId(EffectType.HitSound), soundData);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_HITSOUND_ADDED);
        }
    }

    private void removeHitSoundCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getHitSoundIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_HITSOUND_ID_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeHitSound(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_HITSOUND_REMOVED);
        }
    }

    private void printHitSoundsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_HITSOUND_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getHitSoundIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound e = this.arenaManager.arenas.get((Object)player).getSkill().getHitSoundFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + e.getSound().toString().toLowerCase() + " " + e.getVolume() + " " + e.getPitch() + " " + e.getDelay());
            }
        }
    }

    private void addHitParticleEffectCommand(Player player, String particle, int count, double speed, double sizex, double sizey, double sizez) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (ParticleEffect.getParticelEffectByName(particle) == null) {
            String[] tmp = new String[ParticleEffect.values().length];
            int i = 0;
            while (i < tmp.length) {
                tmp[i] = ParticleEffect.values()[i].toString();
                ++i;
            }
            BukkitUtilities.u().sendfancyValues(player, tmp, "Hitparticles", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_NOT_EXIST);
        } else if (count < 1) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_COUNT);
        } else if (speed < 0.1) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_SPEED);
        } else if (sizex < 1.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_SIZE_X);
        } else if (sizey < 1.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_SIZE_Y);
        } else if (sizez < 1.0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_PARTICLE_SIZE_Z);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect particleEffectData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect(ParticleEffect.getParticelEffectByName(particle), count, (double)((float)speed), sizex, sizey, sizez, 0);
            skill.addHitParticle(skill.getUnusedId(EffectType.HitParticle), particleEffectData);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_HITPARTICLE_ADDED);
        }
    }

    private void removeHitParticleEffectCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getHitParticleIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_HITPARTICLE_ID_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeHitParticle(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_HITPARTICLE_REMOVED);
        }
    }

    private void printHitParticleEffectsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_HITPARTICLE_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getHitParticleIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect e = this.arenaManager.arenas.get((Object)player).getSkill().getHitParticleFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + e.getEffect().getName() + " " + e.getCount() + " " + e.getSpeed() + " " + e.getSizeX() + " " + e.getSizeY() + " " + e.getSizeZ() + " " + e.getDelay());
            }
        }
    }

    private void addOwnHitEffectCommand(Player player, String effectname, int duration, int amplifier) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (BukkitUtilities.u().getEffectByName(effectname) == null) {
            BukkitUtilities.u().sendfancyValues(player, BukkitUtilities.u().getEffectNames(), "Effects", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_NOT_EXIST);
        } else if (duration <= 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_DURATION);
        } else if (amplifier <= 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_AMPLIFIER);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect potionEffectData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect(new PotionEffect(BukkitUtilities.u().getEffectByName(effectname), duration * 20, amplifier), 0);
            skill.addHitOwnEffect(skill.getUnusedId(EffectType.HitPotionOwn), potionEffectData);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_HITOWNEFFECT_ADDED);
        }
    }

    private void removeOwnHitEffectCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getHitOwnEffectIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_HITOWNEFFECT_ID_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeHitOwnEffect(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_HITOWNEFFECT_REMOVED);
        }
    }

    private void printOwnHitPotionEffectsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_HITOWNEFFECT_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getHitOwnEffectIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect e = this.arenaManager.arenas.get((Object)player).getSkill().getHitOwnEffectFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + e.getEffect().getType().getName() + " " + e.getEffect().getDuration() + " " + e.getEffect().getAmplifier() + " " + e.getDelay());
            }
        }
    }

    private void addHitEnemyEffectCommand(Player player, String effectname, int duration, int amplifier) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (BukkitUtilities.u().getEffectByName(effectname) == null) {
            BukkitUtilities.u().sendfancyValues(player, BukkitUtilities.u().getEffectNames(), "Effects", ChatColor.YELLOW);
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_NOT_EXIST);
        } else if (duration <= 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_DURATION);
        } else if (amplifier <= 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_EFFECT_AMPLIFIER);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect potionEffectData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect(new PotionEffect(BukkitUtilities.u().getEffectByName(effectname), duration * 20, amplifier), 0);
            skill.addHitEnemyEffect(skill.getUnusedId(EffectType.HitPotionEnemy), potionEffectData);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_HITENEMYEFFECT_ADDED);
        }
    }

    private void removeHitEnemyEffectCommand(Player player, int id) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getSkill().getHitEnemyEffectIds().contains(id)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_HITENEMYEFFECT_ID_NOT_EXIST);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.removeHitEnemyEffect(id);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_HITNEMEYEFFECT_REMOVED);
        }
    }

    private void printHitEnemyPotionEffectsCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Cardinal.getLogger().logPlayer(player, (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + CardinalLanguage.CommandExecutor.SKILLS_HITENEMYEFFECT_LIST);
            for (Integer i : this.arenaManager.arenas.get((Object)player).getSkill().getHitEnemyEffectIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect e = this.arenaManager.arenas.get((Object)player).getSkill().getHitEnemyEffectFromId(i);
                Cardinal.getLogger().logPlayer(player, (Object)ChatColor.GRAY + i + " " + e.getEffect().getType().getName() + " " + e.getEffect().getDuration() + " " + e.getEffect().getAmplifier() + " " + e.getDelay());
            }
        }
    }

    private void updateLoadingtimeCommand(Player player, int time) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (time < 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DURATION);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.setLoadingTime(time);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_UPDATED_LOADING);
        }
    }

    private void updateDurationtimeCommand(Player player, int time) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (time < 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DURATION);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.setExecutingTime(time);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_UPDATED_DURATION);
        }
    }

    private void updateCooldowntimeCommand(Player player, int time) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (time < 0) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_DURATION);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.setCooldDownTime(time);
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_UPDATED_COOLDOWN);
        }
    }

    private void toogleskillCommand(Player player) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            if (!skill.isEnabled()) {
                skill.setSkillEnabled(true);
                Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_ENABLED);
            } else {
                skill.setSkillEnabled(false);
                Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_DISABLED);
            }
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
        }
    }

    private void setLoreCommand(Player player, int lore, String text) {
        if (!this.arenaManager.arenas.containsKey((Object)player)) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA);
        } else if (!this.arenaManager.arenas.get((Object)player).getArea().isLocationInArea(player.getLocation())) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_YOU_NOT_IN_ARENA);
        } else if (lore != 3 && lore != 2 && lore != 1) {
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_ERROR) + CardinalLanguage.CommandExecutor.SKILLS_LORE);
        } else {
            Skill skill = this.arenaManager.arenas.get((Object)player).getSkill();
            skill.setLore(lore - 1, ChatColor.translateAlternateColorCodes((char)'&', (String)text));
            this.skillManager.save(skill);
            this.arenaManager.arenas.get((Object)player).refresh();
            Cardinal.getLogger().logPlayer(player, String.valueOf(Cardinal.PREFIX_SUCCESS) + CardinalLanguage.CommandExecutor.SKILLS_UPDATED_LORE);
        }
    }
}

