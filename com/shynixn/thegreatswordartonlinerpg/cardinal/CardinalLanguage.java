/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.shynixn.thegreatswordartonlinerpg.cardinal;

import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CardinalLanguage {
    private static CardinalLanguage instance;
    private JavaPlugin plugin;

    protected static CardinalLanguage getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new CardinalLanguage(plugin);
        }
        return instance;
    }

    private CardinalLanguage(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static String[] fix(String text) {
        ArrayList<String> newText = new ArrayList<String>();
        String tmp = "";
        boolean triggered = false;
        int i = 0;
        while (i < text.length()) {
            if (text.charAt(i) == '<') {
                triggered = true;
                newText.add(tmp);
            } else if (text.charAt(i) == '>') {
                triggered = false;
                tmp = "";
            } else if (!triggered) {
                tmp = String.valueOf(tmp) + text.charAt(i);
            }
            ++i;
        }
        newText.add(tmp);
        return newText.toArray(new String[newText.size()]);
    }

    public void reload() throws Exception {
        File engfile = new File(this.plugin.getDataFolder() + "/lang", "ENG.yml");
        File realfile = new File(this.plugin.getDataFolder() + "/lang", String.valueOf(this.plugin.getConfig().getString("language")) + ".yml");
        try {
            Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "====================================================");
            Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "################  " + (Object)ChatColor.AQUA + "LOADING LANGUAGE" + (Object)ChatColor.WHITE + "  ################");
            Bukkit.getServer().getConsoleSender().sendMessage((Object)ChatColor.WHITE + "====================================================");
            if (!engfile.exists()) {
                BukkitUtilities.u().copyFile(this.plugin.getResource("ENG.yml"), engfile);
            } else if (!realfile.exists()) {
                throw new CardinalException("Language file " + realfile.getName() + " does not exist.", "Plugin cannot run correctly", "Change the selected language in the config.yml", Priority.HIGH);
            }
            YamlConfiguration config = new YamlConfiguration();
            config.load(realfile);
            Items.DISPLAYNAME_SCOREBOARD = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-scoreboard"));
            Items.LORE_SCOREBOARD = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-scoreboard"));
            Items.DISPLAYNAME_SOCIAL = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-social"));
            Items.LORE_SOCIAL = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-social"));
            Items.DISPLAYNAME_EQUIPMENT = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-equipment"));
            Items.LORE_EQUIPMENT = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-equipment"));
            Items.DISPLAYNAME_SKILLS = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-skills"));
            Items.LORE_SKILLS = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-skills"));
            Items.DISPLAYNAME_EXIT = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-exit"));
            Items.LORE_EXIT = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-exit"));
            Items.DISPLAYNAME_NERVEGEAR = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-nervegear"));
            Items.LORE_NERVEGEAR = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-nervegear"));
            Items.DISPLAYNAME_LOGOUT = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-logout"));
            Items.LORE_LOGOUT = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-logout"));
            Items.DISPLAYNAME_MENU = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-menu"));
            Items.LORE_MENU = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-menu"));
            Items.DISPLAYNAME_SIGNSELECTOR = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-sign"));
            Items.LORE_SIGNSELECTOR = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-sign"));
            Items.DISPLAYNAME_NEXT = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-next"));
            Items.LORE_NEXT = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-next"));
            Items.DISPLAYNAME_SKILLAPI = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-skillapi"));
            Items.LORE_SKILLAPI = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-skillapi"));
            Items.DISPLAYNAME_PETSAPI = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-petsapi"));
            Items.LORE_PETSAPI = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-petsapi"));
            Items.DISPLAYNAME_EQUIPMENT_ARMOR = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-equipment-armor"));
            Items.LORE_EQUIPMENT_ARMOR = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-equipment-armor"));
            Items.DISPLAYNAME_EQUIPMENT_DROPS = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-equipment-drops"));
            Items.LORE_EQUIPMENT_DROPS = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-equipment-drops"));
            Items.DISPLAYNAME_EQUIPMENT_FOOD = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-equipment-food"));
            Items.LORE_EQUIPMENT_FOOD = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-equipment-food"));
            Items.DISPLAYNAME_EQUIPMENT_MATERIALS = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-equipment-material"));
            Items.LORE_EQUIPMENT_MATERIALS = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-equipment-material"));
            Items.DISPLAYNAME_EQUIPMENT_WEAPON = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.display-equipment-weapon"));
            Items.LORE_EQUIPMENT_WEAPON = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("items.lore-equipment-weapon"));
            Skill.SKILL_ACTIVATED = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("skills.skill-activated"));
            Skill.SKILL_DEACTIVATED = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("skills.skill-deactivated"));
            Skill.SKILL_COOLDOWN = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("skills.skill-cooldown"));
            Skill.SKILL_DISABLED = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("skills.skill-disabled"));
            Skill.SKILL_WRONG_TYPE = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("skills.skill-wrong-type"));
            Skill.SKILL_LOADING = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("skills.skill-loading"));
            Cardinal.TYPE_SAO_BACK = config.getString("cardinal.type-sao-back");
            Cardinal.YOU_ARE_DEAD_FOREVER = config.getString("cardinal.you-dead-forever");
            Cardinal.YOU_ARE_DEAD_RESPAWN = config.getString("cardinal.you-dead-respawn");
            Cardinal.SET_LOGOUT_LOCATION = config.getString("cardinal.update-logout-location");
            Cardinal.LIST_BLACKLIST = config.getString("cardinal.blacklist-list");
            Cardinal.LIST_EXCEPTIONS = config.getString("cardinal.exceptions-list");
            Cardinal.CLEAR_BLACKLIST = config.getString("cardinal.blacklist-clear");
            Cardinal.CLEAR_EXCEPTIONS = config.getString("cardinal.exceptions-clear");
            Cardinal.SKILL_DETECTED = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("cardinal.learn-skill"));
            Cardinal.LINK_LOADING = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("cardinal.link-loading"));
            Cardinal.NEXT_FLOOR_NOT_EXIST = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("cardinal.next-floor-exist"));
            Cardinal.PREVIOUS_FLOOR_NOT_EXIST = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("cardinal.previous-floor-exist"));
            Cardinal.NEXT_FLOOR_NO_PERMS = ChatColor.translateAlternateColorCodes((char)'&', (String)config.getString("cardinal.next-floor-no-perms"));
            CommandExecutor.DEVELOPER_REMOVE_ALL_ENTITIES_SUCCESS = config.getString("commands.developer.developer-remove-all-entities");
            CommandExecutor.DEVELOPER_REMOVE_NEXT_ENTITY_SUCCESS = config.getString("commands.developer.developer-remove-next-entity");
            CommandExecutor.DEVELOPER_CRITICAL_DAMAGE_WARNING = config.getString("commands.developer.developer-critical-damage-warning");
            CommandExecutor.FLOOR_FLOOR_NOT_EXIST = config.getString("commands.floors.floor-not-exist");
            CommandExecutor.FLOOR_FLOOR_EXISTS = config.getString("commands.floors.floor-exists");
            CommandExecutor.FLOOR_SET_NEXT_PORTAL = config.getString("commands.floors.floor-next-portal");
            CommandExecutor.FLOOR_SET_PREVIOUS_PORTAL = config.getString("commands.floors.floor-previous-portal");
            CommandExecutor.FLOOR_FLOOR_CREATED = config.getString("commands.floors.floor-create");
            CommandExecutor.FLOOR_FLOOR_REMOVED = config.getString("commands.floors.floor-remove");
            CommandExecutor.FLOOR_FINISH_MESSAGE = config.getString("commands.floors.floor-finish-field");
            CommandExecutor.FLOOR_LIST_FLOORS = config.getString("commands.floors.floor-list");
            CommandExecutor.FLOOR_SHOW_FLOOR = config.getString("commands.floors.floor-show");
            CommandExecutor.FLOOR_SET_BOSS = config.getString("commands.floors.floor-boss");
            CommandExecutor.RACES_RACE_NOT_EXIST = config.getString("commands.races.race-not-exist");
            CommandExecutor.RACES_RACE_EXISTS = config.getString("commands.races.race-exists");
            CommandExecutor.RACE_SET_SPAWNPOINT = config.getString("commands.races.race-set-spawnpoint");
            CommandExecutor.RACE_LIST_RACES = config.getString("commands.races.race-list");
            CommandExecutor.RACES_RACE_CREATED = config.getString("commands.races.race-create");
            CommandExecutor.RACES_RACE_REMOVED = config.getString("commands.races.race-remove");
            CommandExecutor.MOB_ENTITYTYPE_NOT_EXIST = config.getString("commands.mobs.mob-entity-not-exist");
            CommandExecutor.MOB_ALREADY_EXISTS = config.getString("commands.mobs.mob-exists");
            CommandExecutor.MOB_NOT_EXISTS = config.getString("commands.mobs.mob-not-exist");
            CommandExecutor.MOB_ADDED = config.getString("commands.mobs.mob-added");
            CommandExecutor.MOB_REMOVED = config.getString("commands.mobs.mob-removed");
            CommandExecutor.MOB_CLOSE_OTHER_ARENA_FIRST = config.getString("commands.mobs.mob-close-other-arena");
            CommandExecutor.MOB_CREATE_ARENA = config.getString("commands.mobs.mob-create-arena");
            CommandExecutor.MOB_SPAWN_CUSTOM_MOB = config.getString("commands.mobs.mob-spawn-custommob");
            CommandExecutor.MOB_SPAWN_STANDARD_MOB = config.getString("commands.mobs.mob-spawn-standardmob");
            CommandExecutor.MOB_YOU_DONT_HAVE_OPEN_ARENA = config.getString("commands.mobs.mob-arena-not-open");
            CommandExecutor.MOB_DELETE_ARENA = config.getString("commands.mobs.mob-delete-arena");
            CommandExecutor.MOB_YOU_HAVE_TO_EDIT = config.getString("commands.mobs.mob-are-not-editing");
            CommandExecutor.MOB_YOU_NOT_IN_ARENA = config.getString("commands.mobs.mob-are-not-arena");
            CommandExecutor.MOB_TRANSFERED_EFFECTS = config.getString("commands.mobs.mob-transfered");
            CommandExecutor.MOB_HEALTH_HAS_TO_BE = config.getString("commands.mobs.mob-health-hast-to-be");
            CommandExecutor.MOB_DAMAGE_HAS_TO_BE = config.getString("commands.mobs.mob-damage-has-to-be");
            CommandExecutor.MOB_PERCENTAGE = config.getString("commands.mobs.mob-pecentage");
            CommandExecutor.MOB_UPDATED_HEALTH = config.getString("commands.mobs.mob-updated-health");
            CommandExecutor.MOB_UPDATED_DAMAGE = config.getString("commands.mobs.mob-updated-damage");
            CommandExecutor.MOB_ARENA_KILLED = config.getString("commands.mobs.mob-arena-killed");
            CommandExecutor.MOB_LIST_MOBS = config.getString("commands.mobs.mob-list");
            CommandExecutor.MOB_ENABLED_ARENA_DAYLIGHT = config.getString("commands.mobs.mob-enable-arena-daylight");
            CommandExecutor.MOB_DISABLED_ARENA_DAYLIGHT = config.getString("commands.mobs.mob-disable-arena-daylight");
            CommandExecutor.MOB_ENABLED_ARENA_DAMAGEABLE = config.getString("commands.mobs.mob-enable-arena-damageable");
            CommandExecutor.MOB_DISABLED_ARENA_DAMAGEABLE = config.getString("commands.mobs.mob-disable-arena-damageable");
            CommandExecutor.MOB_ENABLED_ARENA_MOVEABLE = config.getString("commands.mobs.mob-enable-arena-moveable");
            CommandExecutor.MOB_DISABLED_ARENA_MOVEABLE = config.getString("commands.mobs.mob-disable-arena-moevable");
            CommandExecutor.MOB_ENABLED_ARENA_ATTACKING = config.getString("commands.mobs.mob-enable-arena-attacking");
            CommandExecutor.MOB_DISABLED_ARENA_ATTACKING = config.getString("commands.mobs.mob-disable-arena-attacking");
            CommandExecutor.MOB_ENABLED_ARENA_ARENAHEALTH = config.getString("commands.mobs.mob-enable-arena-health");
            CommandExecutor.MOB_DISABLED_ARENA_ARENAHEALTH = config.getString("commands.mobs.mob-disable-arena-health");
            CommandExecutor.MOB_MOVE_ITEM_HELMET = config.getString("commands.mobs.mob-put-helmet");
            CommandExecutor.MOB_PUT_ITEM_HELMET = config.getString("commands.mobs.mob-move-helmet");
            CommandExecutor.MOB_MOVE_ITEM_CHESTPLATE = config.getString("commands.mobs.mob-move-chestplate");
            CommandExecutor.MOB_PUT_ITEM_CHESTPLATE = config.getString("commands.mobs.mob-put-chestplate");
            CommandExecutor.MOB_MOVE_ITEM_LEGGINGS = config.getString("commands.mobs.mob-move-leggings");
            CommandExecutor.MOB_PUT_ITEM_LEGGINGS = config.getString("commands.mobs.mob-put-leggings");
            CommandExecutor.MOB_MOVE_ITEM_BOOTS = config.getString("commands.mobs.mob-move-boots");
            CommandExecutor.MOB_PUT_ITEM_BOOTS = config.getString("commands.mobs.mob-put-boots");
            CommandExecutor.MOB_UPDATED_HELMET_DC = config.getString("commands.mobs.mob-update-helmet-dc");
            CommandExecutor.MOB_UPDATED_CHESTPLATE_DC = config.getString("commands.mobs.mob-update-chestplate-dc");
            CommandExecutor.MOB_UPDATED_LEGGINGS_DC = config.getString("commands.mobs.mob-update-leggings-dc");
            CommandExecutor.MOB_UPDATED_BOOTS_DC = config.getString("commands.mobs.mob-update-boots-dc");
            CommandExecutor.MOB_UPDATED_WEAPON_DC = config.getString("commands.mobs.mob-update-weapon-dc");
            CommandExecutor.MOB_SKILL_NOT_EXIST = config.getString("commands.mobs.mob-skills-not-exist");
            CommandExecutor.MOB_SKILL_ALREADY_KNOW = config.getString("commands.mobs.mob-skills-already-know");
            CommandExecutor.MOB_SKILL_NOT_KNOW = config.getString("commands.mobs.mob-skills-not-know");
            CommandExecutor.MOB_SKILL_ADDED = config.getString("commands.mobs.mob-skills-added");
            CommandExecutor.MOB_SKILL_REMOVED = config.getString("commands.mobs.mob-skills-removed");
            CommandExecutor.MOB_SKILL_REGISTERED_SKILLS = config.getString("commands.mobs.mob-skills-list");
            CommandExecutor.MOB_CLEARED_RIDING_SPOT = config.getString("commands.mobs.mob-riding-clear");
            CommandExecutor.MOB_RIDES_NOW = config.getString("commands.mobs.mob-rides");
            CommandExecutor.MOB_KNOCKBACK_LOWER = config.getString("commands.mobs.mob-knockback-has-to-be");
            CommandExecutor.MOB_KNOCKBACK_DIRECTION = config.getString("commands.mobs.mob-knockback-direction");
            CommandExecutor.MOB_UPDATED_KNOCKBACK = config.getString("commands.mobs.mob-knockback-updated");
            CommandExecutor.MOB_DURATION_LOWER = config.getString("commands.mobs.mob-duration");
            CommandExecutor.MOB_UPDATED_FIREDAMAGE = config.getString("commands.mobs.mob-update-fire");
            CommandExecutor.MOB_ENABLED_DAYLIGHT = config.getString("commands.mobs.mob-enable-daylightburn");
            CommandExecutor.MOB_DISABLED_DAYLIGHT = config.getString("commands.mobs.mob-disable-daylightburn");
            CommandExecutor.MOB_ENABLED_DAMAGEABLE = config.getString("commands.mobs.mob-enable-damageable");
            CommandExecutor.MOB_DISABLED_DAMAGEABLE = config.getString("commands.mobs.mob-disable-damageable");
            CommandExecutor.MOB_ENABLED_DAMAGEING = config.getString("commands.mobs.mob-enable-damaging");
            CommandExecutor.MOB_DISABLED_DAMAGEING = config.getString("commands.mobs.mob-disable-damaging");
            CommandExecutor.MOB_ENABLED_MOVEABLE = config.getString("commands.mobs.mob-enable-moving");
            CommandExecutor.MOB_DISABLED_MOVEABLE = config.getString("commands.mobs.mob-disable-moving");
            CommandExecutor.MOB_ENABLED_ATTACKING = config.getString("commands.mobs.mob-enable-attacking");
            CommandExecutor.MOB_DISABLED_ATTACKING = config.getString("commands.mobs.mob-disable-attacking");
            CommandExecutor.MOB_ENABLED_HEALTH = config.getString("commands.mobs.mob-enable-health");
            CommandExecutor.MOB_DISABLED_HEALTH = config.getString("commands.mobs.mob-disable-health");
            CommandExecutor.MOB_ENABLED_DISPLAYNAME = config.getString("commands.mobs.mob-enable-displayname");
            CommandExecutor.MOB_DISABLED_DISPLAYNAME = config.getString("commands.mobs.mob-disable-displayname");
            CommandExecutor.MOB_ENABLED_DROPS = config.getString("commands.mobs.mob-enable-drops");
            CommandExecutor.MOB_DISABLED_DROPS = config.getString("commands.mobs.mob-disable-drops");
            CommandExecutor.MOB_SIZE_MATTERS = config.getString("commands.mobs.mob-slime-size-is");
            CommandExecutor.MOB_UPDATED_SLIME_SIZE = config.getString("commands.mobs.mob-slime-update");
            CommandExecutor.MOB_VILLAGERTYPE_MATTERS = config.getString("commands.mobs.mob-villagertype-is");
            CommandExecutor.MOB_UPDATED_VILLAGERTYPE = config.getString("commands.mobs.mob-villagertype-update");
            CommandExecutor.MOB_VILLAGERPROFESSION_MATTERS = config.getString("commands.mobs.mob-villagerprofession-is");
            CommandExecutor.MOB_UPDATED_VILLAGERPROFESSION = config.getString("commands.mobs.mob-villagerprofession-update");
            CommandExecutor.MOB_ENABLED_BABY = config.getString("commands.mobs.mob-enable-babymode");
            CommandExecutor.MOB_DISABLED_BABY = config.getString("commands.mobs.mob-disable-babymode");
            CommandExecutor.MOB_ENABLED_WITHER = config.getString("commands.mobs.mob-enable-withermode");
            CommandExecutor.MOB_DISABLED_WITHER = config.getString("commands.mobs.mob-disable-withermode");
            CommandExecutor.MOB_ENABLED_CATMODE = config.getString("commands.mobs.mob-enable-catmode");
            CommandExecutor.MOB_DISABLED_CATMODE = config.getString("commands.mobs.mob-disable-catmode");
            CommandExecutor.MOB_ENABLED_DOGMODE = config.getString("commands.mobs.mob-enable-dogmode");
            CommandExecutor.MOB_DISABLED_DOGMODE = config.getString("commands.mobs.mob-disable-dogmode");
            CommandExecutor.MOB_CATCOLOR_MATTERS = config.getString("commands.mobs.mob-catcolor-is");
            CommandExecutor.MOB_UPDATED_CATCOLOR = config.getString("commands.mobs.mob-catcolor-update");
            CommandExecutor.MOB_ENABLED_POWERED = config.getString("commands.mobs.mob-enable-powered");
            CommandExecutor.MOB_DISABLED_POWERED = config.getString("commands.mobs.mob-disable-powered");
            CommandExecutor.MOB_ENABLED_EXPLOSION_DESTROY = config.getString("commands.mobs.mob-enable-destroy");
            CommandExecutor.MOB_DISABLED_EXPLOSION_DESTROY = config.getString("commands.mobs.mob-disable-destroy");
            CommandExecutor.MOB_MATTERS_EXPLOSIONRADIUS = config.getString("commands.mobs.mob-explosionradius-is");
            CommandExecutor.MOB_UPDATED_EXPLOSIONRADIUS = config.getString("commands.mobs.mob-explosionradius-update");
            CommandExecutor.MOB_WINGS_NOT_EXIST = config.getString("commands.mobs.mob-wings-not-exist");
            CommandExecutor.MOB_UPDATED_WINGS = config.getString("commands.mobs.mob-updated-wings");
            CommandExecutor.MOB_ENABLED_WINGS = config.getString("commands.mobs.mob-enabled-wings");
            CommandExecutor.MOB_DISABLED_WINGS = config.getString("commands.mobs.mob-disabled-wings");
            CommandExecutor.MOB_FLYDIRECTION_NOT_EXIST = config.getString("commands.mobs.mob-fly-not-exist");
            CommandExecutor.MOB_UPDATED_MIN_FLYING = config.getString("commands.mobs.mob-updated-min-flying");
            CommandExecutor.MOB_UPDATED_MAX_FLYING = config.getString("commands.mobs.mob-updated-max-flying");
            CommandExecutor.MOB_ENABLED_FLYING = config.getString("commands.mobs.mob-enabled-flying");
            CommandExecutor.MOB_DISABLED_FLYING = config.getString("commands.mobs.mob-disabled-flying");
            CommandExecutor.RESPAWN_SPAWN_NOT_EXIST = config.getString("commands.respawn.respawn-not-exist");
            CommandExecutor.RESPAWN_SPAWN_ALREADY_EXIST = config.getString("commands.respawn.respawn-exists");
            CommandExecutor.RESPAWN_AMOUNT_SMALLER = config.getString("commands.respawn.respawn-amount-10000000");
            CommandExecutor.RESPAWN_AMOUNT_SMALLER2 = config.getString("commands.respawn.respawn-amount-1000");
            CommandExecutor.RESPAWN_UPDATE_MAXDESPAWN = config.getString("commands.respawn.respawn-updated-maxdespawndelay");
            CommandExecutor.RESPAWN_UPDATE_MAXRESPAWN = config.getString("commands.respawn.respawn-updated-maxrespawndelay");
            CommandExecutor.RESPAWN_ENABLED = config.getString("commands.respawn.respawn-enabled");
            CommandExecutor.RESPAWN_DISABLED = config.getString("commands.respawn.respawn-disabled");
            CommandExecutor.RESPAWN_LIST = config.getString("commands.respawn.respawn-list");
            CommandExecutor.RESPAWN_REMOVE = config.getString("commands.respawn.respawn-removed");
            CommandExecutor.RESPAWN_ADDED = config.getString("commands.respawn.respawn-added");
            CommandExecutor.RESPAWN_MOB_NOT_EXIST = config.getString("commands.respawn.respawn-mob-not-exist");
            CommandExecutor.RESPAWN_RADIUS_X = config.getString("commands.respawn.respawn-lower-x");
            CommandExecutor.RESPAWN_RADIUS_Y = config.getString("commands.respawn.respawn-lower-y");
            CommandExecutor.RESPAWN_RADIUS_Z = config.getString("commands.respawn.respawn-lower-z");
            CommandExecutor.RESPAWN_RADIUS_D = config.getString("commands.respawn.respawn-lower-d");
            CommandExecutor.SKILLS_ALREADY_EXIST = config.getString("commands.skills.skill-exists");
            CommandExecutor.SKILLS_DISPLAYNAME_LONGER = config.getString("commands.skills.skill-displayname-longer");
            CommandExecutor.SKILLS_TYPE_NOT_EXIST = config.getString("commands.skills.skill-type-not-exist");
            CommandExecutor.SKILLS_CREATED = config.getString("commands.skills.skill-created");
            CommandExecutor.SKILLS_NOT_EXIST = config.getString("commands.skills.skill-not-exist");
            CommandExecutor.SKILLS_REMOVED = config.getString("commands.skills.skill-removed");
            CommandExecutor.SKILLS_CLOSE_OTHER_ARENA = config.getString("commands.skills.skill-close-other-arena");
            CommandExecutor.SKILLS_CREATED_ARENA = config.getString("commands.skills.skill-created-arena");
            CommandExecutor.SKILLS_YOU_DONT_HAVE_OPEN_ARENA = config.getString("commands.skills.skill-not-open-arena");
            CommandExecutor.SKILLS_CLOSED_SKILL_EDITING_ = config.getString("commands.skills.skill-closed-arena");
            CommandExecutor.SKILLS_YOU_NOT_IN_ARENA = config.getString("commands.skills.skill-you-not-arena");
            CommandExecutor.SKILLS_CHANGED_TYPE = config.getString("commands.skills.skill-changed-type");
            CommandExecutor.SKILLS_CHANGED_DISPLAYNAME = config.getString("commands.skills.skill-changed-display");
            CommandExecutor.SKILLS_BIND_SKILL = config.getString("commands.skills.skill-bind");
            CommandExecutor.SKILLS_ADDED_SKILL = config.getString("commands.skills.skill-added-skill");
            CommandExecutor.SKILLS_API_IS_NOT_INSTALLED = config.getString("commands.skills.skill-api-not-installed");
            CommandExecutor.SKILLS_API_SKILL_NOT_EXIST = config.getString("commands.skills.skill-api-not-exist");
            CommandExecutor.SKILLS_API_SKILL_ADDED = config.getString("commands.skills.skill-api-added-skill");
            CommandExecutor.SKILLS_LIST = config.getString("commands.skills.skill-list");
            CommandExecutor.SKILLS_ENCHANTMENT_NOT_EXIST = config.getString("commands.skills.skill-enchantment-not-exist");
            CommandExecutor.SKILLS_ENCHANTMENT_LEVEL = config.getString("commands.skills.skill-enchantment-level");
            CommandExecutor.SKILLS_UNKNOWN_POSITIONS = config.getString("commands.skills.skill-enchantment-position");
            CommandExecutor.SKILLS_ENCHANTMENT_ADDED = config.getString("commands.skills.skill-enchantment-added");
            CommandExecutor.SKILLS_ENCHANTMENT_ID_NOT_EXIST = config.getString("commands.skills.skill-enchantment-id-not-exist");
            CommandExecutor.SKILLS_ENCHANTMENT_REMOVED = config.getString("commands.skills.skill-enchantment-removed");
            CommandExecutor.SKILLS_ENCHANTMENT_LIST = config.getString("commands.skills.skill-enchantment-list");
            CommandExecutor.SKILLS_EFFECT_NOT_EXIST = config.getString("commands.skills.skill-effect-not-exist");
            CommandExecutor.SKILLS_EFFECT_DURATION = config.getString("commands.skills.skill-effect-duration");
            CommandExecutor.SKILLS_EFFECT_AMPLIFIER = config.getString("commands.skills.skill-effect-amplifier");
            CommandExecutor.SKILLS_DELAY = config.getString("commands.skills.skill-delay");
            CommandExecutor.SKILLS_EFFECT_ADDED = config.getString("commands.skills.skill-effect-added");
            CommandExecutor.SKILLS_EFFECT_ID_NOT_EXIT = config.getString("commands.skills.skill-effect-id-not-exist");
            CommandExecutor.SKILLS_EFFECT_REMOVED = config.getString("commands.skills.skill-effect-removed");
            CommandExecutor.SKILLS_EFFECT_LIST = config.getString("commands.skills.skill-effect-list");
            CommandExecutor.SKILLS_SOUND_NOT_EXIST = config.getString("commands.skills.skill-sound-not-exist");
            CommandExecutor.SKILLS_SOUND_VOLUME = config.getString("commands.skills.skill-sound-volume");
            CommandExecutor.SKILLS_SOUND_PITCH = config.getString("commands.skills.skill-sound-pitch");
            CommandExecutor.SKILLS_SOUND_ADDED = config.getString("commands.skills.skill-sound-added");
            CommandExecutor.SKILLS_SOUND_ID_NOT_EXIST = config.getString("commands.skills.skill-sound-id-not-exist");
            CommandExecutor.SKILLS_SOUND_REMOVED = config.getString("commands.skills.skill-sound-removed");
            CommandExecutor.SKILLS_SOUND_LIST = config.getString("commands.skills.skill-sound-list");
            CommandExecutor.SKILLS_PARTICLE_NOT_EXIST = config.getString("commands.skills.skill-particle-not-exist");
            CommandExecutor.SKILLS_PARTICLE_COUNT = config.getString("commands.skills.skill-particle-count");
            CommandExecutor.SKILLS_PARTICLE_SPEED = config.getString("commands.skills.skill-particle-speed");
            CommandExecutor.SKILLS_PARTICLE_SIZE_X = config.getString("commands.skills.skill-particle-size-x");
            CommandExecutor.SKILLS_PARTICLE_SIZE_Y = config.getString("commands.skills.skill-particle-size-y");
            CommandExecutor.SKILLS_PARTICLE_SIZE_Z = config.getString("commands.skills.skill-particle-size-z");
            CommandExecutor.SKILLS_PARTICLE_DELAY = config.getString("commands.skills.skill-particle-delay");
            CommandExecutor.SKILLS_PARTICLE_ADDED = config.getString("commands.skills.skill-particle-added");
            CommandExecutor.SKILLS_PARTICLE_REMOVED = config.getString("commands.skills.skill-particle-removed");
            CommandExecutor.SKILLS_PARTICLE_ID_NOT_EXIST = config.getString("commands.skills.skill-particle-id-not-exist");
            CommandExecutor.SKILLS_PARTICLE_LIST = config.getString("commands.skills.skill-particle-list");
            CommandExecutor.SKILLS_DIRECTION = config.getString("commands.skills.skill-direction");
            CommandExecutor.SKILLS_LAUNCH_AMPLIFIER = config.getString("commands.skills.skill-launch-amplifier");
            CommandExecutor.SKILLS_LAUNCH_ADDED = config.getString("commands.skills.skill-launch-added");
            CommandExecutor.SKILLS_LAUNCH_ID_NOT_EXIST = config.getString("commands.skills.skill-launch-id-not-exist");
            CommandExecutor.SKILLS_LAUNCH_LIST = config.getString("commands.skills.skill-launch-list");
            CommandExecutor.SKILLS_LAUNCH_REMOVED = config.getString("commands.skills.skill-launch-removed");
            CommandExecutor.SKILLS_TELEPORT_BLOCKS_AMOUNT = config.getString("commands.skills.skill-teleport-block-amount");
            CommandExecutor.SKILLS_TELEPORT_ADDED = config.getString("commands.skills.skill-teleport-added");
            CommandExecutor.SKILLS_TELEPORT_ID_NOT_EXIST = config.getString("commands.skills.skill-teleport-id-not-exist");
            CommandExecutor.SKILLS_TELEPORT_REMOVED = config.getString("commands.skills.skill-teleport-removed");
            CommandExecutor.SKILLS_TELEPORT_LIST = config.getString("commands.skills.skill-teleport-list");
            CommandExecutor.SKILLS_HITSOUND_ADDED = config.getString("commands.skills.skill-hitsound-added");
            CommandExecutor.SKILLS_HITSOUND_ID_NOT_EXIST = config.getString("commands.skills.skill-hitsound-id-not-exist");
            CommandExecutor.SKILLS_HITSOUND_REMOVED = config.getString("commands.skills.skill-hitsound-removed");
            CommandExecutor.SKILLS_HITSOUND_LIST = config.getString("commands.skills.skill-hitsound-list");
            CommandExecutor.SKILLS_HITPARTICLE_ADDED = config.getString("commands.skills.skill-hitparticle-added");
            CommandExecutor.SKILLS_HITPARTICLE_ID_NOT_EXIST = config.getString("commands.skills.skill-hitparticle-id-not-exist");
            CommandExecutor.SKILLS_HITPARTICLE_REMOVED = config.getString("commands.skills.skill-hitparticle-removed");
            CommandExecutor.SKILLS_HITPARTICLE_LIST = config.getString("commands.skills.skill-hitparticle-list");
            CommandExecutor.SKILLS_HITOWNEFFECT_ADDED = config.getString("commands.skills.skill-hitowneffect-added");
            CommandExecutor.SKILLS_HITOWNEFFECT_ID_NOT_EXIST = config.getString("commands.skills.skill-hitowneffect-id-not-exist");
            CommandExecutor.SKILLS_HITOWNEFFECT_REMOVED = config.getString("commands.skills.skill-hitowneffect-removed");
            CommandExecutor.SKILLS_HITOWNEFFECT_LIST = config.getString("commands.skills.skill-hitowneffect-list");
            CommandExecutor.SKILLS_HITENEMYEFFECT_ADDED = config.getString("commands.skills.skill-hitenemyeffect-added");
            CommandExecutor.SKILLS_HITENEMYEFFECT_ID_NOT_EXIST = config.getString("commands.skills.skill-hitenemyeffect-id-not-exist");
            CommandExecutor.SKILLS_HITNEMEYEFFECT_REMOVED = config.getString("commands.skills.skill-hitenemyeffect-removed");
            CommandExecutor.SKILLS_HITENEMYEFFECT_LIST = config.getString("commands.skills.skill-hitenemyeffect-list");
            CommandExecutor.SKILLS_DURATION = config.getString("commands.skills.skill-duration");
            CommandExecutor.SKILLS_UPDATED_LOADING = config.getString("commands.skills.skill-loading");
            CommandExecutor.SKILLS_UPDATED_DURATION = config.getString("commands.skills.skill-executing");
            CommandExecutor.SKILLS_UPDATED_COOLDOWN = config.getString("commands.skills.skill-cooldown");
            CommandExecutor.SKILLS_ENABLED = config.getString("commands.skills.skill-enabled");
            CommandExecutor.SKILLS_DISABLED = config.getString("commands.skills.skill-disabled");
            CommandExecutor.SKILLS_LORE = config.getString("commands.skills.skill-lore");
            CommandExecutor.SKILLS_UPDATED_LORE = config.getString("commands.skills.skill-update-lore");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CardinalException("Language file " + realfile.getName() + " cannot be loaded,", "Plugin cannot run correctly", "Delete folder lang.", Priority.HIGH);
        }
        CardinalLanguage.checkEntries();
    }

    private static List<Field> getFields() {
        Field field;
        ArrayList<Field> fields = new ArrayList<Field>();
        Field[] arrfield = Cardinal.class.getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            field = arrfield[n2];
            fields.add(field);
            ++n2;
        }
        arrfield = Items.class.getDeclaredFields();
        n = arrfield.length;
        n2 = 0;
        while (n2 < n) {
            field = arrfield[n2];
            fields.add(field);
            ++n2;
        }
        arrfield = CommandExecutor.class.getDeclaredFields();
        n = arrfield.length;
        n2 = 0;
        while (n2 < n) {
            field = arrfield[n2];
            fields.add(field);
            ++n2;
        }
        return fields;
    }

    private static void checkEntries() throws Exception {
        for (Field field : CardinalLanguage.getFields()) {
            if (field.get(null) != null && !((String)field.get(null)).equalsIgnoreCase("null")) continue;
            throw new CardinalException("Cardinal language system has triggered an error!\n\t\t Internal field " + field.getName() + " is empty", "Plugin cannot run correctly", "Check your language settings and delete folder lang", Priority.HIGH);
        }
    }

    public static class Cardinal {
        public static String YOU_ARE_DEAD_FOREVER = "You are dead! You cannot rejoin thegreatswordartonlinerpg.";
        public static String YOU_ARE_DEAD_RESPAWN = "You are dead! Respawn in <minutes> minutes.";
        public static String TYPE_SAO_BACK = "Type '/saob' to teleport back to your arena.";
        public static String SET_LOGOUT_LOCATION = "Updated logoutlocation.";
        public static String LIST_EXCEPTIONS = "Exceptions";
        public static String LIST_BLACKLIST = "Blacklist";
        public static String CLEAR_BLACKLIST = "Cleared blacklist.";
        public static String CLEAR_EXCEPTIONS = "Cleared exceptions.";
        public static String SKILL_DETECTED = "SKill Detected.";
        public static String LINK_LOADING = "Linking ";
        public static String NEXT_FLOOR_NOT_EXIST = "The next floor does not exist.";
        public static String PREVIOUS_FLOOR_NOT_EXIST = "The previous floor does not exist.";
        public static String NEXT_FLOOR_NO_PERMS = "You do not have permission. Beat this floor first.";
    }

    public static class CommandExecutor {
        public static String DEVELOPER_REMOVE_ALL_ENTITIES_SUCCESS = "Removed all entities in your world.";
        public static String DEVELOPER_REMOVE_NEXT_ENTITY_SUCCESS = "Removed entity <entity>.";
        public static String DEVELOPER_CRITICAL_DAMAGE_WARNING = "Warning! Using these commands can cause critical damages to your server.";
        public static String CARDINAL_WORLD_NOT_FOUND = "World not found. Make sure you activated world <worldname>.";
        public static String CARDINAL_CONVERT_STANDARD_WORLD = "Converted <worldname> into a standard world.";
        public static String CARDINAL_CONVERT_SAO_WORLD = "Converted <worldname> into a sao world.";
        public static String CARDINAL_SHOW_SAO_WORLD = "SwordArtOnline world <worldname>.";
        public static String CARDINAL_SHOW_STANDARD_WORLD = "Standard world <worldname>.";
        public static String CARDINAL_LIST_WORLDS = "Registered Worlds:";
        public static String FLOOR_FLOOR_NOT_EXIST = "This floor does not exist.";
        public static String FLOOR_FLOOR_EXISTS = "This floor already exists.";
        public static String FLOOR_SET_NEXT_PORTAL = "Updated next-floor-teleporting-portal-position. Everyone who gets in a radius of 2 blocks near this point, gets automatically teleported to the next floor if he has the permission.";
        public static String FLOOR_SET_PREVIOUS_PORTAL = "Updated previous-floor-teleporting-portal-position. Everyone who gets in a radius of 2 blocks near this point, gets automatically teleported to the previous floor.";
        public static String FLOOR_FLOOR_CREATED = "Created floor <floorid>.";
        public static String FLOOR_FLOOR_REMOVED = "Removed floor <floorid>.";
        public static String FLOOR_FINISH_MESSAGE = "Updated floor finish position. Everyone who gets in a radius of 5 blocks near this point, gets automatically the access to the next floor.";
        public static String FLOOR_LIST_FLOORS = "Registered Floors:";
        public static String FLOOR_SHOW_FLOOR = "Floor <floorid> <text>.";
        public static String FLOOR_SET_BOSS = "Updated floor boss.";
        public static String RACES_RACE_NOT_EXIST = "This race does not exist.";
        public static String RACES_RACE_EXISTS = "This race already exists.";
        public static String RACE_SET_SPAWNPOINT = "Updated race spawnpoint.";
        public static String RACE_LIST_RACES = "Registered Races:";
        public static String RACES_RACE_CREATED = "Created race.";
        public static String RACES_RACE_REMOVED = "Removed race.";
        public static String MOB_ENTITYTYPE_NOT_EXIST = "This entitytype does not exist.";
        public static String MOB_ALREADY_EXISTS = "This mob does already exist.";
        public static String MOB_NOT_EXISTS = "This mob does not exist.";
        public static String MOB_ADDED = "Added mob.";
        public static String MOB_REMOVED = "Removed mob.";
        public static String MOB_CLOSE_OTHER_ARENA_FIRST = "Close your other mobArena first.";
        public static String MOB_CREATE_ARENA = "Created mobeditingarena.";
        public static String MOB_SPAWN_CUSTOM_MOB = "Spawned custom mob.";
        public static String MOB_SPAWN_STANDARD_MOB = "Spawned standard mob.";
        public static String MOB_YOU_DONT_HAVE_OPEN_ARENA = "You don't have an open mobeditingarena.";
        public static String MOB_DELETE_ARENA = "Deleted mobeditingarena.";
        public static String MOB_YOU_HAVE_TO_EDIT = "You have to edit a mob.";
        public static String MOB_YOU_NOT_IN_ARENA = "You are not in your mobeditingarena.";
        public static String MOB_TRANSFERED_EFFECTS = "Transfered inventory and effects to mob.";
        public static String MOB_HEALTH_HAS_TO_BE = "Health has to be greater than 0 and lower than 10000000.";
        public static String MOB_DAMAGE_HAS_TO_BE = "Damage has to be greater than 0 and lower than 10000000.";
        public static String MOB_UPDATED_HEALTH = "Updated mob health.";
        public static String MOB_UPDATED_DAMAGE = "Updated mob damage.";
        public static String MOB_ARENA_KILLED = "Killed mob in your arena.";
        public static String MOB_LIST_MOBS = "Registered Mobs:";
        public static String MOB_ENABLED_ARENA_DAYLIGHT = "Enabled daylightburn in your arena.";
        public static String MOB_DISABLED_ARENA_DAYLIGHT = "Disabled daylightburn in your arena.";
        public static String MOB_ENABLED_ARENA_DAMAGEABLE = "Enabled mob damageable in your arena.";
        public static String MOB_DISABLED_ARENA_DAMAGEABLE = "Disabled mob damageable in your arena.";
        public static String MOB_ENABLED_ARENA_MOVEABLE = "Enabled mob moveable in your arena.";
        public static String MOB_DISABLED_ARENA_MOVEABLE = "Disabled mob moveable in your arena.";
        public static String MOB_ENABLED_ARENA_ATTACKING = "Enabled mob attacking in your arena.";
        public static String MOB_DISABLED_ARENA_ATTACKING = "Disabled mob attacking in your arena.";
        public static String MOB_ENABLED_ARENA_ARENAHEALTH = "Enabled mob health in your arena.";
        public static String MOB_DISABLED_ARENA_ARENAHEALTH = "Disabled mob health in your arena.";
        public static String MOB_MOVE_ITEM_HELMET = "Moved item to your helmet slot.";
        public static String MOB_PUT_ITEM_HELMET = "Put an item in your hands to move it to your helmet slot.";
        public static String MOB_MOVE_ITEM_CHESTPLATE = "Moved item to your chestplate slot.";
        public static String MOB_PUT_ITEM_CHESTPLATE = "Put an item in your hands to move it to your chestplate slot.";
        public static String MOB_MOVE_ITEM_LEGGINGS = "Moved item to your leggings slot.";
        public static String MOB_PUT_ITEM_LEGGINGS = "Put an item in your hands to move it to your leggings slot.";
        public static String MOB_MOVE_ITEM_BOOTS = "Moved item to your boots slot.";
        public static String MOB_PUT_ITEM_BOOTS = "Put an item in your hands to move it to your boots slot.";
        public static String MOB_PERCENTAGE = "Percentage cannot be lower than 0 or greater than 100.";
        public static String MOB_UPDATED_HELMET_DC = "Updated helmet dropchance.";
        public static String MOB_UPDATED_CHESTPLATE_DC = "Updated chestplate dropchance.";
        public static String MOB_UPDATED_LEGGINGS_DC = "Updated leggings dropchance.";
        public static String MOB_UPDATED_BOOTS_DC = "Updated boots dropchance.";
        public static String MOB_UPDATED_WEAPON_DC = "Updated weapon dropchance.";
        public static String MOB_SKILL_NOT_EXIST = "This skill does not exist.";
        public static String MOB_SKILL_ALREADY_KNOW = "This mob already knows this skill.";
        public static String MOB_SKILL_NOT_KNOW = "This mob does not know this skill.";
        public static String MOB_SKILL_ADDED = "Added skill to mob.";
        public static String MOB_SKILL_REMOVED = "Removed skill from mob.";
        public static String MOB_SKILL_REGISTERED_SKILLS = "Registered skills on mob :";
        public static String MOB_CLEARED_RIDING_SPOT = "Cleared riding spot.";
        public static String MOB_RIDES_NOW = "Mob rides now <mob> .";
        public static String MOB_KNOCKBACK_LOWER = "Knockback has to be greater than 0 and lower than 1000.";
        public static String MOB_KNOCKBACK_DIRECTION = "This direction does not exist.";
        public static String MOB_UPDATED_KNOCKBACK = "Updated mob <direction> knockback.";
        public static String MOB_DURATION_LOWER = "Duration has to be greater than 0 and lower than 1000.";
        public static String MOB_UPDATED_FIREDAMAGE = "Updated mob fireattack.";
        public static String MOB_ENABLED_DAYLIGHT = "Enabled daylightburn.";
        public static String MOB_DISABLED_DAYLIGHT = "Disabled daylightburn.";
        public static String MOB_ENABLED_DAMAGEABLE = "Enabled damageable.";
        public static String MOB_DISABLED_DAMAGEABLE = "Disabled damageable.";
        public static String MOB_ENABLED_DAMAGEING = "Enabled damaging.";
        public static String MOB_DISABLED_DAMAGEING = "Disabled damaging.";
        public static String MOB_ENABLED_MOVEABLE = "Enabled moving.";
        public static String MOB_DISABLED_MOVEABLE = "Disabled moving.";
        public static String MOB_ENABLED_ATTACKING = "Enabled attacking.";
        public static String MOB_DISABLED_ATTACKING = "Disabled attacking.";
        public static String MOB_ENABLED_HEALTH = "Enabled health.";
        public static String MOB_DISABLED_HEALTH = "Disabled health.";
        public static String MOB_ENABLED_DISPLAYNAME = "Enabled displayName.";
        public static String MOB_DISABLED_DISPLAYNAME = "Disabled displayName.";
        public static String MOB_ENABLED_DROPS = "Enabled classic drops.";
        public static String MOB_DISABLED_DROPS = "Disabled classic drops.";
        public static String MOB_SIZE_MATTERS = "Size has to be greater than 0 and lower than 100.";
        public static String MOB_UPDATED_SLIME_SIZE = "Updated slimesize.";
        public static String MOB_VILLAGERTYPE_MATTERS = "This villagertype does not exist.";
        public static String MOB_UPDATED_VILLAGERTYPE = "Updated villagertype.";
        public static String MOB_VILLAGERPROFESSION_MATTERS = "This villagerprofession does not exist.";
        public static String MOB_UPDATED_VILLAGERPROFESSION = "Updated villagerprofession.";
        public static String MOB_ENABLED_BABY = "Enabled babymode.";
        public static String MOB_DISABLED_BABY = "Disabled babymode.";
        public static String MOB_ENABLED_WITHER = "Enabled withermode.";
        public static String MOB_DISABLED_WITHER = "Disabled withermode.";
        public static String MOB_ENABLED_CATMODE = "Enabled catmode.";
        public static String MOB_DISABLED_CATMODE = "Disabled catmode.";
        public static String MOB_ENABLED_DOGMODE = "Enabled dogmode.";
        public static String MOB_DISABLED_DOGMODE = "Disabled dogmode.";
        public static String MOB_CATCOLOR_MATTERS = "This catcolor does not exist.";
        public static String MOB_UPDATED_CATCOLOR = "Updated catcolor.";
        public static String MOB_ENABLED_POWERED = "Enabled creeper powered.";
        public static String MOB_DISABLED_POWERED = "Disabled creeper powered.";
        public static String MOB_ENABLED_EXPLOSION_DESTROY = "Enabled creeper destroy blocks.";
        public static String MOB_DISABLED_EXPLOSION_DESTROY = "Disabled creeper destroy blocks.";
        public static String MOB_MATTERS_EXPLOSIONRADIUS = "Amount has to be greater than 0 and lower than 100.";
        public static String MOB_UPDATED_EXPLOSIONRADIUS = "Updated explosionradius.";
        public static String RESPAWN_SPAWN_NOT_EXIST = "This spawn does not exist.";
        public static String RESPAWN_SPAWN_ALREADY_EXIST = "This spawn does already exist.";
        public static String RESPAWN_AMOUNT_SMALLER = "Amount cannot be smaller than 1 or greater than 1000000.";
        public static String RESPAWN_AMOUNT_SMALLER2 = "Amount cannot be lower than 1 or greater than 1000.";
        public static String RESPAWN_UPDATE_MAXDESPAWN = "Updated maxdespawndelay.";
        public static String RESPAWN_UPDATE_MAXRESPAWN = "Updated maxrespawndelay.";
        public static String RESPAWN_ENABLED = "Enabled respawnpoint.";
        public static String RESPAWN_DISABLED = "Disabled respawnpoint.";
        public static String RESPAWN_LIST = "Registered MobSpawns:";
        public static String RESPAWN_REMOVE = "Removed mob spawnpoint.";
        public static String RESPAWN_ADDED = "Added mob spawnpoint.";
        public static String RESPAWN_MOB_NOT_EXIST = "This mob does not exist.";
        public static String RESPAWN_RADIUS_X = "XRadius cannot be lower than 1 or greater than 1000.";
        public static String RESPAWN_RADIUS_Y = "YRadius cannot be lower than 1 or greater than 1000.";
        public static String RESPAWN_RADIUS_Z = "ZRadius cannot be lower than 1 or greater than 1000.";
        public static String RESPAWN_RADIUS_D = "Detectionradius cannot be lower than 1 or greater than 1000.";
        public static String SKILLS_ALREADY_EXIST = "This skill already exists.";
        public static String SKILLS_DISPLAYNAME_LONGER = "Display names longer than 16 cause a scoreboard crash!!!";
        public static String SKILLS_TYPE_NOT_EXIST = "This type does not exist. Use sword, bow, axe or universal.";
        public static String SKILLS_CREATED = "Created skill.";
        public static String SKILLS_NOT_EXIST = "This skill does not exist.";
        public static String SKILLS_REMOVED = "Removed skill.";
        public static String SKILLS_CLOSE_OTHER_ARENA = "Close your other arena first";
        public static String SKILLS_CREATED_ARENA = "Created skill-editing-arena";
        public static String SKILLS_YOU_DONT_HAVE_OPEN_ARENA = "You do not have an open arena.";
        public static String SKILLS_CLOSED_SKILL_EDITING_ = "Closed skill-editing-arena.";
        public static String SKILLS_YOU_NOT_IN_ARENA = "You are not in your skill-editing arena.";
        public static String SKILLS_CHANGED_TYPE = "Changed skilltype of your skill";
        public static String SKILLS_CHANGED_DISPLAYNAME = "Changed displayname of your skill.";
        public static String SKILLS_BIND_SKILL = "Put an item in your hand to bind a skill to it.";
        public static String SKILLS_ADDED_SKILL = "Added skill <skill> to your item.";
        public static String SKILLS_API_IS_NOT_INSTALLED = "SkillAPI plugin is not installed.";
        public static String SKILLS_API_SKILL_NOT_EXIST = "SkillAPI skill does not exist.";
        public static String SKILLS_API_SKILL_ADDED = "Added skillapi skill <skill> to your item.";
        public static String SKILLS_LIST = "Registered Skills:";
        public static String SKILLS_ENCHANTMENT_NOT_EXIST = "This enchantment does not exist.";
        public static String SKILLS_ENCHANTMENT_LEVEL = "Enchantment level can not be lower than 1.";
        public static String SKILLS_UNKNOWN_POSITIONS = "Unknow position. Use the values ";
        public static String SKILLS_ENCHANTMENT_ADDED = "Added enchantment.";
        public static String SKILLS_ENCHANTMENT_ID_NOT_EXIST = "This enchantment id does not exist.";
        public static String SKILLS_ENCHANTMENT_REMOVED = "Removed enchantment.";
        public static String SKILLS_ENCHANTMENT_LIST = "Registered enchantments on your skill:";
        public static String SKILLS_EFFECT_NOT_EXIST = "This effect does not exist.";
        public static String SKILLS_EFFECT_DURATION = "Effectduration can not be lower than 1.";
        public static String SKILLS_EFFECT_AMPLIFIER = "Effectamplifier can not be lower than 1.";
        public static String SKILLS_DELAY = "Delay can not be lower than 0.";
        public static String SKILLS_EFFECT_ADDED = "Added effect.";
        public static String SKILLS_EFFECT_ID_NOT_EXIT = "This potioneffect id does not exist.";
        public static String SKILLS_EFFECT_REMOVED = "Removed effect.";
        public static String SKILLS_EFFECT_LIST = "Registered effects on your skill:";
        public static String SKILLS_SOUND_NOT_EXIST = "This sound does not exist.";
        public static String SKILLS_SOUND_VOLUME = "Volume cannot be greater than 2.0 and lower than 0.0.";
        public static String SKILLS_SOUND_PITCH = "Pitch cannot be greater than 2.0 and lower than 0.0.";
        public static String SKILLS_SOUND_ADDED = "Added sound.";
        public static String SKILLS_SOUND_ID_NOT_EXIST = "This sound id does not exist.";
        public static String SKILLS_SOUND_REMOVED = "Removed sound.";
        public static String SKILLS_SOUND_LIST = "Registered sounds on your skill:";
        public static String SKILLS_PARTICLE_NOT_EXIST = "This particle does not exist.";
        public static String SKILLS_PARTICLE_COUNT = "Count can not be lower than 0.";
        public static String SKILLS_PARTICLE_SPEED = "Speed can not be lower than 0.1";
        public static String SKILLS_PARTICLE_SIZE_X = "Sizex can not be lower than 1.";
        public static String SKILLS_PARTICLE_SIZE_Y = "Sizey can not be lower than 1.";
        public static String SKILLS_PARTICLE_SIZE_Z = "Sizez can not be lower than 1.";
        public static String SKILLS_PARTICLE_DELAY = "Delay can not be lower than 0.";
        public static String SKILLS_PARTICLE_ADDED = "Added particle.";
        public static String SKILLS_PARTICLE_REMOVED = "Removed particle.";
        public static String SKILLS_PARTICLE_ID_NOT_EXIST = "This particle id does not exist.";
        public static String SKILLS_PARTICLE_LIST = "Registered particles on your skill:";
        public static String SKILLS_DIRECTION = "This direction does not exist.";
        public static String SKILLS_LAUNCH_AMPLIFIER = "Amplifier can not be lower than 1.";
        public static String SKILLS_LAUNCH_ADDED = "Added launch.";
        public static String SKILLS_LAUNCH_ID_NOT_EXIST = "This launch id does not exist.";
        public static String SKILLS_LAUNCH_LIST = "Registered launches on your skill:";
        public static String SKILLS_LAUNCH_REMOVED = "Removed launch.";
        public static String SKILLS_TELEPORT_BLOCKS_AMOUNT = "Block amount can not be lower than 1.";
        public static String SKILLS_TELEPORT_ADDED = "Added teleport";
        public static String SKILLS_TELEPORT_ID_NOT_EXIST = "This teleport id does not exist.";
        public static String SKILLS_TELEPORT_REMOVED = "Removed teleport.";
        public static String SKILLS_TELEPORT_LIST = "Registered teleports on your skill:";
        public static String SKILLS_HITSOUND_ADDED = "Added hit-sound.";
        public static String SKILLS_HITSOUND_ID_NOT_EXIST = "This hit-sound id does not exist.";
        public static String SKILLS_HITSOUND_REMOVED = "Removed hit-sound.";
        public static String SKILLS_HITSOUND_LIST = "Registered hit-sounds on your skill:";
        public static String SKILLS_HITPARTICLE_ADDED = "Added hit-particle.";
        public static String SKILLS_HITPARTICLE_ID_NOT_EXIST = "This hit-particle id does not exist.";
        public static String SKILLS_HITPARTICLE_REMOVED = "Removed hit-particle.";
        public static String SKILLS_HITPARTICLE_LIST = "Registered hit-particles on your skill:";
        public static String SKILLS_HITOWNEFFECT_ADDED = "Added hit-own-effect.";
        public static String SKILLS_HITOWNEFFECT_ID_NOT_EXIST = "This hit-own-effect id does not exist.";
        public static String SKILLS_HITOWNEFFECT_REMOVED = "Removed hit-own-effect.";
        public static String SKILLS_HITOWNEFFECT_LIST = "Registered hit-own-effects on your skill:";
        public static String SKILLS_HITENEMYEFFECT_ADDED = "Added hit-enemy-effect.";
        public static String SKILLS_HITENEMYEFFECT_ID_NOT_EXIST = "This hit-enemy-effect id does not exist.";
        public static String SKILLS_HITNEMEYEFFECT_REMOVED = "Removed hit-enemy-effect.";
        public static String SKILLS_HITENEMYEFFECT_LIST = "Registered hit-enemy-effects on your skill:";
        public static String SKILLS_DURATION = "Duration can not be lower than 0.";
        public static String SKILLS_UPDATED_LOADING = "Updated loading-duration.";
        public static String SKILLS_UPDATED_DURATION = "Updated skill-duration.";
        public static String SKILLS_UPDATED_COOLDOWN = "Updated skill-cooldown.";
        public static String SKILLS_ENABLED = "Skill was enabled.";
        public static String SKILLS_DISABLED = "Skill was disabled.";
        public static String SKILLS_LORE = "You can only set lore 1,2 or 3.";
        public static String SKILLS_UPDATED_LORE = "Updated lore.";
        public static String MOB_WINGS_NOT_EXIST = "These wings don't exist.";
        public static String MOB_UPDATED_WINGS = "Updated mob wings.";
        public static String MOB_ENABLED_WINGS = "Enabled mob wings.";
        public static String MOB_DISABLED_WINGS = "Disabled mob wings.";
        public static String MOB_FLYDIRECTION_NOT_EXIST = "Flydirection does not exist.";
        public static String MOB_UPDATED_MIN_FLYING = "Updated min amount.";
        public static String MOB_UPDATED_MAX_FLYING = "Updated max amount.";
        public static String MOB_ENABLED_FLYING = "Enabled flying mob.";
        public static String MOB_DISABLED_FLYING = "Disabled flying mob.";
    }

    public static class Items {
        public static String DISPLAYNAME_SKILLAPI = "Skillmenu";
        public static String LORE_SKILLAPI = "Rightclick to open the skill menu.";
        public static String DISPLAYNAME_SCOREBOARD = "Scoreboard";
        public static String LORE_SCOREBOARD = "Rightclick to change the scoreboard.";
        public static String DISPLAYNAME_SOCIAL = "Social";
        public static String LORE_SOCIAL = "Rightclick to open the social interact menu.";
        public static String DISPLAYNAME_EQUIPMENT = "Equipment";
        public static String LORE_EQUIPMENT = "Rightclick to open your equipment menu.";
        public static String DISPLAYNAME_SKILLS = "Skills";
        public static String LORE_SKILLS = "Rightclick to open your skillbar.";
        public static String DISPLAYNAME_EXIT = "Exit";
        public static String LORE_EXIT = "Rightclick to close this sub menu.";
        public static String DISPLAYNAME_NERVEGEAR = "Nervegear";
        public static String LORE_NERVEGEAR = "Put it on your head and click on a random inventory slot to start.";
        public static String DISPLAYNAME_LOGOUT = "Logout";
        public static String LORE_LOGOUT = "Rightclick to logout from the game.";
        public static String DISPLAYNAME_MENU = "Menu";
        public static String LORE_MENU = "Rightclick to open the personal menu.";
        public static String DISPLAYNAME_SIGNSELECTOR = "Sign selector";
        public static String LORE_SIGNSELECTOR = "Rightclick on a sign to convert it into a 'link start' sign.";
        public static String DISPLAYNAME_NEXT = "Next";
        public static String LORE_NEXT = "Rightclick to show the next page.";
        public static String DISPLAYNAME_PETSAPI = "Petsmenu";
        public static String LORE_PETSAPI = "Rightclick to open the pets menu";
        public static String DISPLAYNAME_EQUIPMENT_ARMOR = "Armor";
        public static String LORE_EQUIPMENT_ARMOR = "Rightclick to open the armor page.";
        public static String DISPLAYNAME_EQUIPMENT_MATERIALS = "Resources";
        public static String LORE_EQUIPMENT_MATERIALS = "Rightclick to open the resources page.";
        public static String DISPLAYNAME_EQUIPMENT_WEAPON = "Weapon";
        public static String LORE_EQUIPMENT_WEAPON = "Rightclick to open the weapon page.";
        public static String DISPLAYNAME_EQUIPMENT_FOOD = "Food";
        public static String LORE_EQUIPMENT_FOOD = "Rightclick to open the weapon page.";
        public static String DISPLAYNAME_EQUIPMENT_DROPS = "Drops";
        public static String LORE_EQUIPMENT_DROPS = "Rightclick to open the drops page.";
    }

    public static class Skill {
        public static String SKILL_LOADING = "Skill is loading";
        public static String SKILL_ACTIVATED = "Skill activated";
        public static String SKILL_DEACTIVATED = "Skill deactivated";
        public static String SKILL_COOLDOWN = "Skill is in cooldown";
        public static String SKILL_DISABLED = "Skill disabled";
        public static String SKILL_WRONG_TYPE = "Skill has wrong type";
    }

}

