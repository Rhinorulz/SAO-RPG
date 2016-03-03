/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.bukkit.Sound
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.shynixn.thegreatswordartonlinerpg.gamesystems.skills;

import com.shynixn.thegreatswordartonlinerpg.cardinal.Cardinal;
import com.shynixn.thegreatswordartonlinerpg.cardinal.CardinalException;
import com.shynixn.thegreatswordartonlinerpg.gamesystems.skills.Skill;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Launch;
import com.shynixn.thegreatswordartonlinerpg.resources.effects.Teleport;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Direction;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.Priority;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.SkillType;
import com.shynixn.thegreatswordartonlinerpg.resources.enums.SlotType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import libraries.com.darkblade12.particleeffects.ParticleEffect;
import libraries.com.shynixn.utilities.BukkitFileManager;
import libraries.com.shynixn.utilities.BukkitObject;
import libraries.com.shynixn.utilities.BukkitUtilities;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class SkillFileManager
extends BukkitFileManager {
    private File getFolder() {
        return new File(this.getDataFolder(), "resources/skills");
    }

    public SkillFileManager(JavaPlugin plugin) {
        super(plugin, "skills.dat");
    }

    private void createFolder(BukkitObject object) {
        File file = new File(this.getFolder(), object.getName());
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public boolean save(BukkitObject object) {
        Skill skill = (Skill)object;
        this.createFolder(object);
        if (this.saveSkillData(skill) && this.saveEnchantmentsData(skill) && this.saveLaunchsData(skill) && this.saveOnHitParticleEffectsData(skill) && this.saveOnHitOwnPotionEffectData(skill) && this.saveOnHitSoundData(skill) && this.saveParticleEffectsData(skill) && this.savePotionEffectData(skill) && this.saveSoundData(skill) && this.saveTeleportingData(skill) && this.saveOnHitEnemyPotionEffectData(skill)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(BukkitObject object) {
        try {
            Skill skill = (Skill)object;
            File dir = new File(this.getFolder(), skill.getName());
            BukkitUtilities.u().saveFolderDeleting(dir);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Deleting data from skill " + object.getName() + " failed", "Skill is not deleted", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    @Override
    public BukkitObject[] load() {
        Cardinal.getLogger().logHeadLine("Loading skills");
        ArrayList<Skill> skills = new ArrayList<Skill>();
        String[] files = this.getFolder().list();
        this.loadSkills(skills, files);
        Cardinal.getLogger().logHeadLine("Completed");
        return skills.toArray(new Skill[skills.size()]);
    }

    private void loadSkills(ArrayList<Skill> skills, String[] files) {
        int i = 0;
        while (i < files.length) {
            Skill skill = this.loadSkillData(files[i]);
            this.loadEnchantmentsData(skill);
            this.loadLaunchsData(skill);
            this.loadOnHitOwnPotionEffectData(skill);
            this.loadOnHitSoundData(skill);
            this.loadParticleEffectData(skill);
            this.loadParticleOnHitEffectData(skill);
            this.loadPotionEffectData(skill);
            this.loadSoundData(skill);
            this.loadTeleportingData(skill);
            this.loadOnHitEnemyPotionEffectData(skill);
            skills.add(skill);
            ++i;
        }
    }

    private boolean saveOnHitEnemyPotionEffectData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "hitenemypotioneffects.txt");
            ArrayList<String> dataString = new ArrayList<String>();
            for (Integer key : skill.getHitEnemyEffectIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect data = skill.getHitEnemyEffectFromId(key);
                dataString.add(key + ";" + data.getEffect().getType().getName() + ";" + data.getEffect().getDuration() + ";" + data.getEffect().getAmplifier());
            }
            BukkitUtilities.u().writeAllLines(datafile, dataString);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving hitenemypotioneffects from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadOnHitEnemyPotionEffectData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "hitenemypotioneffects.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(datafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                PotionEffectType potiontype = BukkitUtilities.u().getEffectByName(parts[1]);
                if (potiontype != null) {
                    PotionEffect effect = new PotionEffect(potiontype, Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect effectData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect(effect, -1);
                    skill.addHitEnemyEffect(Integer.parseInt(parts[0]), effectData);
                }
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading hitenemypotioneffects from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean saveOnHitOwnPotionEffectData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "hitpotioneffects.txt");
            ArrayList<String> dataString = new ArrayList<String>();
            for (Integer key : skill.getHitOwnEffectIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect data = skill.getHitOwnEffectFromId(key);
                dataString.add(key + ";" + data.getEffect().getType().getName() + ";" + data.getEffect().getDuration() + ";" + data.getEffect().getAmplifier());
            }
            BukkitUtilities.u().writeAllLines(datafile, dataString);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving hitpotioneffects from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadOnHitOwnPotionEffectData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "hitpotioneffects.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(datafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                PotionEffectType potiontype = BukkitUtilities.u().getEffectByName(parts[1]);
                if (potiontype != null) {
                    PotionEffect effect = new PotionEffect(potiontype, Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect effectData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect(effect, -1);
                    skill.addHitOwnEffect(Integer.parseInt(parts[0]), effectData);
                }
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading hitpotioneffects from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean saveOnHitParticleEffectsData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "hitparticles.txt");
            ArrayList<String> dataString = new ArrayList<String>();
            for (Integer key : skill.getHitParticleIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect data = skill.getHitParticleFromId(key);
                dataString.add((Object)((Object)data.getEffect()) + ";" + data.getCount() + ";" + data.getSpeed() + ";" + data.getSizeX() + ";" + data.getSizeY() + ";" + data.getSizeZ() + ";" + key);
            }
            BukkitUtilities.u().writeAllLines(datafile, dataString);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving hitparticles from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadParticleOnHitEffectData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "hitparticles.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(datafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                ParticleEffect particleEffect = ParticleEffect.getParticelEffectByName(parts[0]);
                if (particleEffect != null) {
                    com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect mydata = new com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect(particleEffect, Integer.parseInt(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]), Float.parseFloat(parts[4]), Float.parseFloat(parts[5]));
                    skill.addHitParticle(Integer.parseInt(parts[6]), mydata);
                }
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading hitparticles from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean saveOnHitSoundData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "hitsounds.txt");
            ArrayList<String> dataString = new ArrayList<String>();
            for (Integer key : skill.getHitSoundIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound data = skill.getHitSoundFromId(key);
                dataString.add(key + ";" + (Object)data.getSound() + ";" + data.getDelay() + ";" + data.getVolume() + ";" + data.getPitch());
            }
            BukkitUtilities.u().writeAllLines(datafile, dataString);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving hitsounds from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadOnHitSoundData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "hitsounds.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(datafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                Sound sound = BukkitUtilities.u().getSoundByName(parts[1]);
                if (sound != null) {
                    skill.addHitSound(Integer.parseInt(parts[0]), new com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound(sound, Double.parseDouble(parts[3]), Double.parseDouble(parts[4])));
                }
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading hitsounds from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean saveTeleportingData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "teleports.txt");
            ArrayList<String> dataString = new ArrayList<String>();
            for (Integer key : skill.getTeleportIds()) {
                Teleport data = skill.getTeleportFromId(key);
                dataString.add(key + ";" + data.getDirection().name() + ";" + data.getDelay() + ";" + data.getBlockAmount());
            }
            BukkitUtilities.u().writeAllLines(datafile, dataString);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving teleports from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadTeleportingData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "teleports.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(datafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                Teleport teleportingData = new Teleport(Direction.getDirection(parts[1]), Integer.parseInt(parts[3]), Integer.parseInt(parts[2]));
                skill.addTeleport(Integer.parseInt(parts[0]), teleportingData);
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading teleports from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean saveSoundData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "sounds.txt");
            ArrayList<String> dataString = new ArrayList<String>();
            for (Integer key : skill.getSoundIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound data = skill.getSoundFromId(key);
                dataString.add(key + ";" + (Object)data.getSound() + ";" + data.getDelay() + ";" + data.getVolume() + ";" + data.getPitch());
            }
            BukkitUtilities.u().writeAllLines(datafile, dataString);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving sounds from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadSoundData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "sounds.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(datafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                Sound sound = BukkitUtilities.u().getSoundByName(parts[1]);
                if (sound != null) {
                    skill.addSound(Integer.parseInt(parts[0]), new com.shynixn.thegreatswordartonlinerpg.resources.effects.Sound(sound, Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Integer.parseInt(parts[2])));
                }
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading sounds from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean savePotionEffectData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "potioneffects.txt");
            ArrayList<String> dataString = new ArrayList<String>();
            for (Integer key : skill.getEffectIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect data = skill.getEffectFromId(key);
                dataString.add(key + ";" + data.getEffect().getType().getName() + ";" + data.getEffect().getDuration() + ";" + data.getEffect().getAmplifier() + ";" + data.getDelay());
            }
            BukkitUtilities.u().writeAllLines(datafile, dataString);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving potioneffects from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadPotionEffectData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "potioneffects.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(datafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                PotionEffectType potiontype = BukkitUtilities.u().getEffectByName(parts[1]);
                if (potiontype != null) {
                    com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect effectData = new com.shynixn.thegreatswordartonlinerpg.resources.effects.PotionEffect(new PotionEffect(potiontype, Integer.parseInt(parts[2]), Integer.parseInt(parts[3])), Integer.parseInt(parts[4]));
                    skill.addEffect(Integer.parseInt(parts[0]), effectData);
                }
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading potioneffects from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean saveParticleEffectsData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "particles.txt");
            ArrayList<String> dataString = new ArrayList<String>();
            for (Integer key : skill.getParticleIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect data = skill.getParticleFromId(key);
                dataString.add((Object)((Object)data.getEffect()) + ";" + data.getCount() + ";" + data.getSpeed() + ";" + data.getSizeX() + ";" + data.getSizeY() + ";" + data.getSizeZ() + ";" + key + ";" + data.getDelay());
            }
            BukkitUtilities.u().writeAllLines(datafile, dataString);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving particles from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadParticleEffectData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "particles.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(datafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                ParticleEffect particleEffect = ParticleEffect.getParticelEffectByName(parts[0]);
                if (particleEffect != null) {
                    com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect mydata = new com.shynixn.thegreatswordartonlinerpg.resources.effects.ParticleEffect(particleEffect, Integer.parseInt(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]), Float.parseFloat(parts[4]), Float.parseFloat(parts[5]), Integer.parseInt(parts[7]));
                    skill.addParticle(Integer.parseInt(parts[6]), mydata);
                }
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading particles from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean saveLaunchsData(Skill skill) {
        try {
            File datafile = new File(this.getFolder() + "/" + skill.getName(), "launches.txt");
            ArrayList<String> data = new ArrayList<String>();
            for (Integer key : skill.getLaunchIds()) {
                Launch launchData = skill.getLaunchFromId(key);
                data.add(key + ";" + launchData.getDirection().name() + ";" + launchData.getDelay() + ";" + launchData.getAmplifier());
            }
            BukkitUtilities.u().writeAllLines(datafile, data);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving launches from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadLaunchsData(Skill skill) {
        try {
            File launchsdatafile = new File(this.getFolder() + "/" + skill.getName(), "launches.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(launchsdatafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                Launch launchssdata = new Launch(Direction.valueOf(parts[1].toUpperCase()), Double.parseDouble(parts[3]), Integer.parseInt(parts[2]));
                skill.addLaunch(Integer.parseInt(parts[0]), launchssdata);
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading launches from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean saveEnchantmentsData(Skill skill) {
        try {
            File enchantmentsdatafile = new File(this.getFolder() + "/" + skill.getName(), "enchantments.txt");
            ArrayList<String> data = new ArrayList<String>();
            for (Integer key : skill.getEnchantmentIds()) {
                com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment enchantMentData = skill.getEnchantmentFromId(key);
                data.add(key + ";" + enchantMentData.getEnchantment().getName() + ";" + enchantMentData.getLevel() + ";" + enchantMentData.getDelay() + ";" + enchantMentData.getPosition().name());
            }
            BukkitUtilities.u().writeAllLines(enchantmentsdatafile, data);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving enchantments from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean loadEnchantmentsData(Skill skill) {
        try {
            File enchantmentsdatafile = new File(this.getFolder() + "/" + skill.getName(), "enchantments.txt");
            String[] arrstring = BukkitUtilities.u().readAllLines(enchantmentsdatafile);
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String s = arrstring[n2];
                String[] parts = s.split(";");
                Enchantment enchantment = BukkitUtilities.u().getEnchantMentByName(parts[1]);
                if (enchantment != null) {
                    com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment enchantmentsdata = new com.shynixn.thegreatswordartonlinerpg.resources.effects.Enchantment(enchantment, Integer.parseInt(parts[2]), SlotType.getSlotType(parts[4]), Integer.parseInt(parts[3]));
                    skill.addEnchantment(Integer.parseInt(parts[0]), enchantmentsdata);
                }
                ++n2;
            }
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading enchantments from skill " + skill.getName() + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private boolean saveSkillData(Skill skill) {
        try {
            YamlConfiguration dataconfig = new YamlConfiguration();
            File skilldata = BukkitUtilities.u().createFile(new File(this.getFolder() + "/" + skill.getName(), "skill.yml"));
            dataconfig.load(skilldata);
            dataconfig.set("skill.general.name", (Object)skill.getName());
            dataconfig.set("skill.general.displayname", (Object)skill.getDisplayName());
            dataconfig.set("skill.general.lore.1", (Object)skill.getLore(0));
            dataconfig.set("skill.general.lore.2", (Object)skill.getLore(1));
            dataconfig.set("skill.general.lore.3", (Object)skill.getLore(2));
            dataconfig.set("skill.general.type", (Object)skill.getType().name());
            dataconfig.set("skill.general.enabled", (Object)skill.isEnabled());
            dataconfig.set("skill.duration.cooldown", (Object)skill.getCoolDownTime());
            dataconfig.set("skill.duration.loading", (Object)skill.getLoadingTime());
            dataconfig.set("skill.duration.executing", (Object)skill.getExecutingTime());
            dataconfig.save(skilldata);
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Saving data from skill " + skill.getName() + " failed", "Skill is not saved", "Make sure the filesystem is correct", Priority.MEDIUM));
            return false;
        }
        return true;
    }

    private Skill loadSkillData(String folder) {
        Skill skill = null;
        try {
            YamlConfiguration dataconfig = new YamlConfiguration();
            File skilldata = new File(this.getFolder() + "/" + folder, "skill.yml");
            dataconfig.load(skilldata);
            skill = new Skill(dataconfig.getString("skill.general.name"), dataconfig.getString("skill.general.displayname"), SkillType.getSkillType(dataconfig.getString("skill.general.type")));
            skill.setSkillEnabled(dataconfig.getBoolean("skill.general.enabled"));
            skill.setCooldDownTime(dataconfig.getInt("skill.duration.cooldown"));
            skill.setExecutingTime(dataconfig.getInt("skill.duration.executing"));
            skill.setLoadingTime(dataconfig.getInt("skill.duration.loading"));
            skill.setLore(0, dataconfig.getString("skill.general.lore.1"));
            skill.setLore(1, dataconfig.getString("skill.general.lore.2"));
            skill.setLore(2, dataconfig.getString("skill.general.lore.3"));
        }
        catch (Exception e) {
            Cardinal.call().sendException(new CardinalException("Loading data from skill " + folder + " failed", "Skill is not loaded", "Make sure the filesystem is correct", Priority.MEDIUM));
            return null;
        }
        return skill;
    }
}

